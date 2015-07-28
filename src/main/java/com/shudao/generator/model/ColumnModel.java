package com.shudao.generator.model;

/***
 * 数据列实体
 * @author dali
 *
 */
public class ColumnModel {
	//easyui必填项选项
	public static final String OPTION_REQUIRED = "required:true";
	//easyui数字格式精度
	public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
	//列名称
	private String columnName;
	//列数据类型
	private String dataType;
	//列数据类型简短
	private String dataShortType;
	//列注释
	private String columnComment;
	//TODO:
	private String columnType;
	//字符长度
	private String charmaxLength = "";
	//是否为空
	private String nullable;
	//TODO:
	private String scale;
	//精度
	private String precision;
	//TODO:
	private String classType = "";
	//TODO:
	private String propertyName;
	//TODO:
	private String upperPropertyName;
	//TODO:
	private String optionType = "";

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return this.columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getScale() {
		return this.scale;
	}

	public String getPrecision() {
		return this.precision;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getClassType() {
		return this.classType;
	}

	public String getOptionType() {
		return this.optionType;
	}

	public String getCharmaxLength() {
		return this.charmaxLength;
	}

	public String getNullable() {
		return this.nullable;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public void setCharmaxLength(String charmaxLength) {
		this.charmaxLength = charmaxLength;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getDataShortType() {
		return dataShortType;
	}

	public void setDataShortType(String dataShortType) {
		this.dataShortType = dataShortType;
	}

	public String getUpperPropertyName() {
		return upperPropertyName;
	}

	public void setUpperPropertyName(String upperPropertyName) {
		this.upperPropertyName = upperPropertyName;
	}
	
	
}