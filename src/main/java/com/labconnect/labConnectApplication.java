package com.labconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//@SpringBootApplication
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com.labconnect.config")
//@EntityScan(basePackages ={"com.labconnect.models.Identity","com.labconnect.models.notification","com.labconnect.models.orderSpecimen",
//        "com.labconnect.models.testResult","com.labconnect.models.testCatalog","com.labconnect.models.testResult",
//        "com.labconnect.models.workFlow","com.labconnect.security","com.labconnect.config","com.labconnect"})
//@EnableJpaRepositories(basePackages = "com.labconnect.repository.Identity")



@SpringBootApplication//(scanBasePackages = "com.labconnect")
// scan entities
//@EntityScan(basePackages = "com.labconnect.models")
// scan all repositories
//@EnableJpaRepositories(basePackages = "com.labconnect.repository")

public class labConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(labConnectApplication.class,args);
    }
}
