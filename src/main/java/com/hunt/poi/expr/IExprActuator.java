package com.hunt.poi.expr;

import com.hunt.poi.provider.IReadParam;

public interface IExprActuator {
	
	final char VAR_SEPARATOR = '.';
	
	public String runExpr(String exprStr, IReadParam params);

}
