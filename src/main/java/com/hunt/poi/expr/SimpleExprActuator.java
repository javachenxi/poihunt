package com.hunt.poi.expr;


import com.hunt.poi.engine.TemplateRuntimeException;
import com.hunt.poi.provider.IReadParam;
import com.hunt.poi.util.EasyMothedUtil;

public class SimpleExprActuator implements IExprActuator {
	
	public SimpleExprActuator() {
		
	}

	@Override
	public String runExpr(String exprStr, IReadParam params) {
		
		if(exprStr == null||exprStr.length() == 0 ) {
			return "";
		}
		
		String tmpExpr = exprStr.trim();
		
		String retStr = null;
		
		if(tmpExpr.indexOf(VAR_SEPARATOR) > -1) {
			
			String[] paramKeys = EasyMothedUtil.splitByChar(tmpExpr, VAR_SEPARATOR);
			
			if(paramKeys.length > 3 
					|| (paramKeys.length == 3 && !EasyMothedUtil.isNumberStr(paramKeys[2]))) {
				throw new TemplateRuntimeException("Expression is illegal, correct expression is 'key1.key2.1' or 'key1.key2'. " + exprStr);
			}
			
			if(paramKeys.length > 2) {
				retStr = params.getTableString(paramKeys[0].trim(), paramKeys[1].trim(), Integer.parseInt(paramKeys[2].trim()));
			}else {
				retStr = params.getMapString(paramKeys[0].trim(), paramKeys[1].trim());
			}
			
		}else {
			retStr = params.getString(exprStr);
		}
		
		return retStr;
	}
	
	
	
	

}
