package com.hunt.poi.engine;

public class Template {
	
	public final static String XDOC_SUFFIX = "docx";
	
	private String dir;
	private String name;
	private String suffix;
	private String filepath;
	
	public Template(String filepath) {
		this.filepath = filepath;
		splitFilepath();
	}
	
	public Template(String dir, String name, String suffix) {
		this.dir = dir;
		this.name = name;
		this.suffix = suffix;
		buildFilepath();
	}
	
	private void splitFilepath() {
		
		if(this.filepath == null) {
			return ;
		}
		
		int nIndex = this.filepath.lastIndexOf("/");
		
		if(nIndex < 0) {
			nIndex = this.filepath.lastIndexOf("\\");
		}
		
		String nameAndSuffix = null;
		
		if(nIndex > -1) {
			this.dir = this.filepath.substring(0, nIndex);
			nameAndSuffix = this.filepath.substring(nIndex+1);			
		}else {
			nameAndSuffix = this.filepath;
		}
		
		int sIndex = nameAndSuffix.indexOf('.');
		
		if(sIndex > -1) {
			this.name = nameAndSuffix.substring(0, sIndex);
			this.suffix = nameAndSuffix.substring(sIndex+1);
		}else {
			this.name = nameAndSuffix;
		}
		
	}
	
	private void buildFilepath() {
		this.filepath = (this.dir==null?"": this.dir+"/") + this.name + "" + this.suffix;
	}
	
	public String getTemplateFile() {
		return this.filepath;
	}
	
	public boolean isXDocTemplate() {
		return XDOC_SUFFIX.equals(suffix);
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
		buildFilepath();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		buildFilepath();
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
		buildFilepath();
	}
	
	public String toString() {
		return this.getTemplateFile();
	}
	
}
