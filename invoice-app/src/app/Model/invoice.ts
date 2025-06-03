
import {InvoiceItem} from './invoice-items';

export interface Invoice {

  id?: number;
  invoiceNumber: string;
  date: string;
  totalAmount: number;
  items: InvoiceItem[];
}
