package com.hunt.poi.engine;

import java.io.OutputStream;

public class TemplateContext {
	
	private Template template;
	
	private OutputStream output;
	
	
	private ITemplateEngine currEngine;
	
	public TemplateContext(Template template,OutputStream output) {
		this.template = template;
		this.output = output;
	}
	

	public ITemplateEngine getCurrEngine() {
		return currEngine;
	}


	public void setCurrEngine(ITemplateEngine currEngine) {
		this.currEngine = currEngine;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}
	
	

}
