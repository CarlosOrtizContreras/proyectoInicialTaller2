package com.proyecto.proyecto.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.models.entities.Cliente;
@Service
public class ClienteDao  {

    @Autowired
    private IDaoCliente daoCliente;

    public List<Cliente> listarClientes (){
        return daoCliente.findAll();
    }

    public Cliente obtenerCliente(int id){
        return buscarCliente(id).get();
    }

    public void crear (Cliente cliente){
        daoCliente.save(cliente);
    }

    public Optional<Cliente> buscarCliente(int id){
        return daoCliente.findById(id);    
    }

    public void eliminarCliente(int id){
        daoCliente.deleteById(id);
    }

    public Boolean encontrarCliente(int id){
        return daoCliente.existsById(id);
    }
    
}
