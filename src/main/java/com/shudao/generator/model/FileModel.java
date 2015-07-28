package com.shudao.generator.model;

/**
 * 模板文件实体
 * @author dali
 *
 */
public class FileModel {
	
	//输出路径
	private String outPath;
	//模板所在路径
	private String templatePath;
	//输出名称
	private String outName;
	//模板名称
	private String templateName;
	
	
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getOutPath() {
		return outPath;
	}
	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}
	public String getOutName() {
		return outName;
	}
	public void setOutName(String outName) {
		this.outName = outName;
	}
}
