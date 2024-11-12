import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteService } from '@mantenimientos/services/cliente.service';
import { NgSelectConfig, NgSelectModule } from '@ng-select/ng-select';
import { ICliente } from '@ordenes/interfaces/ICliente.interface';
import { AjustarTextoPipe } from '@shared/pipes/ajustar-texto.pipe';
import { NgxPaginationModule } from 'ngx-pagination';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [NgxPaginationModule,NgSelectModule,ReactiveFormsModule,FormsModule,AjustarTextoPipe],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.scss'
})
export class ClienteComponent {
  p = 1;
  itemsPerPage = 5;
  formulario!:FormGroup;
  isEditar:boolean=false;
  @ViewChild('cerrar') cerrar!: ElementRef;

  constructor(private config: NgSelectConfig, private clienteService: ClienteService, private fb:FormBuilder) {
    this.config.notFoundText = 'No se encontraron coincidencias';
  }
  ngOnInit(): void {
    this.formulario=this.iniciarFormulario();
    this.getPages(0);
  }

  private iniciarFormulario(){
    return this.fb.group({
      idCliente:[''],
      nombre:['',[Validators.required]],
      direccion:['',[Validators.required]],
    })
  }

  get listClientes(){
    return this.clienteService.listClientes;
  }

    get isLoading(){
    return this.clienteService.isLoading;
  }


  getPages(page: number) {
    this.clienteService.getClientesByPage(page, this.itemsPerPage);
  }

  pageChange(newPage: number) {
    this.p = newPage;
    this.getPages(this.p - 1);
  }

  abrirModal(){
    this.isEditar=false;
    this.formulario.reset();
  }

  guardarCliente(){
    console.log(this.formulario.value);
    if(this.formulario.valid){
        if(this.isEditar){
          this.clienteService.editarClientes(this.formulario.get('idCliente')?.value,this.formulario.value).subscribe({
            next:()=>{

              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "El cliente se ha editado exitosamente",
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
          this.clienteService.crearClientes(this.formulario.value).subscribe({
            next:()=>{
              this.formulario.reset();
              Swal.fire({
                title: "Éxito",
                text: "El cliente se ha registrado exitosamente",
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

  editar(cliente:ICliente){
    this.formulario.reset();
    this.isEditar=true;


    this.formulario.setValue({
    idCliente: cliente.idCliente,
    nombre: cliente.nombre,
    direccion: cliente.direccion

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
        this.clienteService.eliminarClientes(id).subscribe({
          next:()=>{
            this.formulario.reset();
            Swal.fire({
              title: "Éxito",
              text: "El cliente se ha eliminado exitosamente",
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
              text: "El cliente esta asociado a ordenes de producción y no puede ser eliminado.",
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
