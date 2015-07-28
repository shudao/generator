此代码生成器用于生成 SpringMVC + Spring + Mybatis 代码,项目使用的是mysql,如果为其他数据库,可以修改pom.xml和db.properties


数据库：
注释每个column,用于代码注释和view字段,如名称,地址,号码
表名以tb_开头 单词之间用_隔开
字段统一小写，单词之间用_隔开

使用时应注意的事项：
db.properties  有数据库有关的配置
code.properties 与代码/模板有关的配置

执行方法:
com.shudao.generator.Generat.main

生成的有:
controller:index.js,index.vm,detail.js,detail.vm,info.vm,xxController.java
service:xxService.java,xxServiceImpl.java
dao:xxMapper.java,xxMapper.xml
model:xx.java

Mapper.xml：
<insert id="insertWithPK">
<delete id="deleteByPK">
<update id="updateByPK">
<select id="getModelByPK">
<select id="getListByPage">

service:
int insertWithPK (LogisticsCompany logisticsCompany);
void deleteByPK (int id);
void deleteByPK (Integer[] id);
int updateByPK (LogisticsCompany logisticsCompany);
T getModelByPK (int id);
List<T> getModelList(Map<String, Object> params);
Pager<T> getModelGrid(Integer page, Integer rows, Map<String, Object> params);

controller
对应的vm 文件以及 js

[2015-7-28] 
1.将类划分为model/util/builder三个主体部分
2.添加异常处理
3.精简代码架构
4.将模板按层分开