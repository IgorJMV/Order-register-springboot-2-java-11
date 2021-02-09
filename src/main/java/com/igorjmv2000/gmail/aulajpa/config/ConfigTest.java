package com.igorjmv2000.gmail.aulajpa.config;

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
	
	private static ClientService staticClientService;
	
	@Autowired
	private ProductService productService;
	
	private static ProductService staticProductService;
	
	@Autowired
	private CategoryService categoryService;
	
	private static CategoryService staticCategoryService;
	
	@Autowired
	private OrderService orderService;
	
	private static OrderService staticOrderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	private static OrderItemService staticOrderItemService;
	
	@Override
	public void run(String... args) throws Exception {
		staticClientService = clientService;
		staticProductService = productService;
		staticCategoryService = categoryService;
		staticOrderService = orderService;
		staticOrderItemService = orderItemService;
	}

	public static ClientService getStaticClientService() {
		return staticClientService;
	}

	public static ProductService getStaticProductService() {
		return staticProductService;
	}

	public static CategoryService getStaticCategoryService() {
		return staticCategoryService;
	}

	public static OrderService getStaticOrderService() {
		return staticOrderService;
	}

	public static OrderItemService getStaticOrderItemService() {
		return staticOrderItemService;
	}

}
