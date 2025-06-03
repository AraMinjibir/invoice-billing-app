import { Component } from '@angular/core';
import {Item} from '../../Model/item';
import {ItemsService} from '../../Service/items.service';
import {ConfirmationService, MessageService} from 'primeng/api';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog';
import {ItemFormComponent} from '../item-form/item-form.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ToastModule} from 'primeng/toast';
import {ButtonModule} from 'primeng/button';

@Component({
  selector: 'items',
  imports: [TableModule,
    ButtonModule,
    DialogModule,
    ItemFormComponent,
    ConfirmDialogModule,
    ToastModule],
  providers: [ConfirmationService, MessageService],
  templateUrl: './items.component.html',
  styleUrl: './items.component.scss'
})
export class ItemsComponent {

  items: Item[] = [];
  selectedItem: Item | null = null;
  displayDialog = false;
  editMode = false;

  constructor(
    private itemService: ItemsService,
    private confirm: ConfirmationService,
    private message: MessageService
  ) {}

  ngOnInit() {
    this.loadItems();
  }

  loadItems() {
    this.itemService.getItems().subscribe(items => (this.items = items));
    console.log('Loaded items:', this.items);
  }

  openAdd() {
    this.editMode = false;
    this.selectedItem = null;
    this.displayDialog = true;
  }

  edit(item: Item) {
    this.editMode = true;
    this.selectedItem = { ...item };
    this.displayDialog = true;
  }
  saveItem(item: Item) {
    if (this.editMode && this.selectedItem?.id) {

      item.id = this.selectedItem.id;

      this.itemService.updateItem(this.selectedItem.id, item).subscribe(() => {
        this.loadItems();
        this.message.add({ severity: 'success', summary: 'Item updated' });
        this.displayDialog = false;
      });
    } else {
      this.itemService.createItem(item).subscribe(() => {
        this.loadItems();
        this.message.add({ severity: 'success', summary: 'Item added' });
        this.displayDialog = false;
      });
    }
  }


  confirmDelete(item: Item) {
    console.log('Opening confirm dialog for item:', item);
    this.confirm.confirm({
      message: `Delete "${item.itemName}"?`,
      accept: () => {
        console.log('Confirmed delete for item ID:', item.id); // ✅ Log this
        this.itemService.deleteItem(item.id!).subscribe(() => {
          this.loadItems();
          this.message.add({ severity: 'success', summary: 'Item deleted' });
        });
      },
      reject: () => {
        console.log('User cancelled delete');
      }
    });
  }


}
