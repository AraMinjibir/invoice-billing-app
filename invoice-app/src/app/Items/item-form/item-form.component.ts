import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import {Item} from '../../Model/item';


@Component({
  selector: 'item-form',
  imports: [ ReactiveFormsModule, InputTextModule, InputNumberModule, ButtonModule],
  templateUrl: './item-form.component.html',
  styleUrl: './item-form.component.scss'
})
export class ItemFormComponent {

  @Input() item: Item | null = null;
  @Output() save = new EventEmitter<Item>();

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      itemName: ['', Validators.required],
      description: [''],
      price: [0, [Validators.required, Validators.min(0)]],
      quantity: [1, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['item'] && this.item) {
      this.form.patchValue(this.item);
    }
  }

  submit() {
    if (this.form.valid) {
      this.save.emit(this.form.value as Item);
       this.form.reset();
    }
  }
}
