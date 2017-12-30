package com.hunt.poi.provider;

import java.util.HashMap;
import java.util.Map;

public class SimpleParamProvider implements IParamProvider {
    
	private Map<String, Object> map = null;
	
	public SimpleParamProvider() {
		map = new HashMap<String, Object>();
	}
	
	@Override
	public IReadParam produceParams() {
		// TODO Auto-generated method stub
		IReadParam readParam = new XDocReadParam(map);
		return readParam;
	}
	
	public void initParamap(Map<String, Object> paramap) {
		
		if(paramap != null && !paramap.isEmpty()) {
			map.putAll(paramap);
		}
		
	}
	
}






