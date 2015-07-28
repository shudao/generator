package com.shudao.generator.builder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.shudao.generator.model.FileModel;
import com.shudao.generator.util.ResourceUtil;

public class PageMerge {
	
	/***
	 *  执行模板和数据合并,并生成文件
	 * @param context
	 * @param templateName
	 * @param fileDirPath
	 * @param outName
	 * @throws Exception 
	 */
	public static void merge(Properties props,VelocityContext vc,FileModel fm) throws Exception  {
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		VelocityEngine ve = null;
		try {
			File file = new File(fm.getOutPath() + "//" + fm.getOutName());
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

			//初始化velocity引擎
			ve = new VelocityEngine();
			if(props.get("file.resource.loader.path") != null)
			{
				props.remove("file.resource.loader.path");
			}
			props.setProperty("file.resource.loader.path", fm.getTemplatePath());
			ve.init(props);
			//获取模板
			Template template = ve.getTemplate(fm.getTemplateName(), "UTF-8");
			//输出生成的代码
			fos = new FileOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(
					fos, "UTF-8"));
			template.merge(vc, writer);
			writer.flush();
		} catch (Exception e) {
			System.out.println("数据与模板合并失败:PageMerge:merge()");
			throw e;
		}
		finally
		{
			writer.close();
			fos.close();
		}
	}
}