package io.github.yusukehasegawa.openapi;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@SpringBootApplication
public class OpenApiApplication {

	// @Value("${spring.datasource.hikari.maximum-pool-size}")
	@Value("#{dataSource.maximumPoolSize}")
	private int connectionPoolSize;

	@Bean
	public Scheduler jdbcScheduler() {
		return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
	}

	public static void main(String[] args) {
		SpringApplication.run(OpenApiApplication.class, args);
	}

}
