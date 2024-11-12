import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IUnidad } from '@mantenimientos/interfaces/IProducto.interface';
import { UnidadService } from '@mantenimientos/services/unidad.service';
import { NgSelectConfig, NgSelectModule } from '@ng-select/ng-select';
import { AjustarTextoPipe } from '@shared/pipes/ajustar-texto.pipe';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-unidad',
  standalone: true,
  imports: [NgxPaginationModule,NgSelectModule,ReactiveFormsModule,FormsModule,AjustarTextoPipe],
  templateUrl: './unidad.component.html',
  styleUrl: './unidad.component.scss'
})
export class UnidadComponent {
  p = 1;
  itemsPerPage = 5;
  formulario!:FormGroup;
  isEditar:boolean=false;
  @ViewChild('cerrar') cerrar!: ElementRef;

  constructor(private config: NgSelectConfig, private unidadService: UnidadService, private fb:FormBuilder) {
    this.config.notFoundText = 'No se encontraron coincidencias';
  }
  ngOnInit(): void {
    this.formulario=this.iniciarFormulario();
    this.getPages(0);
  }

  private iniciarFormulario(){
    return this.fb.group({
      idUnidad:[''],
      nombre:['',[Validators.required]],
    })
  }

  get listUnidades(){
    return this.unidadService.listUnidades;
  }

    get isLoading(){
    return this.unidadService.isLoading;
  }


  getPages(page: number) {
    this.unidadService.getUnidadesByPage(page, this.itemsPerPage);
  }

  pageChange(newPage: number) {
    this.p = newPage;
    this.getPages(this.p - 1);
  }

  abrirModal(){
    this.isEditar=false;
    this.formulario.reset();
  }

  guardarProducto(){
    console.log(this.formulario.value);
    if(this.formulario.valid){
        if(this.isEditar){
          this.unidadService.editarUnidades(this.formulario.get('idUnidad')?.value,this.formulario.value).subscribe({
            next:()=>{

              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "La unidad se ha editado exitosamente",
                icon: "success",
                buttonsStyling: true,
                confirmButtonColor: '#1d7373',
              });
              this.cerrar.nativeElement.click();
              this.p=1;
              this.getPages(0);
          this.isEditar=false;

            },
            error:()=>{
              Swal.fire({
                title: "Error",
                text: "El registro no se ha logrado editar",
                icon: "warning",
                buttonsStyling: true,
                confirmButtonColor: '#1d7373',
              });
          this.isEditar=false;

            }
          });
        }else{
          this.unidadService.crearUnidades(this.formulario.value).subscribe({
            next:()=>{
              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "La unidad se ha registrado exitosamente",
                icon: "success",
                buttonsStyling: true,
                confirmButtonColor: '#1d7373',
              });
              this.cerrar.nativeElement.click();
              this.p=1;
              this.getPages(0);
            },
            error:(resp)=>{
              Swal.fire({
                title: "Error",
                text: "El registro no se ha registrado",
                icon: "warning",
                buttonsStyling: true,
                confirmButtonColor: '#1d7373',
              });
            }
          });
        }
    }
  }

  editar(unidad:IUnidad){
    this.formulario.reset();
    this.isEditar=true;


    this.formulario.setValue({
    idUnidad: unidad.idUnidad,
    nombre: unidad.nombre

    });
  }

  eliminar(id:number){
    Swal.fire({
      title: "¿Estás seguro de que deseas eliminar este registro?",
      text: "Esta acción es irreversible.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#1d7373",
      cancelButtonColor: "#d33",
      cancelButtonText:"Cancelar",
      confirmButtonText: "Aceptar"
    }).then((result) => {
      if (result.isConfirmed) {
        this.unidadService.eliminarUnidades(id).subscribe({
          next:()=>{
            this.formulario.reset();
            Swal.fire({
              title: "Éxito",
              text: "La unidad se ha eliminado exitosamente",
              icon: "success",
              buttonsStyling: true,
              confirmButtonColor: '#1d7373',
            });
            this.cerrar.nativeElement.click();
            this.p=1;
            this.getPages(0);
          },
          error:(resp)=>{
            Swal.fire({
              title: "Error",
              text: "La unidad esta asociada a productos y no puede ser eliminada.",
              icon: "warning",
              buttonsStyling: true,
              confirmButtonColor: '#1d7373',
            });
          }
        });
      }
    });
  }
}
