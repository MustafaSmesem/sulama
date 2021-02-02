package com.ewisselectronic.sulama.sulamaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.ewisselectronic.sulama"})
@EnableJpaRepositories({"com.ewisselectronic.sulama.sulamacore"})
@EntityScan({"com.ewisselectronic.sulama.sulamacore.model"})
public class SulamaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SulamaServiceApplication.class, args);
    }
}
