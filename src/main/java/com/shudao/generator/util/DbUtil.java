package com.shudao.generator.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.shudao.generator.model.ColumnModel;
import com.shudao.generator.model.DbModel;

public class DbUtil {
	private static DbModel db;
	
	static
	{
		db = ResourceUtil.getDbInstance();
	}
	
	public DbUtil(){}

	/***
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(db.getDriverName());
		return DriverManager.getConnection(db.getUrl(), db.getUserName(), db.getPassword());
	}
	
	/***
	 * 获取指定数据库的表名s
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static List<String> getTables() throws SQLException, ClassNotFoundException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement("show tables");
		ResultSet rs = ps.executeQuery();
		List<String> list = new ArrayList<String>();
		while (rs.next()) {
			String tableName = rs.getString(1);
			list.add(tableName);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}

	/***
	 *  根据表名获取列数据
	 * @param tableName
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public static List<ColumnModel> getColumnDatas(String tableName) throws SQLException, ClassNotFoundException {
		String SQLColumns = "select column_name ,"
				+ "data_type,column_comment,0,0,"
				+ "character_maximum_length,is_nullable nullable "
				+ "from information_schema.columns "
				+ "where table_name =  '"
				+ tableName
				+ "' "
				+ "and table_schema =  '"
				+ db.getDatabaseName() +"'";

		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List<ColumnModel> columnList = new ArrayList<ColumnModel>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String name = rs.getString(1).toLowerCase();
			String type = rs.getString(2);
			String comment = rs.getString(3);
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = rs.getString(6) == null ? "" : rs.getString(6);
			String nullable = getNullAble(rs.getString(7));
			type = getType(type, precision, scale);
			
			ColumnModel cd = new ColumnModel();
			cd.setColumnName(name);
			cd.setDataType(type);
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			cd.setPropertyName(camelName(name));
			String tempPro = cd.getPropertyName();
			cd.setUpperPropertyName(tempPro.replaceFirst(tempPro.substring(0, 1), tempPro.substring(0, 1).toUpperCase()));
			cd.setDataShortType(StringUtils.substringAfterLast(type, "."));
			formatFieldClassType(cd);
			columnList.add(cd);
		}
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}

	/**
	 * 为easyui转换数据格式
	 * @param columnt
	 */
	public static void formatFieldClassType(ColumnModel columnt) {
		String fieldType = columnt.getColumnType();
		String scale = columnt.getScale();
		if ("N".equals(columnt.getNullable())) {
			columnt.setOptionType("required:true");
		}
		if (("datetime".equals(fieldType)) || ("time".equals(fieldType))) {
			columnt.setClassType("easyui-datetimebox");
		} else if ("date".equals(fieldType)) {
			columnt.setClassType("easyui-datebox");
		} else if ("int".equals(fieldType)) {
			columnt.setClassType("easyui-numberbox");
		} else if ("number".equals(fieldType)) {
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
				columnt.setClassType("easyui-numberbox");
				if (StringUtils.isNotBlank(columnt.getOptionType()))
					columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
				else
					columnt.setOptionType("precision:2,groupSeparator:','");
			} else {
				columnt.setClassType("easyui-numberbox");
			}
		} else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {
			columnt.setClassType("easyui-numberbox");
			if (StringUtils.isNotBlank(columnt.getOptionType()))
				columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
			else
				columnt.setOptionType("precision:2,groupSeparator:','");
		} else {
			columnt.setClassType("easyui-validatebox");
		}
	}

	/**
	 * 获取数据类型
	 * @param dataType
	 * @param precision
	 * @param scale
	 * @return
	 */
	public static String getType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "java.lang.String";
		else if (dataType.contains("int"))
			dataType = "java.lang.Integer";
		else if (dataType.contains("float"))
			dataType = "Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
				dataType = "java.lang.BigDecimal";
			else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
				dataType = "java.lang.Long";
			else
				dataType = "java.lang.Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "java.util.Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}

	public static void getPackage(int type, String createPath, String content, String packageName, String className,
			String extendsClassName, String[] importName) throws Exception {
		if (packageName == null) {
			packageName = "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; i++) {
			sb.append("import ").append(importName[i]).append(";\r");
		}
		sb.append("\r");
		sb.append("/**\r *  entity. @author wolf Date:"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (extendsClassName != null) {
			sb.append(" extends ").append(extendsClassName);
		}
		if (type == 1)
			sb.append(" ").append("implements java.io.Serializable {\r");
		else {
			sb.append(" {\r");
		}
		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp = temp + className.substring(1, className.length());
		if (type == 1) {
			sb.append("private " + className + " " + temp + "; // entity ");
		}
		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		createFile(createPath, "", sb.toString());
	}

	/***
	 * 根据表名转换为类名
	 * @param tableName
	 * @return
	 */
	public static String getTablesNameToClassName(String tableName) {
		String[] split = tableName.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < split.length; i++) {
				String tempTableName = split[i].substring(0, 1).toUpperCase()
						+ split[i].substring(1, split[i].length());
				sb.append(tempTableName);
			}

			return sb.toString();
		}
		String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
		return tempTables;
	}

	/***
	 * 创建文件
	 * @param path
	 * @param fileName
	 * @param str
	 * @throws IOException
	 */
	public static void createFile(String path, String fileName, String str) throws IOException {
		FileWriter writer = new FileWriter(new File(path + fileName));
		writer.write(new String(str.getBytes("utf-8")));
		writer.flush();
		writer.close();
	}


	public static String getColumnSplit(List<ColumnModel> columnList) throws SQLException {
		StringBuffer commonColumns = new StringBuffer();
		for (ColumnModel data : columnList) {
			commonColumns.append(data.getColumnName() + "|");
		}
		return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。
	 * 例如：HelloWorld->HELLO_WORLD
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
	 * 例如：HELLO_WORLD->HelloWorld
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母小写
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}
	/**
	 * 判断数据库字段是否允许为空
	 * @param nullable
	 * @return
	 */
	public static String getNullAble(String nullable) {
		if (("YES".equals(nullable)) || ("yes".equals(nullable))
				|| ("y".equals(nullable)) || ("Y".equals(nullable))) {
			return "Y";
		}
		if (("NO".equals(nullable)) || ("N".equals(nullable))
				|| ("no".equals(nullable)) || ("n".equals(nullable))) {
			return "N";
		}
		return null;
	}

	public static String getPkgFromColumnData(List<ColumnModel> columnDatas) {
		StringBuffer sb = new StringBuffer();
		for (ColumnModel column : columnDatas) {
			String dataType = column.getDataType();
			if (!dataType.contains(".lang.") && !sb.toString().contains(dataType)) {
				// 如果所属类型在java.lang 包下 就不用用
				sb.append("import ").append(dataType).append(";\n");
			}
		}
		return sb.toString();
	}
}