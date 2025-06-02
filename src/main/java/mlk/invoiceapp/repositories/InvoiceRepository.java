package mlk.invoiceapp.repositories;

import mlk.invoiceapp.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
