package com.shudao.generator.builder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.shudao.generator.util.ResourceUtil;

public class CommonPageParser {
	
	/***
	 *  执行模板和数据合并,并生成文件
	 * @param context
	 * @param templateName
	 * @param fileDirPath
	 * @param targetFile
	 * @throws IOException
	 */
	public static void merge(VelocityEngine ve,VelocityContext vc, String templateName,
			String fileDirPath, String targetFile) throws IOException  {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			File file = new File(fileDirPath + "//" +targetFile);
			//是否允许覆盖生成
			boolean replace = ResourceUtil.isReplaceable();
			new File(file.getParent()).mkdirs();
			if(file.exists())
			{
				if(replace){
					file.createNewFile();
					System.out.println("覆盖生成文件："+file.getAbsolutePath());
				}
				else if(!replace){
					throw new Exception("文件已存在,且当前设置为不可覆盖: " + file.getAbsolutePath());
				}
			}
			else if (!file.exists()) {
				file.createNewFile();
				System.out.println("已生成："+file.getAbsolutePath());
			}

			Template template = ve.getTemplate(templateName, "UTF-8");
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(
					fos, "UTF-8"));
			template.merge(vc, writer);
			writer.flush();
		} catch (Exception e) {
			System.out.println("");
			e.printStackTrace();
		}
		finally
		{
			writer.close();
			fos.close();
		}
	}
}