package unificado.core.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unificado.core.service.model.Cuenta;
import unificado.core.service.repository.CuentaRepository;

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
