package com.matinsa.backend.services.servicesImp;

import com.matinsa.backend.dto.ClienteDto;
import com.matinsa.backend.entities.Cliente;
import com.matinsa.backend.repositories.ClienteRepository;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImp implements ClienteService {

    private final ClienteRepository clienteRepository;

    private Cliente findClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.CONFLICT, "El cliente con ID " + id + " no existe"));
    }

    @Override
    public Page<Cliente> listar(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Mensaje crear(ClienteDto dto) {
        Cliente cliente = new Cliente(dto.nombre(), dto.direccion());
        clienteRepository.save(cliente);
        return new Mensaje("El Cliente ha sido creado exitosamente");
    }

    @Override
    @Transactional
    public Mensaje editar(Long id, ClienteDto dto) {
        Cliente cliente = findClienteById(id);
        cliente.setNombre(dto.nombre());
        cliente.setDireccion(dto.direccion());
        clienteRepository.save(cliente);
        return new Mensaje("El Cliente ha sido editado exitosamente");
    }

    @Override
    @Transactional
    public Mensaje eliminar(Long id) {
        Cliente cliente = findClienteById(id);
        clienteRepository.delete(cliente);
        return new Mensaje("El Cliente ha sido eliminado exitosamente");
    }

    @Override
    public List<Cliente> leer() {
        return clienteRepository.findAll();
    }
}
