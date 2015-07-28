package com.shudao.generator.builder;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.shudao.generator.model.CodeModel;
import com.shudao.generator.model.DbModel;
import com.shudao.generator.util.ResourceUtil;
import com.shudao.generator.util.VelocityUtil;

public class Builder {

	/**
	 * 生成操作
	 * 
	 */
	public void generate() {
		try {
			// 获取单例的CodeModel
			CodeModel code = ResourceUtil.getCodeInstance();
			// 获取单例的DbModel
			DbModel db = ResourceUtil.getDbInstance();
			// 获取单例的VelocityContext
			VelocityContext vc = VelocityUtil.getVelocityContextInstance(code,
					db);
			// 获取单例的VelocityEngine
			VelocityEngine ve = VelocityUtil.getVelocityEngineInstance(code);

			// 生成Model
			// String modelPath = CodeResourceUtil.MODEL_SOURCE_CODE_PATH +
			// "\\src\\main\\java\\";
			String modelPath = code.getModelPath();
			String modelBeanPath = code.getPackageName() + code.getClassName()
					+ ".java";
			CommonPageParser.merge(ve, vc, "EntityTemplate.vm", modelPath,
					modelBeanPath);

			// 生成ModelMapperXml
			String mapperResourcePath = code.getDaoPath();
			String mapperResourceXmlPath = code.getPackageName()
					+ code.getClassName() + "Mapper.xml";
			CommonPageParser.merge(ve, vc, "MapperXmlTemplate.vm",
					mapperResourcePath, mapperResourceXmlPath);

			// 生成ModelMapper
			String mapperPath = code.getDaoPath() + "\\src\\main\\java\\";
			String mapperInterfacePath = code.getPackageName()
					+ code.getClassName() + "Mapper.java";
			CommonPageParser.merge(ve, vc, "MapperTemplate.vm",
					mapperPath, mapperInterfacePath);

			// 生成Service接口
			String serviceInterfacePath = code.getServicePath()
					+ "\\src\\main\\java\\";
			String serviceInterfaceFilePath = code.getPackageName()
					+ code.getClassName() + "Service.java";
			CommonPageParser.merge(ve, vc, "ServiceTemplate.vm",
					serviceInterfacePath, serviceInterfaceFilePath);

			// 生成Service接口
			String serviceImplPath = code.getServicePath()
					+ "\\src\\main\\java\\";
			String serviceImplFilePath = code.getPackageName()
					+ "\\service\\impl\\" + code.getClassName()
					+ "ServiceImpl.java";
			CommonPageParser.merge(ve, vc, "ServiceImplTemplate.vm",
					serviceImplPath, serviceImplFilePath);

			// 生成Controller接口
			String controllerPath = code.getControllerPath()
					+ "\\src\\main\\java\\";
			String controllerFilePath = code.getPackageName() + "\\web\\"
					+ code.getClassName() + "Controller.java";
			CommonPageParser.merge(ve, vc, "ControllerTemplate.vm",
					controllerPath, controllerFilePath);

			// 生成列表主页
			String gridIndeVmPath = code.getControllerPath()
					+ "\\src\\main\\webapp\\";
			String gridIndeVmFilePath = "WEB-INF\\velocity\\"
					+ code.getLowerName() + "\\" + "index.vm";
			CommonPageParser.merge(ve, vc, "index.vm", gridIndeVmPath,
					gridIndeVmFilePath);
			// 生成主页列表js
			String gridIndeJsPath = code.getControllerPath()
					+ "\\src\\main\\webapp\\";
			String gridIndeJsFilePath = "scripts\\" + code.getLowerName()
					+ "\\" + "index.js";
			CommonPageParser.merge(ve, vc, "index.js.vm", gridIndeJsPath,
					gridIndeJsFilePath);

			// 生成详细页面
			String detilPath = code.getControllerPath()
					+ "\\src\\main\\webapp\\";
			String detailVmPath = "WEB-INF\\velocity\\" + code.getLowerName()
					+ "\\" + "detail.vm";
			CommonPageParser.merge(ve, vc, "detail.vm", detilPath,
					detailVmPath);

			// 生成主页列表js
			String detailJsPath = code.getControllerPath()
					+ "\\src\\main\\webapp\\";
			String detailJsFilePath = "scripts\\" + code.getLowerName() + "\\"
					+ "detail.js";
			CommonPageParser.merge(ve, vc, "detail.js.vm", detailJsPath,
					detailJsFilePath);

			// 生成详细页面
			String infoPath = code.getControllerPath()
					+ "\\src\\main\\webapp\\";
			String infoVmPath = "WEB-INF\\velocity\\" + code.getLowerName()
					+ "\\" + "info.vm";
			CommonPageParser
					.merge(ve, vc, "info.vm", infoPath, infoVmPath);

			// 生成查看详细js
			String infoJsPath = code.getControllerPath()
					+ "\\src\\main\\webapp\\";
			String infoJsFilePath = "scripts\\" + code.getLowerName() + "\\"
					+ "info.js";
			CommonPageParser.merge(ve, vc, "info.js.vm", infoJsPath,
					infoJsFilePath);
			System.out.println("代码生成完毕!");
		} catch (Exception e) {
			System.out.println("生成失败:Builder.generate");
			e.printStackTrace();
		}
	}

}