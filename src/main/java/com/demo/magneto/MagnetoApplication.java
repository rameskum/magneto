package com.demo.magneto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class MagnetoApplication {

	public static void main(String[] args) {
		System.setProperty("input", "file:" + new File("C:/Users/rameskum/Desktop/user.csv").getAbsolutePath());
		System.setProperty("output", "file:" + new File("C:/Users/rameskum/Desktop/user_out.csv").getAbsolutePath());
		SpringApplication.run(MagnetoApplication.class, args);
	}
}
