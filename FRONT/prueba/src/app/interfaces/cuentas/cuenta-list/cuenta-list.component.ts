import { Component, inject, OnInit } from '@angular/core';
import { CuentaService } from '../../../services/cuenta.service';
import { Cuenta } from '../../../model/cuenta.interface';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-cuenta-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl:  './cuenta-list.component.html',
  styleUrl: './cuenta-list.component.css'
})
export default class CuentaListComponent implements OnInit{

  private cuentaService = inject (CuentaService)

  cuentas : Cuenta[] = [];


  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(){
    this.cuentaService.list()
    .subscribe(cuentas =>{
      this.cuentas = cuentas;
    });
  
  }

  deleteCuenta(cuenta : Cuenta){

    this.cuentaService.delete(cuenta.id)
    .subscribe(()=>{
      this.loadAll();
    })
  }

}
