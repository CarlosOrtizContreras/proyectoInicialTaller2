package com.proyecto.proyecto.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.models.entities.Empresa;

@Repository
public interface RepositorioEmpresa extends JpaRepository<Empresa,Integer>{

    
} 
