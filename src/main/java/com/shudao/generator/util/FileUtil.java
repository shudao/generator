package com.shudao.generator.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.shudao.generator.model.CodeModel;
import com.shudao.generator.model.FileModel;

/**
 * 文件工具类
 * 
 * @author dali
 * 
 */
public class FileUtil {

	/***
	 * 获取当前模板
	 * 
	 * @param code
	 * @return
	 */
	public static List<File> getTemplateDirs(CodeModel code) {
		//获取template地址
		File file = new File(code.getFullTemplatePath());
		//获取template下路径
		File [] dirs = file.listFiles();
		//用于存放真正的模板文件
		List<File> files = new ArrayList<File>();
		for (File f : dirs) {
			for(File temp:f.listFiles())
			{
				files.add(temp);
			}
		}
		return files;
	}

	/***
	 * 获取当前模板的FileModel
	 * 
	 * @param filePath
	 * @param fileName
	 * @param templateName
	 * @return
	 */
	public static FileModel getFileModelInstance(File file,CodeModel code) {
		FileModel fm = new FileModel();
		fm.setTemplatePath(file.getParent());
		fm.setTemplateName(file.getName());
		fm.setOutName(delDotVmSuffix(file.getName(),code.getClassName()));
		fm.setOutPath(getOutPath(file));
		return fm;
	}

	/**
	 * 删除文件的.vm后缀
	 * 
	 * @param templateName
	 * @return
	 */
	private static String delDotVmSuffix(String templateName,String className) {
		String full = templateName.substring(0, templateName.length() - 3);
		String outputName = className + full;
		return outputName;
	}
	/**
	 * 根据模板所在父路径,获取输出路径
	 * @param file
	 * @return
	 */
	private static String getOutPath(File file)
	{
		return ResourceUtil.getCodebundle().getString(file.getParentFile().getName());
	}
}
