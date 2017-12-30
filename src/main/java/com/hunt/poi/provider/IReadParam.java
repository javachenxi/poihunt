package com.hunt.poi.provider;

public interface IReadParam {
	
	public String getString(String key);
	
	public Object getObject(String key);
	
	public String getTableString(String tableTag, String columnTag, int row);
	
	public String getMapString(String key, String secKey);
    
}
