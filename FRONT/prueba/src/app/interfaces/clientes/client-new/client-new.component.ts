import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ClientService } from '../../../services/client.service';
import { Client } from '../../../model/client.interface';

@Component({
  selector: 'app-client-new',
  standalone: true,
  imports: [RouterModule,ReactiveFormsModule],
  templateUrl: './client-new.component.html',
  styleUrl: './client-new.component.css'
})
export default class ClientNewComponent implements OnInit{

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private clientService = inject(ClientService);

  form? : FormGroup;
  client? : Client;


  ngOnInit(): void {
      const id = this.route.snapshot.paramMap.get('id');
      
      if(id){
        this.clientService.get(parseInt(id))
        .subscribe(client => {
          this.client = client;
          this.form = this.fb.group({
            tipoId: [client.tipoId, [Validators.required]],
            numId: [client.numId,[Validators.required]],
            nombre: [client.nombre,[Validators.required]],
            apellido: [client.apellido,[Validators.required]],
            correo: [client.correo,[Validators.required]],
            fechaNacimiento: [client.fechaNacimiento, [Validators.required]]
          })
        })
      }else{
        this.form = this.fb.group({
          tipoId: ['', [Validators.required]],
          numId: ['',[Validators.required]],
          nombre: ['',[Validators.required]],
          apellido: ['',[Validators.required]],
          correo: ['',[Validators.required]],
          fechaNacimiento: ['', [Validators.required]]
        })
      }
  }


  save(){
    const clientForm = this.form!.value;

    if(this.client){
      this.clientService.update(this.client.id, clientForm)
      .subscribe(()=>{
        this.router.navigate(['/']);
      });

    }else{
        this.clientService.create(clientForm)
        .subscribe(()=>{
          this.router.navigate(['/']);
        });

    }

    
  }



}
