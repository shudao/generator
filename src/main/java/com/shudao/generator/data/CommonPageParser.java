package com.shudao.generator.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.shudao.generator.factory.CodeGenerateFactory;

public class CommonPageParser {
	private static VelocityEngine ve;

	private static boolean isReplace = true;

	static {
		try {
			String templateBasePath = CodeGenerateFactory.getProjectPath()
					+ "src/main/resources/template/";
			System.out.println(templateBasePath);
			
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "file");
			properties.setProperty("file.resource.loader.description",
					"Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.path",
					templateBasePath);
			properties.setProperty("file.resource.loader.cache", "true");
			properties.setProperty(
					"file.resource.loader.modificationCheckInterval", "30");
			properties.setProperty("runtime.log.logsystem.class",
					"org.apache.velocity.runtime.log.Log4JLogChute");
			properties.setProperty("runtime.log.logsystem.log4j.logger",
					"org.apache.velocity");
			properties.setProperty("directive.set.null.allowed", "true");
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.init(properties);
			ve = velocityEngine;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void WriterPage(VelocityContext context, String templateName,
			String fileDirPath, String targetFile) {
		try {
			File file = new File(fileDirPath + targetFile);
			new File(file.getParent()).mkdirs();
			if (file.exists()) {
				System.out.println("覆盖生成文件："+file.getAbsolutePath());
			} else if (isReplace) {
				System.out.println("已生成："+file.getAbsolutePath());
				file.createNewFile();
			}

			Template template = ve.getTemplate(templateName, "UTF-8");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fos, "UTF-8"));
			template.merge(context, writer);
			writer.flush();
			writer.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}