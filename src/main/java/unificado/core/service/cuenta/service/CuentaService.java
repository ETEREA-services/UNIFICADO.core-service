package unificado.core.service.cuenta.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unificado.core.service.cuenta.model.Cuenta;
import unificado.core.service.cuenta.repository.CuentaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository repository;

    public List<Cuenta> findAll() {
        return repository.findAll();
    }
}
