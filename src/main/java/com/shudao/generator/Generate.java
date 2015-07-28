package com.shudao.generator;

import com.shudao.generator.builder.Builder;

/***
 * 启动器
 * 
 * @author dali
 * 
 */
public class Generate {

	public static void main(String[] args) {
		try {
			Builder builder = new Builder();
			builder.generate();
		} catch (Exception e) {
			System.out.println("代码生成失败:Generate.main");
			e.printStackTrace();
		}
	}
}
