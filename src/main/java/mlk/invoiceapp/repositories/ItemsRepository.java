package mlk.invoiceapp.repositories;

import mlk.invoiceapp.entities.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Long> {
}
