package com.matinsa.backend.controllers;

import com.matinsa.backend.dto.ProductoDto;
import com.matinsa.backend.entities.Producto;
import com.matinsa.backend.interfaces.IProducto;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.services.ProductoService;
import com.matinsa.backend.utils.ReportGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("producto")
public class ProductoController {

    private final ProductoService productoService;
    private final ReportGenerator reportGenerator;

    @GetMapping("/combo/{id}")
    public ResponseEntity<List<Producto>> combo(@PathVariable Long id){
        return ResponseEntity.ok(productoService.findAllProductsByCategoria(id));
    }

    @GetMapping("/combo-materia-prima")
    public ResponseEntity<List<Producto>> materiaPrima(){
        return ResponseEntity.ok(productoService.findAllMateriaPrima());
    }

    @PreAuthorize("hasRole('BODEGA') or hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Page<IProducto>> listar(Pageable pageable, @PathVariable int id){
        return ResponseEntity.ok(productoService.findAllProducts(pageable, id));
    }

    @PreAuthorize("hasRole('BODEGA') or hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Mensaje> crear(@RequestBody @Valid ProductoDto productoDto){
        return ResponseEntity.ok(productoService.crear(productoDto));
    }

    @PreAuthorize("hasRole('BODEGA') or hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editar(@PathVariable Long id,@RequestBody @Valid ProductoDto productoDto){
        return ResponseEntity.ok(productoService.editar(id,productoDto));
    }

    @PreAuthorize("hasRole('BODEGA') or hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminar(@PathVariable Long id){
        return ResponseEntity.ok(productoService.eliminar(id));
    }

    @PreAuthorize("hasRole('BODEGA') or hasRole('ADMINISTRADOR')")
    @GetMapping("/generar-pdf/{id}")
    public ResponseEntity<byte[]> generarPDF(@PathVariable Integer id) throws Exception {
        List<?> datos = productoService.reporte(id);
        if(datos.isEmpty()){
            throw new CustomException(HttpStatus.BAD_REQUEST, "No se encontraron datos");
        }
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);
        Map<String,Object> parameters = new HashMap<>();
        String rutaJRXML = "/reports/inventario.jrxml";
        String nombreArchivo = "reporte.pdf";
        return reportGenerator.generarPDF(nombreArchivo,rutaJRXML, parameters, dataSource);
    }

}
