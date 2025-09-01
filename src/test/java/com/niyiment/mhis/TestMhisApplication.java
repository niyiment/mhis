package com.niyiment.mhis;

import org.springframework.boot.SpringApplication;

public class TestMhisApplication {

	public static void main(String[] args) {
		SpringApplication.from(MhisApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
