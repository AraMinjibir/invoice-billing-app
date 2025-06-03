import {Component, Input} from '@angular/core';
import {Invoice} from '../Model/invoice';

@Component({
  selector: 'app-invoice-details',
  imports: [],
  templateUrl: './invoice-details.component.html',
  styleUrl: './invoice-details.component.scss'
})
export class InvoiceDetailsComponent {

  @Input() invoice: Invoice | null = null;

  calculateTotal(): number {
    return this.invoice?.items.reduce((sum, item) => sum + (item.quantity * item.price), 0) || 0;
  }
}
