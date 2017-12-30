package com.hunt.poi.provider;

import java.util.List;
import java.util.Map;

public class XDocReadParam implements IReadParam {
	
	private Map<String, Object> paraMap = null;
	
	public XDocReadParam(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}
	
	public String getString(String key) {
		Object obj = paraMap.get(key);		
		return obj==null ? "": obj.toString();
	}
	
	public Object getObject(String key) {
		Object obj = paraMap.get(key);
		return obj;
	}
	
	@SuppressWarnings("rawtypes")
	public String getMapString(String key, String secKey) {
        Object objMap = paraMap.get(key);
		
		if(objMap == null || !(objMap instanceof Map)) {
			return "";
		}
		
		Map map = (Map)objMap;
		
		Object tmpObj =  map.get(secKey);
		
		return tmpObj == null ? "": tmpObj.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public String getTableString(String tableTag, String columnTag, int row) {
		Object objlist = paraMap.get(tableTag);
		
		if(objlist == null || !(objlist instanceof List)) {
			return "";
		}
		
		List list = (List) objlist;
		
		if(list.size() < row ) {
			return "";
		}
		
		Object tmpObj = list.get(row-1);
		
		if(tmpObj == null || !(tmpObj instanceof Map)) {
			return "";
		}
		
		Map objMap = (Map)tmpObj;
		
		tmpObj = objMap.get(columnTag);
		
		return tmpObj == null ? "": tmpObj.toString();
	}
	

}
