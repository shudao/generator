package com.shudao.generator.builder;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.VelocityContext;

import com.shudao.generator.model.CodeModel;
import com.shudao.generator.model.DbModel;
import com.shudao.generator.model.FileModel;
import com.shudao.generator.util.FileUtil;
import com.shudao.generator.util.ResourceUtil;
import com.shudao.generator.util.VelocityUtil;

public class Builder {

	/**
	 * 生成操作
	 * @throws Exception 
	 * 
	 */
	public void generate() throws Exception {
		try {
			// 获取单例的CodeModel
			CodeModel code = ResourceUtil.getCodeInstance();
			// 获取单例的DbModel
			DbModel db = ResourceUtil.getDbInstance();
			// 获取单例的VelocityContext
			VelocityContext vc = VelocityUtil.getVelocityContextInstance(code,
					db);
			// 获取单例的VelocityEngine
			Properties props = VelocityUtil.getVelocityPropertiesInstance(code);

			List<File> files  = FileUtil.getTemplateDirs(code);
			
			//遍历模板文件,生成代码
			for(File dir:files){
				FileModel fm = FileUtil.getFileModelInstance(dir,code);
				//得到template文件
				PageMerge.merge(props,vc,fm);
			}
			
			//提示完成
			System.out.println("代码生成完毕!");
			
		} catch (Exception e) {
			System.out.println("生成失败:Builder.generate()");
			throw e;
		}
	}

}