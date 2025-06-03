import { Routes } from '@angular/router';
import {HeaderComponent} from './layout/header/header.component';
import {CustomersComponent} from './customers/customers.component';
import {ItemsComponent} from './Items/items/items.component';
import {ItemFormComponent} from './Items/item-form/item-form.component';
import {InvoiceComponent} from './invoice/invoice.component';

export const routes: Routes = [
  {
    path: 'header',
    component: HeaderComponent,
  },
  {
    path:'customers',
    component: CustomersComponent,
  },
  {
    path:'items',
    component: ItemsComponent,
  },
  {
    path:'item-form',
    component: ItemFormComponent,
  },
  {
    path:'invoice',
    component: InvoiceComponent,
  },
  {
    path: '', redirectTo: 'customers', pathMatch: 'full'
  }
];
