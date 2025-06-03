import { Component } from '@angular/core';
import {Customer} from '../Model/customer';
import {CustomerService} from '../Service/customer.service';
import {FormsModule} from '@angular/forms';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {InputTextModule} from 'primeng/inputtext';

@Component({
  selector: 'customers',
  imports: [FormsModule,
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.scss'
})
export class CustomersComponent {

  customers: Customer[] = [];
  selectedCustomer: Customer = this.getEmptyCustomer();
  displayDialog = false;
  isEdit = false;

  constructor(private customerService: CustomerService) {}

  ngOnInit() {
    this.loadCustomers();
  }

  loadCustomers() {
    this.customerService.getCustomers().subscribe(data => this.customers = data);
  }

  openNew() {
    this.selectedCustomer = this.getEmptyCustomer();
    this.isEdit = false;
    this.displayDialog = true;
  }

  editCustomer(customer: Customer) {
    this.selectedCustomer = { ...customer };
    this.isEdit = true;
    this.displayDialog = true;
  }

  saveCustomer() {
    if (this.isEdit) {
      this.customerService.updateCustomer(this.selectedCustomer.id!, this.selectedCustomer)
        .subscribe(() => this.loadCustomers());
    } else {
      this.customerService.createCustomer(this.selectedCustomer)
        .subscribe(() => this.loadCustomers());
    }
    this.displayDialog = false;
  }

  deleteCustomer(id: number) {
    this.customerService.deleteCustomer(id).subscribe(() => this.loadCustomers());
  }

  private getEmptyCustomer(): Customer {
    return { name: '', email: '', phone: 0, address: '' };
  }

}
