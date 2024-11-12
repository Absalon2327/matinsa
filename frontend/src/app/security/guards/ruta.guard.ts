import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { TokenService } from '@security/services/token.service';

export const rutaGuard: CanActivateFn = async (route, state) => {
  const tokenService = inject(TokenService);
  const router = inject(Router);

  if (tokenService.getToken() !== '') {
    try {
      const resp = await tokenService.validarToken().toPromise();

      if (resp) {
        // eslint-disable-next-line dot-notation
        const expectedAuthorities = route.data['expectedRol'];
        const roles = tokenService.getRoles();

        if (roles[0] === 'ADMINISTRADOR') {
          return true;
        }

        // ? Si NO especifica los permisos para la ruta y no es Admin
        if (!expectedAuthorities) {
          router.navigate(['/inicio']);
          return false;
        }

        // ? Si especifica los permisos para la ruta y no es Admin
        const hasExpectedAuthorities = expectedAuthorities.some((authority: string) =>
          roles.includes(authority),
        );

        // ! Si NO tiene permisos de los que se esperan
        if (!hasExpectedAuthorities) {
          router.navigate(['/inicio']);
          // this.tokenService.logOut();
          return false;
        }

        return true;
      }

      return false;
    } catch (error) {
      return false;
    }
  } else {
    tokenService.logOut();
    return false;
  }

};
