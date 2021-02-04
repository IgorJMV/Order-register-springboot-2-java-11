package com.igorjmv2000.gmail.aulajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.igorjmv2000.gmail.aulajpa.gui.start.StartGui;

@SpringBootApplication
public class AulaJpa02Application {

	public static void main(String[] args) {
		SpringApplication.run(AulaJpa02Application.class, args);
		StartGui.initialize(args);
	}

}
