import { Component, inject, OnInit } from '@angular/core';
import { ClientService } from '../../../services/client.service';
import { RouterModule } from '@angular/router';
import { Client } from '../../../model/client.interface';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.css'
})
export default class ClientListComponent implements OnInit{
  private clientService = inject(ClientService);

  clients: Client[] = [];

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(){
    this.clientService.list()
    .subscribe(clients =>{
      this.clients = clients;
    });
  
  }

  deleteClient(client : Client){

    this.clientService.delete(client.id)
    .subscribe(()=>{
      this.loadAll();
    })
  }

}
