package com.shudao.generator.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;

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

	private static Properties props;

	/**
	 * 获取单例的VelocityContext
	 * 
	 * @param code
	 * @param db
	 * @return
	 * @throws Exception 
	 */
	public synchronized static VelocityContext getVelocityContextInstance(
			CodeModel code, DbModel db) throws Exception {
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
			throw e;
		}
	}

	public synchronized static Properties getVelocityPropertiesInstance(
			CodeModel cm) throws Exception {
		try {
			if (props == null) {
				props = new Properties();
				props.setProperty("resource.loader", "file");
				props.setProperty("file.resource.loader.description",
						"Velocity File Resource Loader");
				props.setProperty("file.resource.loader.cache", "true");
				props.setProperty(
						"file.resource.loader.modificationCheckInterval", "30");
				props.setProperty("runtime.log.logsystem.class",
						"org.apache.velocity.runtime.log.Log4JLogChute");
				props.setProperty("runtime.log.logsystem.log4j.logger",
						"org.apache.velocity");
				props.setProperty("directive.set.null.allowed", "true");
				return props;
			}
			return props;
		} catch (Exception e) {
			System.out
					.println("初始化Properties失败:VelocityUtil.getVelocityPropertiesInstance");
			throw e;
		}
	}

}
