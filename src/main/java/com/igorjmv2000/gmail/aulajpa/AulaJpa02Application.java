package com.igorjmv2000.gmail.aulajpa;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.igorjmv2000.gmail.aulajpa.gui.start.StartGui;

@SpringBootApplication
public class AulaJpa02Application {

	public static void main(String[] args) {
		try {
			SpringApplication.run(AulaJpa02Application.class, args);
			StartGui.initialize(args, false);
		}catch(HibernateException | BeanCreationException e) {
			StartGui.initialize(args, true);
		}
	}

}
