package mlk.invoiceapp.sevices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mlk.invoiceapp.entities.Items;
import mlk.invoiceapp.repositories.ItemsRepository;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsRepository itemRepository;

    public List<Items> findAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Items> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    @Transactional
    public Items createItem(Items item) {
        return itemRepository.save(item);
    }

    @Transactional
    public Items updateItem(Long id,Items updatedItem) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setPrice(updatedItem.getPrice());
                    item.setItemName(updatedItem.getItemName());
                    item.setDescription(updatedItem.getDescription());
                    item.setInvoice(updatedItem.getInvoice());
                    return itemRepository.save(item);
                }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
