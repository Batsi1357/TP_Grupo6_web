package com.example.tp_grupo6.serviceImpls;

import com.example.tp_grupo6.entities.Cliente;
import com.example.tp_grupo6.repositories.ClienteRepository;
import com.example.tp_grupo6.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClienteServiceImpl implements ClienteService
{
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> list() {
        return clienteRepository.findAll();
    }

    @Override
    public void insert(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente listId(int id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(int id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public void update(Cliente cliente) {
        clienteRepository.save(cliente);
    }
    @Override
    public List<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public List<Cliente> findByDominioEmailSQL(String dominio) {
        return clienteRepository.buscarPorDominioEmailSQL(dominio);
    }

    @Override
    public List<Cliente> findByEdadMinimaJPQL(int edadMin) {
        return clienteRepository.buscarPorEdadJPQL(edadMin);
    }

    @Override
    public List<Cliente> findByNombreOApellidoContiene(String texto) {
        return clienteRepository.buscarPorNombreOApellidoJPQL(texto);
    }

}
