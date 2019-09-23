## GXH COMMUNITY

## 部署
### 依赖
- 安装git  
- 安装jdk  
- maven  
- mysql
## 步骤   
- yum install git 
- mkdir App
- cd App
- git clone https://github.com/1320546563/community.git  
- yum install maven  
- mvn clean compile package
- cp  src/main/resources/application.properties  src/main/resources/application-production.properties  
- vim src/main/resources/application-production.properties  
- mvn package  
- java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar

##资料  
[spring 文档](http://spring.io/guides)  
[Spring web](http://spring.io/guides/gs/serving-web-content/)  
[es社区](http://elasticsearch.cn/explore)  
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[Spring Web Mvc](https://docs.spring.io/spring/docs/5.1.9.RELEASE/spring-framework-reference/web.html#spring-web)  
[mybatis generator](http://www.mybatis.org/generator/reference/plugins.html)
## 工具
[git](http://git-scm.com/download)  
[Lombok](http://www.projectlombok.org)  
 
