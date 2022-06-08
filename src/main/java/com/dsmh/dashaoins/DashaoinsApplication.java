package com.dsmh.dashaoins;

import com.dsmh.dashaoins.acl.DashaoinsClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAsync
@EnableTransactionManagement
@EnableConfigurationProperties({DashaoinsClientProperties.class})
public class DashaoinsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashaoinsApplication.class, args);
	}

}
