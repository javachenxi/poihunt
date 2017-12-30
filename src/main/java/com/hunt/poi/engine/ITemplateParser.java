package com.hunt.poi.engine;

import org.apache.poi.POIXMLDocument;

import com.hunt.poi.provider.IReadParam;

public interface ITemplateParser {
	
	final char S_TOKEN_PARAM = '$';
	
	final char SS_TOKEN_PARAM = '{';
	
	final char E_TOKEN_PARAM = '}';
	
	public POIXMLDocument execute(TemplateContext context, POIXMLDocument poiDoc,
			IReadParam params);
	
}
