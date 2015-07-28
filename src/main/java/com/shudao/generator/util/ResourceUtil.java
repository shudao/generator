package com.shudao.generator.util;

import java.util.ResourceBundle;

import com.shudao.generator.model.CodeModel;
import com.shudao.generator.model.DbModel;

/***
 * 属性解析器
 * @author dali
 *
 */
public class ResourceUtil {
	
	//加载与数据库设置有关的配置文件
	private static final ResourceBundle dbBundle = ResourceBundle
			.getBundle("db");
	
	//加载与代码生成有关的配置文件
	private static final ResourceBundle codeBundle = ResourceBundle
			.getBundle("code");
	
	//code单例
	private static CodeModel code;
	//db 单例
	private static DbModel db;
	
	//避免实例化使用
	private ResourceUtil()
	{}
	
	//获取数据库驱动类名
	private static String getDriverName() {
		return dbBundle.getString("driver_name");
	}

	//获取数据库地址
	private static String getUrl() {
		return dbBundle.getString("url");
	}

	//获取数据库用户名
	private static String getUserName() {
		return dbBundle.getString("username");
	}

	//获取数据库密码
	private static String getPassword() {
		return dbBundle.getString("password");
	}

	//获取数据库名称
	private static String getDatabaseName() {
		return dbBundle.getString("database_name");
	}
	
	//获取要解析的表名s
	private static String  getTableName() {
		String tableName = dbBundle.getString("tables");
		return tableName;
	}
	
	//获取model文件的输出路径
	private static String getModelPath(){
		return codeBundle.getString("model");
	}
	
	//获取dao文件的输出路径
	private static String getDaoPath(){
		return codeBundle.getString("dao");
	}
	
	//获取service文件的输出路径
	private static String getServicePath(){
		return codeBundle.getString("service");
	}
	
	//获取控制器文件的输出路径
	private static String getControllerPath(){
		return codeBundle.getString("controller");
	}
	
	//获取用于代码注释的作者名
	private static String getAuthor(){
		return codeBundle.getString("author");
	}
	
	//获取代码的命名空间
	private static String getPackageName(){
		return codeBundle.getString("package_name");
	}
	
	//获取模块的中文名称,生成注释
	private static String getModuleName()
	{
		return codeBundle.getString("module_name");
	}
	
	//获取模块的中文名称,生成注释
	private static String getReplaceable()
	{
		return codeBundle.getString("replaceable");
	}
	
	//获取当前项目目录
	public static String getProjectPath()
	{
		return System.getProperty("user.dir").replace("\\", "/");
	}
	
	//获取当前项目目录
	public static String getTemplatePath()
	{
		return codeBundle.getString("template_path");
	}
	
	//判断是否允许代码覆盖
	public static boolean isReplaceable()
	{
		String is = getReplaceable();
		if( is !=null && is.trim().equals("true"))
		{
			return true;
		}
		else if(is != null && is.trim().equals("false"))
		{
			return false;
		}
		else if(is == null)
		{
			return false;
		}
		return false;
	}
	
	//获取读取的dbBundle
	public static ResourceBundle getDbbundle() {
		return dbBundle;
	}
	//获取读取的dbBundle
	public static ResourceBundle getCodebundle() {
		return codeBundle;
	}

	/***
	 * 单例模式的Code实例
	 * @return
	 */
	public synchronized static CodeModel getCodeInstance()
	{
		if(code == null)
		{
			code = new CodeModel();
			code.setAuthor(getAuthor());
			code.setControllerPath(getControllerPath());
			code.setDaoPath(getDaoPath());
			code.setModelPath(getModelPath());
			code.setModuleName(getModuleName());
			code.convertPackageName(getPackageName());
			code.setServicePath(getServicePath());
			code.setTemplatePath(getTemplatePath());
			code.setClassNameFromTableName(getTableName());
			code.setLowerNameFromClassName(code.getClassName());
			code.setReplaceable(isReplaceable());
			code.setFullTemplatePath(getProjectPath(), getTemplatePath());
			return code;
		}
		return code;
	}
	
	/**
	 * 单例模式的Database实例
	 * @return
	 */
	public static synchronized DbModel getDbInstance()
	{
		if( db == null )
		{
			db = new DbModel();
			db.setDatabaseName(getDatabaseName());
			db.setDriverName(getDriverName());
			db.setPassword(getPassword());
			db.setTableName(getTableName());
			db.setUrl(getUrl());
			db.setUserName(getUserName());
			return db;
		}
		return db;
	}
}