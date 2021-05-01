package shortvideo.declantea.me.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private ApplicationContext context;

    @Value("${DB_ENDPOINT}")
    private String db_endpoint;

    @Value("${DB_DATABASE}")
    private String db_database;

    @Value("${DB_USERNAME}")
    private String db_username;

    @Value("${DB_PASSWORD}")
    private String db_password;


    private static final Logger logger= LoggerFactory.getLogger(HibernateConfig.class);

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }


    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setConfigLocation(context.getResource("classpath:hibernate-cfg/hibernate.cfg.xml"));
        sessionFactory.setHibernateProperties(getHibernateProperties());
//        sessionFactory.setDataSource(dataSource());
        logger.debug("ENV:{}",System.getenv());
        sessionFactory.setPackagesToScan("shortvideo.declantea.me.entity");
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public Properties getHibernateProperties(){
        Properties properties = new Properties();
        properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL,"jdbc:mysql://"+db_endpoint+"/"+db_database+"?createDatabaseIfNotExist=true");
        properties.put(Environment.USER,db_username);
        properties.put(Environment.PASS,db_password);
        properties.put("show_sql",true);
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.connection.autocommit",true);
        properties.put("hibernate.c3p0.min_size",5);
        properties.put("hibernate.c3p0.max_size",20);
        properties.put("hibernate.c3p0.timeout",1800);
        properties.put("hibernate.c3p0.max_statements",150);
        properties.put("hibernate.hbm2ddl.auto","update");
        return properties;
    }

}
