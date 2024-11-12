import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { IProductoResponse } from '@mantenimientos/interfaces/IProducto.interface';
import { ProductoService } from '@mantenimientos/services/producto.service';
import { NgSelectConfig, NgSelectModule } from '@ng-select/ng-select';
import { AjustarTextoPipe } from '@shared/pipes/ajustar-texto.pipe';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [NgxPaginationModule,NgSelectModule,ReactiveFormsModule,FormsModule,AjustarTextoPipe],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.scss'
})
export class ProductoComponent {
  p = 1;
  itemsPerPage = 5;
  formulario!:FormGroup;
  isEditar:boolean=false;
  @ViewChild('cerrar') cerrar!: ElementRef;
  tipo:string='1';

  constructor(private config: NgSelectConfig, private productoService: ProductoService, private fb:FormBuilder) {
    this.config.notFoundText = 'No se encontraron coincidencias';
  }

  ngOnInit(): void {
    this.formulario=this.iniciarFormulario();
    this.getPages(0);
    this.productoService.getAllCategorias();
    this.productoService.getAllUnidades();
  }

  private iniciarFormulario(){
    return this.fb.group({
      idProducto:[''],
      estado:[''],
      codigo:[''],
      nombreProducto:['',[Validators.required]],
      descripcion:['',[Validators.required]],
      tipoProducto:['',[Validators.required]],
      unidad:[null,[Validators.required]],
      categoriaProducto:[null],
      cantidad:['',[Validators.required]]
    })
  }

  get listProductos(){
    return this.productoService.listProductos;
  }
  get isLoading(){
    return this.productoService.isLoading;
  }

  get listCategorias(){
    return this.productoService.listCategorias;
  }

  get listUnidades(){
    return this.productoService.listUnidades;
  }


  getPages(page: number) {
    this.productoService.getProductosByPage(page, this.itemsPerPage,this.tipo);
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
          this.productoService.editarProducto(this.formulario.get('idProducto')?.value,this.formulario.value).subscribe({
            next:()=>{

              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "El producto se ha editado exitosamente",
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
          this.productoService.crearProducto(this.formulario.value).subscribe({
            next:()=>{
              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "El producto se ha registrado exitosamente",
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

  editar(producto:IProductoResponse){
    this.formulario.reset();
    this.isEditar=true;


    this.formulario.setValue({
    idProducto: producto.idProducto,
    estado: producto.estado === 'Activo' ? true: false,
    codigo: producto.codigo,
    nombreProducto: producto.nombreProducto,
    descripcion: producto.descripcion,
    tipoProducto: producto.idTipoProducto.toString(),
    unidad: producto.idUnidad.toString(),
    categoriaProducto:  producto.idProducto ===1 ? producto.categoriaProducto.toString() : '',
    cantidad: producto.cantidad,

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
        this.productoService.eliminarProducto(id).subscribe({
          next:()=>{
            this.formulario.reset();
            Swal.fire({
              title: "Éxito",
              text: "El producto se ha eliminado exitosamente",
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
              text: "Este producto tiene órdenes de producción asociadas y no puede ser eliminado del inventario.",
              icon: "warning",
              buttonsStyling: true,
              confirmButtonColor: '#1d7373',
            });
          }
        });
      }
    });
  }

  generarReporte(){
    this.productoService.generarPdf(this.tipo).subscribe((blob: Blob) => {
      const blobUrl = URL.createObjectURL(blob);
      window.open(blobUrl, '_blank'); // Abre el PDF en una nueva pestaña
    });
  }

  filtrar(){
    this.p=1;
    this.productoService.getProductosByPage(0, this.itemsPerPage,this.tipo);
  }

}
