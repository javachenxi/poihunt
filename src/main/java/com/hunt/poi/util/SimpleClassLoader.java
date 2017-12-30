package com.hunt.poi.util;

import com.hunt.poi.engine.TemplateRuntimeException;
import com.hunt.poi.provider.IParamProvider;
import com.hunt.poi.util.SimpleObjectCache.IGenerateObject;

public class SimpleClassLoader {
	
	private SimpleObjectCache<String, Object> simpleObjectCache = null;
	
	private static SimpleClassLoader simpleClassLoader = null;
	
	private SimpleClassLoader() {
		simpleObjectCache = new SimpleObjectCache<String, Object>(new GenerateObject(this));
	}
	
	public static SimpleClassLoader getInstance() {
		
		if(simpleClassLoader == null) {
			synchronized (SimpleClassLoader.class) {
				
				if(simpleClassLoader == null) {
					simpleClassLoader = new SimpleClassLoader();
				}
				
			}
		}
		
		return simpleClassLoader;
	}
	
	public IParamProvider getParamProviderClass(String providerClass) {
		return (IParamProvider)simpleObjectCache.getObject(providerClass);
	}
	
	public Class<?> loadClass(String providerClass) {
		
		ClassLoader thClassLoader = Thread.currentThread().getContextClassLoader();
		Class<?> pClass = null;
		
		try {
			pClass = thClassLoader.loadClass(providerClass);
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
		
		if(pClass == null) {
			try {
				pClass = Class.forName(providerClass);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(pClass == null) {
			try {
				pClass = ClassLoader.getSystemClassLoader().loadClass(providerClass);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return pClass;
	}
    
    public class GenerateObject implements IGenerateObject<String, Object> {
    	
    	private SimpleClassLoader simpleClassLoader = null;
    	
    	public GenerateObject(SimpleClassLoader simpleClassLoader) {
    		this.simpleClassLoader = simpleClassLoader;
    	}

		@Override
		public Object create(String key) {
			
			Class<?> class1 = simpleClassLoader.loadClass(key);
			
			if(class1 == null) {
				throw new TemplateRuntimeException(" Class " + key  + " is not find!");
			}
			
			try {
				return class1.newInstance();
			} catch (Exception e) {
				throw new TemplateRuntimeException(" Class " + key  + " new instance is error!", e);
			}			
		}
    	
    }
    

}




