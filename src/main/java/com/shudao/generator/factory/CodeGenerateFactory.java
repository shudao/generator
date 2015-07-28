package com.shudao.generator.factory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.shudao.generator.data.ColumnData;
import com.shudao.generator.data.CommonPageParser;
import com.shudao.generator.data.CreateBean;
import com.shudao.generator.def.CodeResourceUtil;

public class CodeGenerateFactory {

	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

	/**
	 * @Description:
	 * @Author:dingbs
	 * @Since:2015年5月25日
	 * @param tableName 表名
	 * @param moduleName 中文注释名
	 * @param packageName 包名
	 */
	public static void codeGenerate(String tableName, String moduleName, String packageName) {

		String packagePath = packageName.replace(".", "\\");
		String url = CodeResourceUtil.getURL();
		String username = CodeResourceUtil.getUSERNAME();
		String passWord = CodeResourceUtil.getPASSWORD();

		CreateBean createBean = new CreateBean(url, username, passWord);

		String className = createBean.getTablesNameToClassName(tableName);
		String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());

		VelocityContext context = new VelocityContext();

		context.put("packageName", packageName);
		context.put("className", className);
		context.put("lowerClassName", lowerName);
		context.put("lowerName", lowerName);
		context.put("tableName", tableName);
		context.put("entityPackage", packageName);
		context.put("newLine", "\n");
		context.put("tableLine", "\t");
		context.put("jquery", "$");

		// 添加注释相关信息
		context.put("createTime", formatter.format(new Date()));
		context.put("author", CodeResourceUtil.CODE_AUTHOR);
		context.put("moduleName", moduleName);
		try {
			// 获取javabean 属性的类型
			List<ColumnData> columnDatas = createBean.getColumnDatas(tableName);
			String importPackageName = getPkgFromColumnData(columnDatas);
			context.put("importPackageName", importPackageName);
			context.put("columnDatas", columnDatas);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 生成Model
		//String modelPath = CodeResourceUtil.MODEL_SOURCE_CODE_PATH + "\\src\\main\\java\\";
		String modelPath = CodeResourceUtil.MODEL_SOURCE_CODE_PATH;
		String modelBeanPath = packagePath   + className + ".java";
		CommonPageParser.WriterPage(context, "EntityTemplate.vm", modelPath, modelBeanPath);

		// 生成ModelMapperXml
		String mapperResourcePath = CodeResourceUtil.DAO_SOURCE_CODE_PATH ;
		String mapperResourceXmlPath = packagePath   + className + "Mapper.xml";
		CommonPageParser.WriterPage(context, "MapperXmlTemplate.vm", mapperResourcePath, mapperResourceXmlPath);

		// 生成ModelMapper
		String mapperPath = CodeResourceUtil.DAO_SOURCE_CODE_PATH + "\\src\\main\\java\\";
		String mapperInterfacePath = packagePath   + className + "Mapper.java";
		CommonPageParser.WriterPage(context, "MapperTemplate.vm", mapperPath, mapperInterfacePath);

		// 生成Service接口
		String serviceInterfacePath = CodeResourceUtil.SERVICE_SOURCE_CODE_PATH + "\\src\\main\\java\\";
		String serviceInterfaceFilePath = packagePath +   className + "Service.java";
		CommonPageParser.WriterPage(context, "ServiceTemplate.vm", serviceInterfacePath, serviceInterfaceFilePath);

		// 生成Service接口
		String serviceImplPath = CodeResourceUtil.SERVICE_SOURCE_CODE_PATH + "\\src\\main\\java\\";
		String serviceImplFilePath = packagePath + "\\service\\impl\\" + className + "ServiceImpl.java";
		CommonPageParser.WriterPage(context, "ServiceImplTemplate.vm", serviceImplPath, serviceImplFilePath);

		// 生成Controller接口
		String controllerPath = CodeResourceUtil.CONTROLLER_SOURCE_CODE_PATH + "\\src\\main\\java\\";
		String controllerFilePath = packagePath + "\\web\\" + className + "Controller.java";
		CommonPageParser.WriterPage(context, "ControllerTemplate.vm", controllerPath, controllerFilePath);

		// 生成列表主页
		String gridIndeVmPath = CodeResourceUtil.CONTROLLER_SOURCE_CODE_PATH + "\\src\\main\\webapp\\";
		String gridIndeVmFilePath = "WEB-INF\\velocity\\" + lowerName + "\\" + "index.vm";
		CommonPageParser.WriterPage(context, "index.vm", gridIndeVmPath, gridIndeVmFilePath);
		// 生成主页列表js
		String gridIndeJsPath = CodeResourceUtil.CONTROLLER_SOURCE_CODE_PATH + "\\src\\main\\webapp\\";
		String gridIndeJsFilePath = "scripts\\" + lowerName + "\\" + "index.js";
		CommonPageParser.WriterPage(context, "index.js.vm", gridIndeJsPath, gridIndeJsFilePath);

		// 生成详细页面
		String detilPath = CodeResourceUtil.CONTROLLER_SOURCE_CODE_PATH + "\\src\\main\\webapp\\";
		String detailVmPath = "WEB-INF\\velocity\\" + lowerName + "\\" + "detail.vm";
		CommonPageParser.WriterPage(context, "detail.vm", detilPath, detailVmPath);

		// 生成主页列表js
		String detailJsPath = CodeResourceUtil.CONTROLLER_SOURCE_CODE_PATH + "\\src\\main\\webapp\\";
		String detailJsFilePath = "scripts\\" + lowerName + "\\" + "detail.js";
		CommonPageParser.WriterPage(context, "detail.js.vm", detailJsPath, detailJsFilePath);

		// 生成详细页面
		String infoPath = CodeResourceUtil.CONTROLLER_SOURCE_CODE_PATH + "\\src\\main\\webapp\\";
		String infoVmPath = "WEB-INF\\velocity\\" + lowerName + "\\"  + "info.vm";
		CommonPageParser.WriterPage(context, "info.vm", infoPath, infoVmPath);

		// 生成查看详细js
		String infoJsPath = CodeResourceUtil.CONTROLLER_SOURCE_CODE_PATH + "\\src\\main\\webapp\\";
		String infoJsFilePath = "scripts\\" + lowerName + "\\"  + "info.js";
		CommonPageParser.WriterPage(context, "info.js.vm", infoJsPath, infoJsFilePath);

		System.out.println("生成文件完毕。");
	}

	private static String getPkgFromColumnData(List<ColumnData> columnDatas) {
		StringBuffer sb = new StringBuffer();
		for (ColumnData column : columnDatas) {
			String dataType = column.getDataType();
			if (!dataType.contains(".lang.") && !sb.toString().contains(dataType)) {
				// 如果所属类型在java.lang 包下 就不用用
				sb.append("import ").append(dataType).append(";\n");
			}
		}
		return sb.toString();
	}

	public static String getProjectPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}
}