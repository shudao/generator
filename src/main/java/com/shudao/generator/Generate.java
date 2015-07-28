package com.shudao.generator;

import java.io.IOException;

import com.shudao.generator.factory.CodeGenerateFactory;

/***
 * 启动器
 * @author dali
 *
 */
public class Generate {

	public static void main(String[] args) throws IOException {

		CodeGenerateFactory.codeGenerate();
		
	}
}
