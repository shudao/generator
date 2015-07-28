package com.shudao.generator.def;

import java.util.ResourceBundle;

/***
 * 属性解析器
 * @author dali
 *
 */
public class ResourceUtil {
	
	//加载与数据库设置有关的配置文件
	private static final ResourceBundle bundle = ResourceBundle
			.getBundle("database");
	
	//加载与代码生成有关的配置文件
	private static final ResourceBundle bundlePath = ResourceBundle
			.getBundle("code");
	
	//获取当前项目根路径,以获取模板文件路径
	private static final String projectPath = System.getProperty("user.dir").replace("\\", "/") + "/";
	
	//避免实例化使用
	private ResourceUtil()
	{}
	
	//获取数据库驱动类名
	public static String getDriverName() {
		return bundle.getString("driver_name");
	}

	//获取数据库地址
	public static String getUrl() {
		return bundle.getString("url");
	}

	//获取数据库用户名
	public static String getUserName() {
		return bundle.getString("username");
	}

	//获取数据库密码
	public static String getPassword() {
		return bundle.getString("password");
	}

	//获取数据库名称
	public static String getDatabaseName() {
		return bundle.getString("database_name");
	}
	
	//获取要解析的表名s
	public static String  getTableName() {
		String tableName = bundle.getString("tables");
		return tableName;
	}
	
	//获取model文件的输出路径
	public static String getModeSourceCodePath(){
		return bundlePath.getString("model_path");
	}
	
	//获取dao文件的输出路径
	public static String getDaoSourceCodePath(){
		return bundlePath.getString("dao_path");
	}
	
	//获取service文件的输出路径
	public static String getServiceSourceCodePath(){
		return bundlePath.getString("service_path");
	}
	
	//获取控制器文件的输出路径
	public static String getControllerSourceCodePath(){
		return bundlePath.getString("controller_path");
	}
	
	//获取用于代码注释的作者名
	public static String getCodeAuthor(){
		return bundlePath.getString("code_author");
	}
	
	//获取代码的命名空间
	public static String getPackageName(){
		return bundlePath.getString("package_name");
	}
	
	//获取模块的中文名称,生成注释
	public static String getModuleName()
	{
		return bundlePath.getString("module_name");
	}
	
	//获取当前项目目录
	public static String getProjectPath()
	{
		return projectPath;
	}
}