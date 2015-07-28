package com.shudao.generator;

import com.shudao.generator.factory.CodeGenerateFactory;


public class Generate {

	public static void main(String[] args) {

		//此处修改成你的 表名 和 中文注释
		String tableName = "tb_schedule"; //
		// 中文注释
		String moduleName = "物流公司";
		// 包名
		String packageName = "";

		CodeGenerateFactory.codeGenerate(tableName, moduleName, packageName);
	}
}
