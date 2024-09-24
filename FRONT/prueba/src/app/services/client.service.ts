import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Client } from '../model/client.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private http = inject(HttpClient);

  getCLient():Observable<Client[]>{
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.get<Client[]>('http://localhost:8085/api/cliente')
    }


  list(){
    return this.http.get<Client[]>('http://localhost:8085/api/cliente');
  }

  get(id: number){
    return this.http.get<Client>(`http://localhost:8085/api/cliente/${id}`);
  }

  create(client: Client){
    return this.http.post<Client>('http://localhost:8085/api/cliente',client);
  }

  update(id: number, client: Client){
    return this.http.put<Client>(`http://localhost:8085/api/cliente/${id}`,client);
  }

  delete(id: number){
    return this.http.delete<void>(`http://localhost:8085/api/cliente/${id}`);
  }

}
