import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Cuenta } from '../model/cuenta.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CuentaService {

  private http = inject(HttpClient)

  getCuenta():Observable<Cuenta[]>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Cuenta[]>('http://localhost:8085/api/producto')
    }


    list(){
      return this.http.get<Cuenta[]>('http://localhost:8085/api/producto');
    }

    get(id:number){
      return this.http.get<Cuenta[]>(`http://localhost:8085/api/producto/${id}`);
    }

    create(cuenta: Cuenta){
      return this.http.post<Cuenta>('http://localhost:8085/api/producto',cuenta);
    }

    update(id: number, cuenta: Cuenta){
      return this.http.put<Cuenta>(`http://localhost:8085/api/producto/${id}`,cuenta);
    }

    delete(id: number){
      return this.http.delete<void>(`http://localhost:8085/api/producto/${id}`);   
    }

    activate(cuenta: Cuenta) {
      return this.http.put<void>(`http://localhost:8085/api/producto/activar/${cuenta.id}`, null);
    }

     desactivate(cuenta: Cuenta) {
      return this.http.put<String>(`http://localhost:8085/api/producto/desactivar/${cuenta.id}`, null);
    }

    cancelate(cuenta: Cuenta) {
      return this.http.put<String>(`http://localhost:8085/api/producto/cancelar/${cuenta.id}`, null);
    }
        
}
