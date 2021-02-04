package com.igorjmv2000.gmail.aulajpa.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.igorjmv2000.gmail.aulajpa.services.CategoryService;
import com.igorjmv2000.gmail.aulajpa.services.ClientService;
import com.igorjmv2000.gmail.aulajpa.services.OrderItemService;
import com.igorjmv2000.gmail.aulajpa.services.OrderService;
import com.igorjmv2000.gmail.aulajpa.services.ProductService;

@Configuration
public class ConfigTest implements CommandLineRunner{
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		
	}

}
