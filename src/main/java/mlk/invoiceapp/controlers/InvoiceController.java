package mlk.invoiceapp.controlers;

import lombok.RequiredArgsConstructor;
import mlk.invoiceapp.entities.Invoice;
import mlk.invoiceapp.sevices.EmailService;
import mlk.invoiceapp.sevices.InvoiceService;
import mlk.invoiceapp.sevices.PdfGeneratorService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final PdfGeneratorService pdfGeneratorService;
    private final EmailService emailService;

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice);
    }


    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.findAllInvoices();
    }

    @GetMapping("/{id}")
    public Optional<Invoice> getInvoiceById(@PathVariable Long id) {
        return invoiceService.findInvoiceById(id);
    }

    @PutMapping("/{id}")
    public Invoice updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return invoiceService.updateInvoice(id, invoice);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> downloadInvoicePdf(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        byte[] pdfBytes = pdfGeneratorService.generateInvoicePdf(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=invoice_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(pdfBytes)));
    }

    @PostMapping("/{id}/email")
    public ResponseEntity<String> emailInvoiceToClient(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        byte[] pdfBytes = pdfGeneratorService.generateInvoicePdf(invoice);

        emailService.sendEmailWithAttachment(
                invoice.getCustomer().getEmail(),
                "Invoice #" + invoice.getInvoiceNumber(),
                "Dear " + invoice.getCustomer().getName() + ", please find your invoice attached.",
                pdfBytes,
                "invoice_" + invoice.getInvoiceNumber() + ".pdf"
        );

        return ResponseEntity.ok("Invoice emailed to " + invoice.getCustomer().getEmail());
    }

}
