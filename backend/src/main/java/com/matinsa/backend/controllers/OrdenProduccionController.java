package com.matinsa.backend.controllers;

import com.matinsa.backend.dto.CantidadDto;
import com.matinsa.backend.dto.OrdenProduccionDto;
import com.matinsa.backend.entities.OrdenProduccion;
import com.matinsa.backend.interfaces.IOrden;
import com.matinsa.backend.security.dto.Mensaje;
import com.matinsa.backend.security.exceptions.CustomException;
import com.matinsa.backend.services.OrdenProducionService;
import com.matinsa.backend.utils.ReportGenerator;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("orden-produccion")
public class OrdenProduccionController {

    public final OrdenProducionService ordenProducionService;
    private final ReportGenerator reportGenerator;

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @GetMapping
    public ResponseEntity<Page<OrdenProduccion>> listarOrdenes(Pageable pageable){
        return ResponseEntity.ok(ordenProducionService.listar(pageable));
    }

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @GetMapping("/filtro")
    public ResponseEntity<Page<IOrden>> listar(@RequestParam(value = "estado", required = false) String estado,
                                               @RequestParam(value = "fecha", required = false)  String fecha, Pageable pageable){
        return ResponseEntity.ok(ordenProducionService.listarFiltro(estado,fecha,pageable));
    }

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @GetMapping("combo")
    public ResponseEntity<List<OrdenProduccion>> combo(){
        return ResponseEntity.ok(ordenProducionService.leer());
    }

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @PostMapping("verificar-existencias")
    public ResponseEntity<Mensaje> verificarExistencias(@RequestBody CantidadDto dto){
        return ResponseEntity.ok(ordenProducionService.verificarExistencias(dto));
    }

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Mensaje> crear(@RequestBody OrdenProduccionDto dto){
        return ResponseEntity.ok(ordenProducionService.crear(dto));
    }

    @PreAuthorize("hasRole('PRODUCCION') or hasRole('ADMINISTRADOR')")
    @GetMapping("/generar-pdf")
    public ResponseEntity<byte[]> generarPDF(@RequestParam(value = "estado", required = false) String estado, @RequestParam(value = "fecha", required = false)  String fecha) throws Exception {
        List<?> datos = ordenProducionService.reporte(estado,fecha);
        if(datos.isEmpty()){
            throw new CustomException(HttpStatus.BAD_REQUEST, "No se encontraron datos");
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos);
        Map<String,Object> parameters = new HashMap<>();
        String rutaJRXML = "/reports/ordenes.jrxml";
        String nombreArchivo = "reporte.pdf";
        return reportGenerator.generarPDF(nombreArchivo,rutaJRXML, parameters, dataSource);
    }

}
