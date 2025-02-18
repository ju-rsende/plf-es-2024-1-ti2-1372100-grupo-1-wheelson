package com.renatomatos.wheelson.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.renatomatos.wheelson.models.Locatario;
import com.renatomatos.wheelson.repositories.LocatarioRepository;

import jakarta.transaction.Transactional;

@Service
public class LocatarioService {

    @Autowired
    private LocatarioRepository locatarioRepository;

    public void save(Locatario locatario) {
        locatarioRepository.save(locatario);
    }

    public Locatario findById(Long id) {
        Optional<Locatario> locatario = locatarioRepository.findById(id);
        return locatario.orElseThrow(() -> new RuntimeException("Locatario não encontrado"));
    }

    public Locatario findByEmail(String email) {
        Optional<Locatario> locatario = locatarioRepository.findByEmail(email);
        return locatario.orElseThrow(() -> new RuntimeException("Locatario não encontrado"));
    }

    public Locatario findByEmailAndSenha(String email, String senha) {
        Optional<Locatario> locatario = locatarioRepository.findByEmail(email);
        if (locatario.isPresent()) {
            if (locatario.get().getSenha().equals(senha)) {
                return locatario.get();
            }
            throw new RuntimeException("Senha incorreta");
        }
        throw new RuntimeException("Locatario não encontrado");
    }

    @Transactional
    public Locatario create(Locatario locatario) {
        return this.locatarioRepository.save(locatario);
    }

    @Transactional
    public Locatario update(Locatario locatario) {
        Locatario newLocador = findById(locatario.getId());
        newLocador.setNome(locatario.getNome());
        newLocador.setEmail(locatario.getEmail());
        newLocador.setTelefone(locatario.getTelefone());
        newLocador.setRua(locatario.getRua());
        newLocador.setBairro(locatario.getBairro());
        newLocador.setNumeroResid(locatario.getNumeroResid());
        newLocador.setCidade(locatario.getCidade());
        newLocador.setUF(locatario.getUF());
        newLocador.setStatus(locatario.isStatus());
        
        return this.locatarioRepository.save(newLocador);
        
    }

    @Transactional
    public Locatario updateStatus(Long id) {
        Locatario locatario = findById(id);
        locatario.setStatus(true);
        locatario.setData_aprovacao(new Date());
        return locatarioRepository.save(locatario);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            locatarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível deletar o locatario pois há entidades relacionadas!");
        }
        
    }
    
    //getall
    public Iterable<Locatario> findAll() {
        return locatarioRepository.findAll();
    }
    //getall com status false
    public List<Locatario> findAllByStatusFalse() {
        Iterable<Locatario> allLocatarios = locatarioRepository.findAll();
        return StreamSupport.stream(allLocatarios.spliterator(), false)
                .filter(locatario -> !locatario.isStatus())
                .collect(Collectors.toList());
    }

    
}
