package com.igorjmv2000.gmail.aulajpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igorjmv2000.gmail.aulajpa.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
