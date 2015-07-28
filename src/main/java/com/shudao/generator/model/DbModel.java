package com.shudao.generator.model;

/***
 * database配置实体
 * @author dali
 *
 */
public class DbModel {

	//驱动名称
	private String driverName;
	//数据库路径
	private String url;
	//数据库名称
	private String databaseName;
	//数据库用户名
	private String userName;
	//数据库密码
	private String password;
	//数据库表名
	private String tableName;
	
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
