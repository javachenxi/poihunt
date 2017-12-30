package com.hunt.poi.engine;

import com.hunt.poi.expr.IExprActuator;
import com.hunt.poi.provider.IParamProvider;

public interface ITemplateEngine {
	
	public void renderTemplate(TemplateContext templateContext);

	public IExprActuator getExprActuator();

	public void setExprActuator(IExprActuator exprActuator);

	public ITemplateParser getTemplateParser();

	public void setTemplateParser(ITemplateParser templateParser);

	public IParamProvider getParamProvider() ;

	public void setParamProvider(IParamProvider paramProvider);
	

}
