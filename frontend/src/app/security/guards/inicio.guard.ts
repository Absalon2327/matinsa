import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

export const inicioGuard: CanActivateFn = async (route, state) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);


    if (tokenService.isLogged()) {
      try {
        const resp = await tokenService.validarToken().toPromise();
        if (resp) {
          return true;
        }
        return false;
      } catch (error) {
        router.navigate(['/']);
        return false;
      }
    }

    router.navigate(['/']);
    return false;
};
