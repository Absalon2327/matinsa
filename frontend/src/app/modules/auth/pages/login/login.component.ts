import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginUsuario } from '@security/models/login-usuario';
import { AuthService } from '@security/services/auth.service';
import { TokenService } from '@security/services/token.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginUsuario!: LoginUsuario;
  toggleBtn: boolean = false;

  formularioLogin!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router
  ) { }


  imagenUrl: string = '';
  ngOnInit(): void {
    this.formularioLogin = this.iniciarFormulario();
  }

  seePassword() {
    this.toggleBtn = !this.toggleBtn;
  }

  private iniciarFormulario(): FormGroup {
    return this.fb.group({
      usuario: ['', [Validators.required]],
      contrasenia: ['', [Validators.required]],
    });
  }

  onLogin(): void {
    // this.tokenService.cleanLocalStorage();
console.log('a');

    this.loginUsuario = new LoginUsuario(
      this.formularioLogin.get('usuario')?.value,
      this.formularioLogin.get('contrasenia')?.value,
    );

    this.authService.login(this.loginUsuario).subscribe({
      next: (data) => {
        this.tokenService.setToken(data.token);

        this.router.navigate(['/inicio']);
      },
      error: () => {
        // Swal.fire({
        //   title: 'Datos Incorrectos',
        //   text: 'Vuelve a intentarlo',
        //   confirmButtonColor: primary,
        // });
      },
    });
  }
  hola(){
    console.log('JHGFD');

  }
}
