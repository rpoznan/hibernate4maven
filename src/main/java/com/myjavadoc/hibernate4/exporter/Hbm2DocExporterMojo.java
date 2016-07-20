package com.myjavadoc.hibernate4.exporter;

import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

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

import org.hibernate.tool.hbm2x.DocExporter;
import org.hibernate.tool.hbm2x.Exporter;
//import org.codehaus.mojo.hibernate3.HibernateExporterMojo;

import com.myjavadoc.hibernate4.HibernateExporterMojo;

/**
 * Generates HTML documentation for the database schema.
 *
 * @author <a href="mailto:jreyes@hiberforum.org">Johann Reyes</a>
 * @version $Id: Hbm2DocExporterMojo.java 6588 2008-03-28 12:22:57Z bentmann $
 //* @goal hbm2doc
 **/
@Mojo(name = "hbm2doc", threadSafe = true)
public class Hbm2DocExporterMojo
    extends HibernateExporterMojo
{
    /**
     * Default constructor.
     */
    public Hbm2DocExporterMojo()
    {
    	super();
        addDefaultComponent( "target/hibernate3/javadoc", "configuration", false );
        addDefaultComponent( "target/hibernate3/javadoc", "annotationconfiguration", true );
    }

// --------------------- Interface ExporterMojo ---------------------

    /**
     * Returns <b>hbm2doc</b>.
     *
     * @return String goal's name
     */

    public String getName()
    {
        return "hbm2doc";
    }

    /**
     * Instantiates a org.hibernate.tool.hbm2x.DocExporter object.
     *
     * @return DocExporter
     */
    public final Exporter createExporter()
    {
        return new DocExporter();
    }
}
