import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { LineaProduccionService } from '@linea-produccion/services/linea-produccion.service';
import { NgSelectConfig, NgSelectModule } from '@ng-select/ng-select';
import { IOrdenResponse } from '@ordenes/interfaces/IOrden.interface';
import { AjustarTextoPipe } from '@shared/pipes/ajustar-texto.pipe';
import { FechaPipe } from '@shared/pipes/fecha.pipe';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-linea-produccion',
  standalone: true,
  imports: [NgxPaginationModule,NgSelectModule,ReactiveFormsModule,FormsModule,AjustarTextoPipe,FechaPipe],
  templateUrl: './linea-produccion.component.html',
  styleUrl: './linea-produccion.component.scss'
})
export class LineaProduccionComponent {
  p = 1; // Página actual para ngx-pagination
  itemsPerPage = 5;
  ordenSeleccionada!:IOrdenResponse | undefined;
  formulario!:FormGroup;
  finzalizarOrden!:FormGroup;
  @ViewChild('cerrar') cerrar!: ElementRef;
  @ViewChild('segundoCerrar') segundoCerrar!: ElementRef;
  constructor(private config: NgSelectConfig, private lineaService: LineaProduccionService, private fb:FormBuilder) {
    this.config.notFoundText = 'No se encontraron coincidencias';
  }
  ngOnInit(): void {
    this.getPages(0);
    this.lineaService.getAllOrdenes();
    this.formulario = this.fb.group({
        ordenProduccion:[null,[Validators.required]],
        lineaProduccion:[null,[Validators.required]],
        fechaIngreso:['',[Validators.required]]
    });
    this.finzalizarOrden=this.fb.group({
      lineaProduccion:[null,[Validators.required]],
      fechaFinalizacion:[null,[Validators.required]],
    })
  }

  get listLineasProduccion(){
    return this.lineaService.listLineasProduccion;
  }

  get listOrdenes(){
    return this.lineaService.listOrdenes;
  }

  get isLoading(){
    return this.lineaService.isLoading;
  }

  getPages(page: number) {
    this.lineaService.getLineasProduccionByPage(page, this.itemsPerPage);
  }

  pageChange(newPage: number) {
    this.p = newPage; // Actualizar la página actual
    this.getPages(this.p - 1); // Ajuste para el índice de la página que comienza desde cero
  }

  obtenerInfoOrden(){
    const idOrden= this.formulario.get('ordenProduccion')?.value;
    this.ordenSeleccionada= this.listOrdenes.find(objeto => objeto.idOrden == idOrden);

  }

  abrirModal(){
    this.formulario.reset();
    this.ordenSeleccionada=undefined;
  }


  guardarLinea(){
    console.log(this.formulario.valid, this.formulario.value);
    if(this.formulario.valid){
      this.lineaService.crearLineaProduccion(this.formulario.value).subscribe({
        next:()=>{

          this.formulario.reset();
          Swal.fire({
            title: "Éxito",
            text: "La línea de producción se ha registrado exitosamente",
            icon: "success",
            buttonsStyling: true,
            confirmButtonColor: '#1d7373',
          });
          this.cerrar.nativeElement.click();
          this.p=1;
          this.getPages(0);
          this.lineaService.getAllOrdenes();
        },
        error:(resp)=>{
          Swal.fire({
            title: "Atención",
            text: resp.error.mensaje,
            icon: "warning",
            buttonsStyling: true,
            confirmButtonColor: '#1d7373',
          });
        }
      });
    }
  }

  abrirFinalizarModal(id:number){
  this.finzalizarOrden.reset();
  this.finzalizarOrden.get('lineaProduccion')?.setValue(id);
  }

  finalizar(){
    if(this.finzalizarOrden.valid){
    this.lineaService.finalizarOrden(this.finzalizarOrden.value).subscribe({
      next:()=>{
        this.formulario.reset();
        Swal.fire({
          title: "Éxito",
          text: "La línea de producción se ha finalizado exitosamente",
          icon: "success",
          buttonsStyling: true,
          confirmButtonColor: '#1d7373',
        });
        this.segundoCerrar.nativeElement.click();
        this.p=1;
        this.getPages(0);
        this.lineaService.getAllOrdenes();
      },
      error:(resp)=>{
        Swal.fire({
          title: "Error",
          text: "La fecha de finalización no puede ser menor a la de ingreso",
          icon: "warning",
          buttonsStyling: true,
          confirmButtonColor: '#1d7373',
        });
      }
    });
    }
  }


}
