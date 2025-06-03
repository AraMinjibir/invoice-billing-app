import { Component } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import {Invoice} from '../Model/invoice';
import {InvoiceService} from '../Service/invoice.service';
import { TableModule } from 'primeng/table';
import {ConfirmationService, MessageService} from 'primeng/api';
import {DialogModule} from 'primeng/dialog';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ToastModule} from 'primeng/toast';
import {InvoiceFormComponent} from '../invoice-form/invoice-form.component';


@Component({
  selector: 'invoice',
  imports: [TableModule,
    ButtonModule,
    DialogModule,
    ConfirmDialogModule,
    ToastModule,
    InvoiceFormComponent],
  providers: [ConfirmationService, MessageService],
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.scss'
})
export class InvoiceComponent {

  invoices: Invoice[] = [];
  selectedInvoice: Invoice | null = null;
  displayDialog = false;
  editMode = false;

  constructor(
    private invoiceService: InvoiceService,
    private confirm: ConfirmationService,
    private message: MessageService
  ) {}

  ngOnInit() {
    this.loadInvoices();
  }

  loadInvoices() {
    this.invoiceService.getInvoices().subscribe(invoices => (this.invoices = invoices));
  }

  openAdd() {
    this.editMode = false;
    this.selectedInvoice = null;
    this.displayDialog = true;
  }

  edit(invoice: Invoice) {
    this.editMode = true;
    this.selectedInvoice = { ...invoice };
    this.displayDialog = true;
  }

  saveInvoice(invoice: Invoice) {
    if (this.editMode && this.selectedInvoice?.id) {
      invoice.id = this.selectedInvoice.id;
      this.invoiceService.updateInvoice(this.selectedInvoice.id, invoice).subscribe(() => {
        this.loadInvoices();
        this.message.add({ severity: 'success', summary: 'Invoice updated' });
        this.displayDialog = false;
      });
    } else {
      this.invoiceService.createInvoice(invoice).subscribe(() => {
        this.loadInvoices();
        this.message.add({ severity: 'success', summary: 'Invoice added' });
        this.displayDialog = false;
      });
    }
  }

  confirmDelete(invoice: Invoice) {
    this.confirm.confirm({
      message: `Delete Invoice "${invoice.invoiceNumber}"?`,
      accept: () => {
        this.invoiceService.deleteInvoice(invoice.id!).subscribe(() => {
          this.loadInvoices();
          this.message.add({ severity: 'success', summary: 'Invoice deleted' });
        });
      }
    });
  }

}
