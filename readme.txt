table的命名规则：

表名以tb_开头 单词之间用_隔开
字段统一小写，单词之间用_隔开,

使用时应注意的事项：
database.properties 修改数据配置信息
code_path.properties 修改生成代码的路径

生成方法：
	/**
	 * @Description:
	 * @Author:dingbs
	 * @Since:2015年5月25日
	 * @param tableName 表名
	 * @param moduleName 中文注释名
	 * @param packageName 包名
	 */
CodeGenerateFactory.codeGenerate(tableName, moduleName, packageName);



生成的***Mapper.xml包含的方法：
<insert id="insertWithPK">
<delete id="deleteByPK">
<update id="updateByPK">
<select id="getModelByPK">
<select id="getListByPage">


对应的service

int insertWithPK (LogisticsCompany logisticsCompany);

void deleteByPK (int id);

void deleteByPK (Integer[] id);

int updateByPK (LogisticsCompany logisticsCompany);

T getModelByPK (int id);
	
List<T> getModelList(Map<String, Object> params);

Pager<T> getModelGrid(Integer page, Integer rows, Map<String, Object> params);


对应的controller

对应的vm 文件以及 js