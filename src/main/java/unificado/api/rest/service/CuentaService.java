package unificado.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.api.rest.kotlin.model.Cuenta;
import unificado.api.rest.kotlin.repository.CuentaRepository;

import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository repository;

    @Autowired
    public CuentaService(CuentaRepository repository) {
        this.repository = repository;
    }

    public List<Cuenta> findAll() {
        return repository.findAll();
    }
}
