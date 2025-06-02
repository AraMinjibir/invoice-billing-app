package mlk.invoiceapp.controlers;

import lombok.RequiredArgsConstructor;
import mlk.invoiceapp.entities.Items;
import mlk.invoiceapp.sevices.ItemsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemsController {
    private final ItemsService itemsService;

    @GetMapping
    public List<Items> getAllItems(){
        return itemsService.findAllItems();
    }

    @GetMapping("/{id}")
    public Optional<Items> getItemsById(@PathVariable Long id){
        return itemsService.findItemById(id);
    }

    @PostMapping
    public Items createItems(@RequestBody Items items){
        return itemsService.createItem(items);
    }

    @PutMapping("/{id}")
    public Items updateItems(@PathVariable Long id, @RequestBody Items items){
        return itemsService.updateItem(id, items);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id){
         itemsService.deleteItem(id);
    }

}
