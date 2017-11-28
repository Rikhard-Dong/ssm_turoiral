# SSM 整合教程
### 快速开始
1. 下载到本地
2. 使用IDEA打开工程
3. 配置tomcat服务器
4. 修改数据库资源文件及创建数据库 
5. 运行

### 使用到的技术
1. spring 容器
2. springMVC 框架
3. mybatis 框架
4. druid 数据源
5. pageHelper 分页插件
6. maven构建项目
7. 前端使用bootstrap+jquery

### 项目结构
```
├── pom.xml
├── README.md
├── src
│   └── main
│       ├── java
│       │   └── io
│       │       └── ride
│       │           ├── dao
│       │           │   └── InfoDao.java
│       │           ├── dto
│       │           │   └── Result.java
│       │           ├── mapper
│       │           │   └── InfoMapper.xml
│       │           ├── model
│       │           │   └── Info.java
│       │           ├── service
│       │           │   ├── impl
│       │           │   │   └── InfoServiceImpl.java
│       │           │   └── InfoService.java
│       │           └── web
│       │               └── InfoController.java
│       ├── resources
│       │   ├── create.sql
│       │   ├── jdbc.properties
│       │   ├── mybatis
│       │   │   └── mybatis-conf.xml
│       │   └── spring
│       │       ├── spring-dao.xml
│       │       ├── spring-mvc.xml
│       │       └── spring-service.xml
│       ├── test
│       │   └── io
│       │       └── ride
│       │           └── dao
│       │               └── InfoDaoTest.java
│       └── webapp
│           ├── index.jsp
│           └── WEB-INF
│               ├── resource
│               │   ├── css
│               │   │   ├── .....
│               │   ├── fonts
│               │   │   ├── .....
│               │   └── js
│               │       ├── .....
│               ├── view
│               │   └── info.jsp
│               └── web.xml

```

### 配置项目
0. pom配置
    
    ...
1. 配置spring-dao
mybatis-config.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <settings>
        <!-- 开启驼峰命名转换 -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
        <!-- 使用jdbc的generatedKeys获取数据库自增主键 -->
        <setting name="useGeneratedKeys" value="true"/>
        <!-- 使用列别名替换列别名默认true -->
        <setting name="useColumnLabel" value="true"/>
    </settings>
    <typeAliases>
        <package name="io.ride.model"/>
    </typeAliases>
    <!-- 配置分页插件拦截器 -->
    <plugins>
        <plugin interceptor="com.github.pagehelper.PageInterceptor">
            <!-- 分页参数合理化 -->
            <property name="reasonable" value="true"/>
        </plugin>
    </plugins>
</configuration>
```
spring-dao配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 加载配置文件 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>

    <!-- 配置druid数据源 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
        <!-- 基本配置 -->
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- 配置监控统计拦截的filters，去掉后监控界面sql无法统计 -->
        <property name="filters" value="stat"/>
    </bean>

    <!-- sqlSessionFactory工厂 -->
    <bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactory">
        <property name="dataSource" ref="dataSource"/>
        <!-- 指定mybatis配置文件 -->
        <property name="configLocation" value="classpath:mybatis/mybatis-conf.xml"/>
        <!-- 指定mapper文件位置 -->
        <property name="mapperLocations" value="classpath:io/ride/mapper/*.xml"/>
    </bean>

    <!-- 配置扫描的dao层 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="io.ride.dao"/>
    </bean>
</beans>
```
2. 配置spring-mvc.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <context:component-scan base-package="io.ride.web"/>

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="resolver">
        <property name="prefix" value="/WEB-INF/view/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!-- 开启mvc注解 -->
    <mvc:annotation-driven/>
    <mvc:resources mapping="/resource/**" location="/WEB-INF/resource/"/>
</beans>
```

3. spring-service配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="io.ride.service"/>


</beans>
```

4. web.xml
```xml
<!DOCTYPE web-app PUBLIC
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
        "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
    <display-name>Archetype Created Web Application</display-name>

    <!-- 加载spring配置文件 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:spring/spring-*.xml</param-value>
    </context-param>

    <!-- 编码过滤器 -->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF8</param-value>
        </init-param>
    </filter>

    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--
        自动装配ApplicationContext的配置信息, 会自动装配applicationContext.xml文件
        这里已经通过上面指定配置文件, 所以这个监听器不用加
    -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>

    <!-- 前端控制器 -->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring/spring-mvc.xml</param-value>
        </init-param>
    </servlet>

    <!-- 配置 Druid 监控信息显示页面 -->
    <servlet>
        <servlet-name>DruidStatView</servlet-name>
        <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
        <init-param>
            <!-- 允许清空统计数据 -->
            <param-name>resetEnable</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <!-- 用户名 -->
            <param-name>loginUsername</param-name>
            <param-value>ride</param-value>
        </init-param>
        <init-param>
            <!-- 密码 -->
            <param-name>loginPassword</param-name>
            <param-value>admin</param-value>
        </init-param>
    </servlet>

    <servlet-mapping>
        <servlet-name>DruidStatView</servlet-name>
        <url-pattern>/druid/*</url-pattern>
    </servlet-mapping>

    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <welcome-file-list>
        <welcome-file>/index.jsp</welcome-file>
    </welcome-file-list>
</web-app>

```


### 代码编写
#### model
```java
public class Info {
    private Integer id;
    private String username;
    private String content;
    private Date createTime;
    
    // getter and setter
}
```
#### dao层接口
```java
public interface InfoDao {
    int add(Info info);

    int del(@Param("id") Integer id);

    List<Info> list();

    Info findById(@Param("id") Integer id);
}

```
实现看mapper文件

#### service层接口
```java
public interface InfoService {
    boolean add(String username, String content);

    List<Info> list();

    boolean del(Integer id);
}

```
具体实现查看impl类

#### 控制层代码
```java
@Controller
@RequestMapping("/info")
@ResponseBody
public class InfoController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/list")
    public Result list(@RequestParam("page") Integer page) {
        PageHelper.startPage(page, 10);
        List<Info> infos = infoService.list();
        PageInfo<Info> pageInfo = new PageInfo<Info>(infos);
        return new Result(true, "成功!").addData("pageInfo", pageInfo);
    }

    @RequestMapping("/add")
    public Result add(@RequestParam("username") String username,
                      @RequestParam("content") String content) {
        Boolean flag = infoService.add(username, content);
        if (flag) {
            return new Result(true, "添加成功!");
        } else {
            return new Result(false, "添加失败!");
        }
    }

    @RequestMapping("/del")
    public Result del(@RequestParam("id") Integer id) {
        boolean flag = infoService.del(id);
        if (flag) {
            return new Result(true, "删除成功!");
        } else {
            return new Result(false, "删除失败!");
        }
    }
}
```