package com.shudao.generator.def;

import java.util.ResourceBundle;

public class CodeResourceUtil {
	private static final ResourceBundle bundle = ResourceBundle
			.getBundle("database");
	private static final ResourceBundle bundlePath = ResourceBundle
			.getBundle("code_path");
	
	
	public static String DRIVER_NAME;
	public static String URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String DATABASE_NAME;
	
	public static String MODEL_SOURCE_CODE_PATH;
	public static String DAO_SOURCE_CODE_PATH;
	public static String SERVICE_SOURCE_CODE_PATH;
	public static String CONTROLLER_SOURCE_CODE_PATH;
	public static String CODE_AUTHOR;
	public static String packageName;
	
	static {
		DRIVER_NAME = getDRIVER_NAME();
		URL = getURL();
		USERNAME = getUSERNAME();
		PASSWORD = getPASSWORD();
		DATABASE_NAME = getDATABASE_NAME();

		MODEL_SOURCE_CODE_PATH = getModeSourceCodePath();
		DAO_SOURCE_CODE_PATH = getDaoSourceCodePath();
		SERVICE_SOURCE_CODE_PATH = getServiceSourceCodePath();
		CONTROLLER_SOURCE_CODE_PATH = getControllerSourceCodePath();
		CODE_AUTHOR = getCodeAuthor();
		packageName = getPackageName();
	}

	public static final String getDRIVER_NAME() {
		return bundle.getString("driver_name");
	}

	public static final String getURL() {
		return bundle.getString("url");
	}

	public static final String getUSERNAME() {
		return bundle.getString("username");
	}

	public static final String getPASSWORD() {
		return bundle.getString("password");
	}

	public static final String getDATABASE_NAME() {
		return bundle.getString("database_name");
	}
	
	public static final String getModeSourceCodePath(){
		return bundlePath.getString("model_source_root_package");
	}
	
	
	public static final String getDaoSourceCodePath(){
		return bundlePath.getString("dao_source_root_package");
	}
	
	public static final String getServiceSourceCodePath(){
		return bundlePath.getString("service_source_root_package");
	}
	
	public static final String getControllerSourceCodePath(){
		return bundlePath.getString("controller_source_root_package");
	}
	
	public static final String getCodeAuthor(){
		return bundlePath.getString("code_author");
	}
	public static final String getPackageName(){
		return bundlePath.getString("package_name");
	}
}