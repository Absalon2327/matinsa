import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ajustarTexto',
  standalone: true
})
export class AjustarTextoPipe implements PipeTransform {

  transform(value: string): string {
    const replacedText = value.replace(/_/g, ' ');
    return replacedText.charAt(0).toUpperCase() + replacedText.slice(1).toLowerCase();
  }

}
