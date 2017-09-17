package com.company.communicate;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan ("com.company.communicate")
//@MapperScan ("com.company.communicate.mapper")
//@ImportResource ({ "classpath:hessian/hessian-client.xml", "classpath:hessian/hessian-server.xml" })
public class CommunicateApplication {

	public static void main(String[] args) {
		try {
		SpringApplication.run(CommunicateApplication.class, args);
		}
		catch(Throwable e)
		{
		   e.printStackTrace();	
		}
	}
}
