package com.shudao.generator.util;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.shudao.generator.model.CodeModel;
import com.shudao.generator.model.ColumnModel;
import com.shudao.generator.model.DbModel;

/***
 * velocity引擎工具类
 * 
 * @author dali
 * 
 */
public class VelocityUtil {

	private static VelocityContext vc;

	private static VelocityEngine ve;

	/**
	 * 获取单例的VelocityContext
	 * 
	 * @param code
	 * @param db
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public synchronized static VelocityContext getVelocityContextInstance(
			CodeModel code, DbModel db) throws ClassNotFoundException,
			SQLException {
		try {
			if (vc == null) {
				vc = new VelocityContext();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
				vc.put("packageName", code.getPackageName());
				vc.put("className", code.getClassName());
				vc.put("lowerClassName", code.getLowerName());
				vc.put("lowerName", code.getLowerName());
				vc.put("tableName", db.getTableName());
				vc.put("entityPackage", code.getPackageName());
				vc.put("newLine", "\n");
				vc.put("tableLine", "\t");
				vc.put("jquery", "$");
				// 添加注释相关信息
				vc.put("createTime", formatter.format(new Date()));
				vc.put("author", code.getAuthor());
				vc.put("moduleName", code.getModuleName());
				// 获取javabean 属性的类型
				List<ColumnModel> columnDatas = DbUtil.getColumnDatas(db
						.getTableName());
				String importPackageName = DbUtil
						.getPkgFromColumnData(columnDatas);
				vc.put("importPackageName", importPackageName);
				vc.put("columnDatas", columnDatas);
				return vc;
			}
			return vc;
		} catch (Exception e) {
			System.out
					.println("初始化VelocityContext失败:VelocityUtil.getVelocityContextInstance");
		}
		return vc;
	}

	public synchronized static VelocityEngine getVelocityEngineInstance(
			CodeModel cm) {
		try {
			if (ve == null) {
				ve = new VelocityEngine();
				Properties properties = new Properties();
				properties.setProperty("resource.loader", "file");
				properties.setProperty("file.resource.loader.description",
						"Velocity File Resource Loader");
				properties.setProperty("file.resource.loader.path",
						cm.getFullTemplatePath());
				properties.setProperty("file.resource.loader.cache", "true");
				properties.setProperty(
						"file.resource.loader.modificationCheckInterval", "30");
				properties.setProperty("runtime.log.logsystem.class",
						"org.apache.velocity.runtime.log.Log4JLogChute");
				properties.setProperty("runtime.log.logsystem.log4j.logger",
						"org.apache.velocity");
				properties.setProperty("directive.set.null.allowed", "true");
				ve.init(properties);
				return ve;
			}
			return ve;
		} catch (Exception e) {
			System.out
					.println("初始化VelocityEngine失败:VelocityUtil.getVelocityEngineInstance");
			e.printStackTrace();
		}
		return ve;
	}

}
