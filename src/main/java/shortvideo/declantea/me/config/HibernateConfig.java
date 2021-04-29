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


    private static final Logger logger= LoggerFactory.getLogger(HibernateConfig.class);

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }


    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setConfigLocation(context.getResource("classpath:hibernate-cfg/hibernate.cfg.xml"));
//        sessionFactory.setHibernateProperties(getHibernateProperties());
//        sessionFactory.setDataSource(dataSource());
//        logger.debug(sessionFactory.getHibernateProperties().toString());
        sessionFactory.setPackagesToScan("shortvideo.declantea.me.entity");
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }


}
