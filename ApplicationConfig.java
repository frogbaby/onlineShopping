package onlineShop;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
 @Configuration用于定义配置类，可替换xml配置文件, 相当于替换了ssmbuild中的spring-dao.xml. 
 被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
 并用于构建bean定义，初始化Spring容器。
 
 
 @EnableWebMvc: 启用spring mvc的这几个核心组件提供的能力. @EnableWebMvc注解需要用在一个可以注册到spring容器的配置类上，
 然后@EnableWebMvc注解导入了DelegatingWebMvcConfiguration配置类，这个类继承了WebMvcConfigurationSupport类提供的spring mvc各个组件的能力并且这个类也被注册到了spring容器。

 * */


@Configuration
@EnableWebMvc
public class ApplicationConfig {

	// 类似于mybaties中的 sqlSessionFactory， 在spring中整合hibernate
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		// scan object-relational mapping
		sessionFactory.setPackagesToScan("onlineShop.model");
		// 将设置好的关于hibernate的properties文件设置给sessionFactory
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
                        // change to your own RDS_Endpoint
                        // change the username and password
		dataSource.setUrl("jdbc:mysql://laiproject-instance.cpie2ielrbup.us-east-2.rds.amazonaws.com:3306/ecommerce?serverTimezone=UTC");
		dataSource.setUsername("admin");
		dataSource.setPassword("Wade123!");

		return dataSource;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10240000);
		return multipartResolver;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		//自动建表
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		//设置hibernate方言
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		return hibernateProperties;
	}
}
