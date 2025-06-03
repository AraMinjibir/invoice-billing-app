import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {ButtonModule} from 'primeng/button';
import {MenubarModule} from 'primeng/menubar';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'header',
  imports: [RouterModule, CommonModule, ButtonModule, MenubarModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  items = [
    { label: 'Items', icon: 'pi pi-box', routerLink: '/items' },
    { label: 'Invoices', icon: 'pi pi-file', routerLink: '/invoice' },
    { label: 'Customers', icon: 'pi pi-file', routerLink: '/customers' }
  ];
}
