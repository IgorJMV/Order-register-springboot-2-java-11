package com.igorjmv2000.gmail.aulajpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igorjmv2000.gmail.aulajpa.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

}
