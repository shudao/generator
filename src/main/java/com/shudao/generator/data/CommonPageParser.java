package com.shudao.generator.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.shudao.generator.def.ResourceUtil;

public class CommonPageParser {
	private static VelocityEngine ve;

	private static boolean isReplace = true;

	static {
		try {
			String templatePath = ResourceUtil.getProjectPath()
					+ "src/main/resources/template/";
			
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "file");
			properties.setProperty("file.resource.loader.description",
					"Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.path",
					templatePath);
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

	/***
	 *  执行模板和数据合并,并生成文件
	 * @param context
	 * @param templateName
	 * @param fileDirPath
	 * @param targetFile
	 * @throws IOException
	 */
	public static void WriterPage(VelocityContext context, String templateName,
			String fileDirPath, String targetFile) throws IOException {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			File file = new File(fileDirPath + "//" +targetFile);
			new File(file.getParent()).mkdirs();
			if (file.exists()) {
				System.out.println("覆盖生成文件："+file.getAbsolutePath());
			} else if (isReplace) {
				System.out.println("已生成："+file.getAbsolutePath());
				file.createNewFile();
			}

			Template template = ve.getTemplate(templateName, "UTF-8");
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(
					fos, "UTF-8"));
			template.merge(context, writer);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			writer.close();
			fos.close();
		}
	}
}