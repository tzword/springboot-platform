### 项目介绍
  本项目是基于springboot的一个通用基础框架，里面包含一些基础组件，工具类和代码生成工具。

### 组织架构

```
docker
├─Dockfile docker配置文件
│
java
│
├─common 公共模块
│  ├─base Base继承通用类
│  ├─config springBoot所有配置
│  ├─dataSources 多数据源配置包
│  ├─domain 统一返回包
│  ├─exception 异常处理包
│  ├─gen 代码生成工具
│       └─CodeGenerator 代码生成工具启动类
|  ├─kafka 生产和消费
|  ├─rsa ras加密解密组件
|  ├─shiro shiro配置包
|  ├─sign 验签组件(md5,sha1)
|  ├─sysenum enum枚举包
│  └─xss xss过滤器
│
├─controller 请求访问模块（可以自动生成）
│
├─entity 实体模块（可以自动生成）
│
├─mapper mapper模块(可以自动生成)
│
├─req 传入的参数实体
│
├─resp 传出的参数实体
│
├─Service 服务层模块(可以自动生成)
│  └─impl service实现类
│
├─util 工具模块
│
├─SpringbootPlatformApplication 启动类
│ 
│
├─resources 配置文件夹
│  ├─mapper mapper.xml文件(可以自动生成)
│  ├─static 静态文件目录
│  ├─templates 静态文件目录
│  │   ├─controller.java.ftl controller层代码模板
│  │   └─mapper.xml.ftl mapper层代码模板
│  ├─application.yml springboot配置
│  ├─application-dev.yml 开发环境配置
│  ├─application-prod.yml 生产环境配置
│  ├─application-test.yml 测试环境配置
│  ├─code-generator.properties 代码生成工具配置类
│  └─logback-spring.xml log日志配置类
│  
└─pom.xml   maven.xml


```


###技术选项

技术|名称|
---|---|
springboot|springboot框架 ||
Apache Shiro|权限框架||
MyBatis-plus Generator|代码生成||
PageHelper|MyBatis物理分页插件||
redisTemplate|redis连接工具||
Log4J|日志组件||
Swagger2|接口测试框架||
Maven|项目构建管理||
docker|docker构建工具||
freemarker|模板引擎||
GSON|谷歌json||
druid|阿里连接池||
DynamicDataSource|主从数据源配置||
rabbitmq|rabbitmq配置||
kafka|kafka配置||
ras|rsa加密||
sign|验签插件||


### 开发环境
- JDK8.0
- mysql5.7或者mysql8.0
- eclipse,idea

### 代码生成工具使用流程
1.需要修改code-generator.properties的配置  
2.如果需要修改生成模板，就在templates目录下修改生成的模板(一般不建议修改)  
3.执行gen包下面的CodeGenerator,启动就可以了  

### 部署流程
1. 修改application-*.yml 里面自己数据库版本对应的jdbc链接信息(是5.7还是8.0)  
2. 修改application-*.yml 里面redis的配置,rabbitmq的配置,kafka的配置
3. 测试环境日志是输出到控制台，生产环境日志输出到磁盘，如果部署生产需要修改 logback-spring.xml 的日志路径
`<property name="LOG_HOME" value="d:\\platform-log" />`  
4. 修改dockerfile文件的配置

### 主从数据源的使用
1.修改application-*.yml 里的

    slave:
        enabled : true
        
2.在serviceImpl的方法上加上@DataSource标签，指定是哪个数据源  

    //主库数据源
    @DataSource(value = DataSourceType.MASTER)
    public User testMasterGetById(long id) {
       //代码省略
    }
    
    //从库数据源
    @DataSource(value = DataSourceType.SLAVE)
    public User testSlaveGetById(long id) {
        //代码省略
    }       

### swagger的使用
1.在类上配置 `@Api(value = "XXX管理", tags = {"XXX管理"})`  
2.在方法上配置 `@ApiOperation(value = "XXX", notes = "XXX")`  
3.访问：http://服务名:端口号/swagger-ui.html  
**注意**：application-*.yml 里的

    swagger:
        swagger-ui-open : true
        host: localhost:8088
        
在dev或者test下默认swagger-ui-open是true,也就是开启swagger,host根据环境的地址进行设置
在prod环境下需要设置成 false。
  

### 注意事项
 _原有的  
 controller(UserController.java)  
 entity(User.java)  
 mapper(UserMapper.java)  
 service(IUserService.java)  
 impl(UserServiceImpl.java)  
 resources/mapper(UserMapper.xml)  
 为生成的示例代码，实际开发需要删除。_