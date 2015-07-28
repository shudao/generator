package com.shudao.generator.model;

import com.shudao.generator.util.DbUtil;

/**
 * code配置实体
 * @author dali
 *
 */
public class CodeModel {
	
	//model输出路径
	private String modelPath;
	//dao输出路径
	private String daoPath;
	//service输出路径
	private String servicePath;
	//controller输出路径
	private String controllerPath;
	//author姓名
	private String author;
	//模块名称,如:用户信息
	private String moduleName;
	//包名称,如com.shudao.generator
	private String packageName;
	//模板所在位置
	private String templatePath;
	//转换后的类名
	private String className;
	//转换后的首字母小写类名
	private String lowerName;
	//全路径的模板地址,如d:/generator/src/main/resources/template
	private String fullTemplatePath;
	//是否覆盖生成
	private boolean replaceable ;
	
	public String getModelPath() {
		return modelPath;
	}
	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
	public String getDaoPath() {
		return daoPath;
	}
	public void setDaoPath(String daoPath) {
		this.daoPath = daoPath;
	}
	public String getServicePath() {
		return servicePath;
	}
	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}
	public String getControllerPath() {
		return controllerPath;
	}
	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public void convertPackageName(String packageName)
	{
		//将包名转换为目录的形式
		String temp = packageName.replace(".", "\\");
		this.setPackageName(temp);
	}
	public String getTemplatePath() {
		return templatePath;
	}
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getLowerName() {
		return lowerName;
	}
	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
	}
	
	/**
	 * 先将tableName转换后赋值给className
	 * @param tableName
	 */
	public void setClassNameFromTableName(String tableName)
	{
		String temp = DbUtil.getTablesNameToClassName(tableName);
		this.setClassName(temp);
	}
	/***
	 * 将首字母大写转换为首字母小写的格式
	 * @param className
	 */
	public void setLowerNameFromClassName(String className) {
		String temp = className.substring(0, 1).toLowerCase() 
				+ className.substring(1, className.length());
		this.setLowerName(temp);
	}
	/**
	 * 使用项目路径和模板路径,拼接为全路径
	 * @param projectPath
	 * @param templatePath
	 */
	public void setFullTemplatePath(String projectPath ,String templatePath) {
		String temp = projectPath + "/" + templatePath;
		this.fullTemplatePath = temp;
	}
	public String getFullTemplatePath() {
		return fullTemplatePath;
	}
	public boolean isReplaceable() {
		return replaceable;
	}
	public void setReplaceable(boolean replaceable) {
		this.replaceable = replaceable;
	}
	
	
}
