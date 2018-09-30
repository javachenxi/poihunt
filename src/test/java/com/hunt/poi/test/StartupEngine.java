package com.hunt.poi.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hunt.poi.engine.ITemplateEngine;
import com.hunt.poi.engine.Template;
import com.hunt.poi.engine.TemplateContext;
import com.hunt.poi.engine.TemplateEngineFactory;
import com.hunt.poi.provider.SimpleParamProvider;

public class StartupEngine {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileOutputStream outputStream = null;
		
		try {
			outputStream = new FileOutputStream("D:\\temp\\real-template-bak.docx");
			SimpleParamProvider paramProvider = new SimpleParamProvider();
			initRealMap(paramProvider);
			ITemplateEngine templateEngine = TemplateEngineFactory.createEngine(paramProvider);
			Template template = new Template("D:\\temp\\real-template.docx");
			TemplateContext templateContext = new TemplateContext(template, outputStream);
			templateEngine.renderTemplate(templateContext);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if(outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		
	}
	
	private static void initRealMap(SimpleParamProvider paramProvider) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("month", "12");
		map.put("year", "2017");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> listmap = new HashMap<String, String>();
		listmap.put("sec", "3444");
		listmap.put("thd", "4544");
		listmap.put("four", "233");
		list.add(listmap);
		list.add(listmap);
		
		map.put("table", list);		
		paramProvider.initParamap(map); 
	}
	


}
