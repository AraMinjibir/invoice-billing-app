package mlk.invoiceapp.sevices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mlk.invoiceapp.entities.Invoice;
import mlk.invoiceapp.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<Invoice> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setId(updatedInvoice.getId());
                    invoice.setCustomer(updatedInvoice.getCustomer());
                    invoice.setItems(updatedInvoice.getItems());
                    invoice.setTotalAmount(updatedInvoice.getTotalAmount());
                    invoice.setInvoiceNumber(updatedInvoice.getInvoiceNumber());
                    invoice.setDate(updatedInvoice.getDate());
                    return invoiceRepository.save(invoice);
                }).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Transactional
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }
}
