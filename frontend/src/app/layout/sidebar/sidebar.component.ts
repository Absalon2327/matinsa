import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TokenService } from '@security/services/token.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  @Input() status: boolean = false;
  @Output() buttonClicked = new EventEmitter<void>();

  constructor(
    private tokenService: TokenService,
  ) { }

  addToggle() {
    if (window.innerWidth <= 850) {
      this.buttonClicked.emit();
    }
  }

  verificarRol(role: string): boolean {
    return this.tokenService.getRoles().includes(role);
  }

  cerrarSesion(){
  this.tokenService.logOut();
  }
}
