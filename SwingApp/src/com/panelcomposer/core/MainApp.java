package com.panelcomposer.core;

import log.Logger;

import org.hibernate.tool.hbm2ddl.SchemaExport;

import util.AnnotationConfigurationWithWildcard;
import util.PersistenceHelper;
import util.xml_readers.EntityReader;
import util.xml_readers.EnumerationReader;
import util.xml_readers.PanelReader;
import util.xml_readers.TypeComponentReader;

import com.panelcomposer.elements.SMainForm;

public class MainApp {
	
	public static void main(String[] args) {
		//Logger.getLogger("").setLevel(Level.OFF);
		Logger.log("Kreno");
		PersistenceHelper.createFactory("hotelsoap");
		
		TypeComponentReader.loadMappings();
		EnumerationReader.loadMappings();
		//TypeMapping.loadMappings();
		EntityReader.loadMappings();
		PanelReader.loadMappings();
		
		//SCHEMA EXPORT
		AnnotationConfigurationWithWildcard cfg = new AnnotationConfigurationWithWildcard();
		cfg.configure();
		new SchemaExport(cfg).create(true, true);
		
		SMainForm sm = new SMainForm();
		sm.setVisible(true);
	}
}
