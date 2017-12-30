package com.hunt.poi.engine;

import com.hunt.poi.provider.IParamProvider;

public class TemplateEngineFactory {	
	
	public static ITemplateEngine createEngine(String providerClass) {
		return new XDocTemplateEngine(providerClass);
	}
	
	public static ITemplateEngine createEngine() {
		return new XDocTemplateEngine();
	}
	
	public static ITemplateEngine createEngine(IParamProvider paramProvider) {
		return new XDocTemplateEngine(paramProvider);
	}

}
