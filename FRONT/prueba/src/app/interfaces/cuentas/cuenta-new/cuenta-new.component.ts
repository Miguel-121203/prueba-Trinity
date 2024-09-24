import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CuentaService } from '../../../services/cuenta.service';
import { Cuenta } from '../../../model/cuenta.interface';

@Component({
  selector: 'app-cuenta-new',
  standalone: true,
  imports: [RouterModule,ReactiveFormsModule],
  templateUrl: './cuenta-new.component.html',
  styleUrl: './cuenta-new.component.css'
})
export default class CuentaNewComponent implements OnInit{

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cuentaService = inject(CuentaService);

  form? : FormGroup;
  cuenta? : Cuenta;


  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    
    if(id){
      this.cuentaService.get(parseInt(id))
      .subscribe(cuenta  => {
        this.cuenta;
        this.form = this.fb.group({
          tipoCuenta: [cuenta,  [Validators.required]],
        })
      })
    }else{
      this.form = this.fb.group({
        tipoCuenta: ['', [Validators.required]],
        saldo: ['',[Validators.required]],
        exentaGmf: ['',[Validators.required]],
      })
    }
}

save(){
  const cuentaForm = this.form!.value;

  if(this.cuenta){
    this.cuentaService.update(this.cuenta.id, cuentaForm)
    .subscribe(()=>{
      this.router.navigate(['/cuenta-list']);
    });

  }else{
      this.cuentaService.create(cuentaForm)
      .subscribe(()=>{
        this.router.navigate(['/cuenta-list']);
      });

  }

  
}

}
