import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => import('./interfaces/clientes/client-list/client-list.component')
    },
    {
        path: 'form-client',
        loadComponent: () => import('./interfaces/clientes/client-new/client-new.component')
    },
    {
        path: ':id/edit',
        loadComponent: () => import('./interfaces/clientes/client-new/client-new.component')
    }
    ,
    {
        path: 'cuenta-list',
        loadComponent: () => import('./interfaces/cuentas/cuenta-list/cuenta-list.component')
    },
    {
        path: 'cuenta-new',
        loadComponent: () => import('./interfaces/cuentas/cuenta-new/cuenta-new.component')
    },
    {
        path: ':id/editcuenta',
        loadComponent: () => import('./interfaces/cuentas/cuenta-new/cuenta-new.component')
    }
];
