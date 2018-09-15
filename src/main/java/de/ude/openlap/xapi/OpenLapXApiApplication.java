package de.ude.openlap.xapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = { OpenLapXApiApplication.class })
public class OpenLapXApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenLapXApiApplication.class, args);
	}
}
