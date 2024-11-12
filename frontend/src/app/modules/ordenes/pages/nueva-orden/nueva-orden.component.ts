import { Component, ElementRef, TemplateRef, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { NgSelectConfig, NgSelectModule } from '@ng-select/ng-select';
import { IDetalleOrden } from '@ordenes/interfaces/IDetalleOrden.interface';
import { OrdenesService } from '@ordenes/services/ordenes.service';
import Swal from 'sweetalert2';
import { ICantidad } from '../../interfaces/IProducto.interface';
import { IOrden } from '@ordenes/interfaces/IOrden.interface';

@Component({
  selector: 'app-nueva-orden',
  standalone: true,
  imports: [NgSelectModule, ReactiveFormsModule],
  templateUrl: './nueva-orden.component.html',
  styleUrl: './nueva-orden.component.scss',
})
export class NuevaOrdenComponent {
  formulario!: FormGroup;
  formularioDetalle!: FormGroup;
  detallesOrden = new Set<IDetalleOrden>();
  detallesOrdenArray: IDetalleOrden[] = [];
  detalleEditar!: IDetalleOrden | null;
  cantidadDto!: ICantidad;
  ordenProduccion!: IOrden;
  @ViewChild('cerrar') cerrar!: ElementRef;

  constructor(
    private config: NgSelectConfig,
    private fb: FormBuilder,
    private ordenService: OrdenesService
  ) {
    this.config.notFoundText = 'No se encontraron coincidencias';
  }

  ngOnInit(): void {
    this.formulario = this.iniciarFormulario();
    this.formularioDetalle = this.iniciarFormularioDetalle();
    this.ordenService.getAllClientes();
    this.ordenService.getAllCategorias();
    this.ordenService.getAllMateriaPrima();
  }

  private iniciarFormulario() {
    return this.fb.group({
      idOrden: [''],
      cliente: [null, [Validators.required]],
      tipoProducto: [null, []],
      producto: [null, [Validators.required]],
      fechaEntrega: ['', [Validators.required]],
      cantidad: ['', [Validators.required]],
    });
  }

  private iniciarFormularioDetalle() {
    return this.fb.group({
      idDetalle: [''],
      producto: [null, [Validators.required]],
      productoNombre: [null, [Validators.required]],
      cantidad: ['', [Validators.required]],
    });
  }

  get listClientes() {
    return this.ordenService.listClientes;
  }

  get listCategorias() {
    return this.ordenService.listCategorias;
  }

  get listProductos() {
    return this.ordenService.listProductos;
  }

  get listMateriaPrima() {
    return this.ordenService.listMateriaPrima;
  }

  filtrarProductos() {
    const tipo = this.formulario.get('tipoProducto')?.value;
    this.formulario.get('producto')?.setValue(null);

    if (tipo) {
      this.ordenService.listProductos = [];
      this.ordenService.getProductosByTipo(tipo);
      return;
    }
    this.ordenService.listProductos = [];
  }

  abrirModalDetalle() {
    this.formularioDetalle.reset();
  }

  obtenerNombreMateriaPrima() {
    const materiaPrima = this.formularioDetalle.get('producto')?.value;
    const objetoEncontrado = this.listMateriaPrima.find(
      (objeto) => objeto.idProducto == materiaPrima
    );
    this.formularioDetalle
      .get('productoNombre')
      ?.setValue(objetoEncontrado?.nombreProducto);
  }

  guardarDetalle() {
    if (this.formularioDetalle.valid) {
      // SI EL REGISTRO SE ESTA EDITANDO
      if (this.detalleEditar) {
        console.log(this.detalleEditar, this.formularioDetalle.value);

        if (this.detalleEditar !== this.formularioDetalle.value) {
          this.detallesOrden.delete(this.detalleEditar);
          this.detallesOrdenArray = Array.from(this.detallesOrden);
        }
        this.detalleEditar = null;
      }

      //VERIFICAR EXISTENCIAS
      if (
        this.detallesOrdenArray.some(
          (detalle) =>
            detalle.producto === this.formularioDetalle.get('producto')?.value
        )
      ) {
        Swal.fire({
          title: 'Atención',
          text: 'La materia prima ya ha sido agregada al detalle de la orden.',
          icon: 'warning',
        });
        return;
      }
      this.cantidadDto = {
        id: this.formularioDetalle.get('producto')?.value,
        cantidad: this.formularioDetalle.get('cantidad')?.value,
      };
      this.ordenService.verificarExistencias(this.cantidadDto).subscribe({
        next: () => {
          this.detallesOrden.add(this.formularioDetalle.value);
          this.detallesOrdenArray = Array.from(this.detallesOrden);
          this.cerrar.nativeElement.click();
        },
        error: (resp) => {
          Swal.fire({
            title: 'Atención',
            text: resp.error.mensaje,
            icon: 'warning',
            buttonsStyling: true,
            confirmButtonColor: '#1d7373',
          });
          return;
        },
      });
    }
    return Object.values(this.formularioDetalle.controls).forEach((control) =>
      control.markAsTouched()
    );
  }

  eliminarDetalle(detalle: IDetalleOrden) {
    this.detallesOrden.delete(detalle);
    this.detallesOrdenArray = Array.from(this.detallesOrden);
  }

  editarDetalle(detalle: IDetalleOrden) {
    this.detalleEditar = detalle;
    this.formulario.reset();
    this.formularioDetalle.get('producto')?.setValue(detalle.producto);
    this.formularioDetalle.get('cantidad')?.setValue(detalle.cantidad);
  }

  guardarOrden() {
    if (this.formulario.valid) {
      this.ordenProduccion = {
        cliente: this.formulario.get('cliente')?.value,
        producto: this.formulario.get('producto')?.value,
        cantidad: this.formulario.get('cantidad')?.value,
        fechaEntrega: this.formulario.get('fechaEntrega')?.value,
        detallesOrden: this.detallesOrdenArray,
      };

      this.ordenService.crearOrdenProduccion(this.ordenProduccion).subscribe({
        next: () => {
          this.formulario.reset();
          this.detallesOrden.clear();
          this.detallesOrdenArray = Array.from(this.detallesOrden);

          Swal.fire({
            title: 'Éxito',
            text: 'La Orden de producción se ha creado exitosamente',
            icon: 'success',
            buttonsStyling: true,
            confirmButtonColor: '#1d7373',
          });
        },
        error: (resp) => {
          Swal.fire({
            title: 'Atención',
            text: resp.error.mensaje,
            icon: 'warning',
            buttonsStyling: true,
            confirmButtonColor: '#1d7373',
          });
        },
      });
    }
    return Object.values(this.formulario.controls).forEach((control) =>
      control.markAsTouched()
    );
  }
}
