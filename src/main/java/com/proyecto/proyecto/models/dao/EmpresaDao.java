package com.proyecto.proyecto.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.models.entities.Empresa;

@Service
public class EmpresaDao {
    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    public List<Empresa> listarEmpresa (){
        return repositorioEmpresa.findAll();
    }
    
    public void eliminarEmpresa(int id) {
        repositorioEmpresa.deleteById(id);
    }
    
    public Boolean encontrarEmpresa(int id) {
        return repositorioEmpresa.existsById(id);}
    

    public void crearEmpresa (Empresa empresa){
        repositorioEmpresa.save(empresa);
    }
     public Optional<Empresa> buscarEmpresa(int id){
        return repositorioEmpresa.findById(id);    
    }
       public Empresa obtenerEmpresa(int id){
        return buscarEmpresa(id).get();
    }
    


}
