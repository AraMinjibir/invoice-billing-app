import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {Invoice} from '../Model/invoice';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ButtonModule} from 'primeng/button';
import {InputNumberModule} from 'primeng/inputnumber';
import {CalendarModule} from 'primeng/calendar';
import {InputTextModule} from 'primeng/inputtext';

@Component({
  selector: 'invoice-form',
  imports: [ReactiveFormsModule, InputTextModule, CalendarModule, InputNumberModule, ButtonModule],
  templateUrl: './invoice-form.component.html',
  styleUrl: './invoice-form.component.scss'
})
export class InvoiceFormComponent {

  @Input() invoice: Invoice | null = null;
  @Output() save = new EventEmitter<Invoice>();

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      invoiceNumber: ['', Validators.required],
      date: ['', Validators.required],
      totalAmount: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['invoice'] && this.invoice) {
      this.form.patchValue(this.invoice);
    }
  }

  submit() {
    if (this.form.valid) {
      this.save.emit(this.form.value);
      this.form.reset();
    }
  }

}
