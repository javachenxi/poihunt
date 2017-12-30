package com.hunt.poi.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.hunt.poi.expr.IExprActuator;
import com.hunt.poi.expr.SimpleExprActuator;
import com.hunt.poi.provider.IParamProvider;
import com.hunt.poi.provider.IReadParam;
import com.hunt.poi.provider.SimpleParamProvider;
import com.hunt.poi.util.SimpleClassLoader;

public class XDocTemplateEngine implements ITemplateEngine{
	
	private ITemplateParser templateParser = null;
	
	private IParamProvider paramProvider = null;
	
	private IExprActuator exprActuator = null;
	
	public XDocTemplateEngine() {
		this.templateParser = new XDocTemplateParser();
		this.paramProvider = new SimpleParamProvider();
		this.exprActuator = new SimpleExprActuator();
	}
	
	public XDocTemplateEngine(IParamProvider paramProvider) {
		this.templateParser = new XDocTemplateParser();
		this.paramProvider = paramProvider;
		this.exprActuator = new SimpleExprActuator();
	}
	
	public XDocTemplateEngine(String providerClass) {
		if(providerClass == null) {
			throw new TemplateRuntimeException(" providerClass parameter is null."); 
		}
		
		this.templateParser = new XDocTemplateParser();
		this.paramProvider = SimpleClassLoader.getInstance().getParamProviderClass(providerClass);
		this.exprActuator = new SimpleExprActuator();
	}
	
	public XDocTemplateEngine(ITemplateParser templateParser, 
			IParamProvider paramProvider,IExprActuator exprActuator) {
		this.templateParser = templateParser;
		this.paramProvider = paramProvider;
		this.exprActuator = exprActuator;
	}

	@Override
	public void renderTemplate(TemplateContext templateContext) {
		
		templateContext.setCurrEngine(this);
		
		Template template = templateContext.getTemplate();
		File templateFile = new File(template.getTemplateFile());
		
		if(!templateFile.isFile() || !templateFile.exists()) {
			throw new TemplateRuntimeException("The template file is not find. " + template.getTemplateFile());
		}
		
		if(!template.isXDocTemplate()) {
			throw new TemplateRuntimeException("The template file type suffix is illegal. " + template.getSuffix());
		}
		
		FileInputStream templateInput = null;
		
		try {
			templateInput = new FileInputStream(templateFile);
			XWPFDocument poiDoc = new XWPFDocument(templateInput);
			IReadParam readParam = paramProvider.produceParams();
			templateParser.execute(templateContext, poiDoc, readParam);	
			poiDoc.write(templateContext.getOutput());
			
		}catch (Exception e) {
			throw new TemplateRuntimeException("Engine is error. " + templateFile.getPath() , e);
		}finally {
			
			if(templateInput != null) {
				try {
					templateInput.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}
	}

	public IExprActuator getExprActuator() {
		return exprActuator;
	}

	public void setExprActuator(IExprActuator exprActuator) {
		this.exprActuator = exprActuator;
	}

	public ITemplateParser getTemplateParser() {
		return templateParser;
	}

	public void setTemplateParser(ITemplateParser templateParser) {
		this.templateParser = templateParser;
	}

	public IParamProvider getParamProvider() {
		return paramProvider;
	}

	public void setParamProvider(IParamProvider paramProvider) {
		this.paramProvider = paramProvider;
	}
	
     
}
