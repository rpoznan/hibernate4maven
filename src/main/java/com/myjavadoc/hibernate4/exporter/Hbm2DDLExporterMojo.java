package com.myjavadoc.hibernate4.exporter;

/*
 * Copyright 2005 Johann Reyes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
//import org.codehaus.mojo.hibernate3.HibernateExporterMojo;
//import org.codehaus.mojo.hibernate3.HibernateUtils;
import org.hibernate.tool.hbm2x.Exporter;

import com.myjavadoc.hibernate4.HibernateExporterMojo;
import com.myjavadoc.hibernate4.HibernateUtils;

import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.io.File;

/**
 * Generates database schema.
 *
 * @author <a href="mailto:jreyes@hiberforum.org">Johann Reyes</a>
 * @version $Id: Hbm2DDLExporterMojo.java 6588 2008-03-28 12:22:57Z bentmann $
 // * @goal hbm2ddl
 //* @execute phase="process-resources"
 */
@Mojo(name = "hbm2ddl", threadSafe = true, defaultPhase = LifecyclePhase.PROCESS_RESOURCES, requiresDependencyResolution = ResolutionScope.TEST)
public class Hbm2DDLExporterMojo
    extends HibernateExporterMojo
{
    /**
     * Default constructor.
     */
    public Hbm2DDLExporterMojo()
    {
    	super();
        addDefaultComponent( "target/hibernate3/sql", "configuration", false );
        addDefaultComponent( "target/hibernate3/sql", "annotationconfiguration", true );
    }

// --------------------- Interface ExporterMojo ---------------------

    /**
     * Returns <b>hbm2ddl</b>.
     *
     * @return String goal's name
     */
    public String getName()
    {
        return "hbm2ddl";
    }

    /**
     * This method is not to be used.
     *
     * @return throws IllegalStateException
     */
    protected Exporter createExporter()
    {
        throw new IllegalStateException( "Should not call create exporter on hbm2ddl" );
    }

    /**
     * Overrides the default implementation of executing this goal.
     *
     * @throws MojoExecutionException if there is an error executing the goal
     */
    protected void doExecute()
        throws MojoExecutionException
    {
        boolean scriptToConsole = getComponentProperty( "console", true );
        boolean exportToDatabase = getComponentProperty( "export", true );
        boolean haltOnError = getComponentProperty( "haltonerror", false );
        boolean drop = getComponentProperty( "drop", false );
        boolean create = getComponentProperty( "create", true );
        String implementation = getComponentProperty( "implementation", getComponent().getImplementation() );

        Configuration configuration = getComponentConfiguration( implementation ).getConfiguration( this );

        if ( getComponentProperty( "update", false ) )
        {
            SchemaUpdate update = new SchemaUpdate( configuration );
            update.execute( scriptToConsole, exportToDatabase );
        }
        else
        {
            SchemaExport export = new SchemaExport( configuration );
            export.setDelimiter( getComponentProperty( "delimiter", ";" ) );
            export.setHaltOnError( haltOnError );
            export.setFormat( getComponentProperty( "format", false ) );

            String outputFilename = getComponentProperty( "outputfilename" );
            if ( outputFilename != null )
            {
                File outputFile = HibernateUtils.prepareFile(
                    new File( getProject().getBasedir(), getComponent().getOutputDirectory() ), outputFilename,
                    "outputfilename" );
                export.setOutputFile( outputFile.toString() );
            }

            if ( drop && create )
            {
                export.create( scriptToConsole, exportToDatabase );
            }
            else
            {
                export.execute( scriptToConsole, exportToDatabase, drop, create );
            }

            if ( export.getExceptions().size() > 0 )
            {
                Iterator iterator = export.getExceptions().iterator();
                int cnt = 1;
                getLog().warn( export.getExceptions().size() + " errors occurred while performing <hbm2ddl>." );
                while ( iterator.hasNext() )
                {
                    getLog().error( "Error #" + cnt + ": " + iterator.next().toString() );
                }
                if ( haltOnError )
                {
                    throw new MojoExecutionException( "Errors while performing <hbm2ddl>" );
                }
            }
        }
    }
}