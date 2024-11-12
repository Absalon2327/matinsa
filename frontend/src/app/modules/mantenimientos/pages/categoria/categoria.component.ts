import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ICategoria } from '@mantenimientos/interfaces/IProducto.interface';
import { CategoriaService } from '@mantenimientos/services/categoria.service';
import { NgSelectConfig, NgSelectModule } from '@ng-select/ng-select';
import { AjustarTextoPipe } from '@shared/pipes/ajustar-texto.pipe';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-categoria',
  standalone: true,
  imports: [NgxPaginationModule,NgSelectModule,ReactiveFormsModule,FormsModule,AjustarTextoPipe],
  templateUrl: './categoria.component.html',
  styleUrl: './categoria.component.scss'
})
export class CategoriaComponent {
  p = 1;
  itemsPerPage = 5;
  formulario!:FormGroup;
  isEditar:boolean=false;
  @ViewChild('cerrar') cerrar!: ElementRef;

  constructor(private config: NgSelectConfig, private categoriaService: CategoriaService, private fb:FormBuilder) {
    this.config.notFoundText = 'No se encontraron coincidencias';
  }
  ngOnInit(): void {
    this.formulario=this.iniciarFormulario();
    this.getPages(0);
  }

  private iniciarFormulario(){
    return this.fb.group({
      idCategoria:[''],
      nombre:['',[Validators.required]],
    })
  }

  get listCategorias(){
    return this.categoriaService.listCategorias;
  }

    get isLoading(){
    return this.categoriaService.isLoading;
  }


  getPages(page: number) {
    this.categoriaService.getCategoriasByPage(page, this.itemsPerPage);
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
          this.categoriaService.editarCategorias(this.formulario.get('idCategoria')?.value,this.formulario.value).subscribe({
            next:()=>{

              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "La categoría se ha editado exitosamente",
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
          this.categoriaService.crearCategorias(this.formulario.value).subscribe({
            next:()=>{
              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "La categoría se ha registrado exitosamente",
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

  editar(categoria:ICategoria){
    this.formulario.reset();
    this.isEditar=true;


    this.formulario.setValue({
    idCategoria: categoria.idCategoria,
    nombre: categoria.nombre

    });
  }

  eliminar(id:number){
    Swal.fire({
      title: "¿Estás seguro de que deseas eliminar este registro?",
      text: "Esta acción es irreversible.",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      cancelButtonText:"Cancelar",
      confirmButtonText: "Aceptar"
    }).then((result) => {
      if (result.isConfirmed) {
        this.categoriaService.eliminarCategorias(id).subscribe({
          next:()=>{
            this.formulario.reset();
            Swal.fire({
              title: "Éxito",
              text: "La categoría se ha eliminado exitosamente",
              icon: "success"
            });
            this.cerrar.nativeElement.click();
            this.p=1;
            this.getPages(0);
          },
          error:(resp)=>{
            Swal.fire({
              title: "Error",
              text: "La categoría esta asociada a productos y no puede ser eliminada.",
              icon: "warning"
            });
          }
        });
      }
    });
  }
}
