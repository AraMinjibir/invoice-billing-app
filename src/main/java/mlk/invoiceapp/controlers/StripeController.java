package mlk.invoiceapp.controllers;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import mlk.invoiceapp.entities.Invoice;
import mlk.invoiceapp.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class StripeController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping("/stripe")
    public ResponseEntity<?> payInvoice(@RequestBody Map<String, String> payload) {
        String invoiceId = payload.get("invoiceId");
        Long id = Long.valueOf(invoiceId);  // convert String to Long

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (invoice.isPaid()) {
            return ResponseEntity.badRequest().body("Invoice already paid");
        }

        long amountInKobo = invoice.getAmount() * 100;

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInKobo)
                    .setCurrency("ngn")
                    .putMetadata("invoiceId", invoiceId)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            return ResponseEntity.ok(Map.of(
                    "clientSecret", intent.getClientSecret(),
                    "invoiceId", invoiceId
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Payment error: " + e.getMessage());
        }
    }

}
