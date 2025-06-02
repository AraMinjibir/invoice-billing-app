package mlk.invoiceapp.controlers;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import mlk.invoiceapp.entities.Invoice;
import mlk.invoiceapp.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class WebHookController {

    private final InvoiceRepository invoiceRepository;

    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    public WebHookController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }


    @PostMapping("/api/payments/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws IOException {
        String payload = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        String sigHeader = request.getHeader("Stripe-Signature");

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, stripeWebhookSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);

            if (intent != null) {
                String invoiceIdStr = intent.getMetadata().get("invoiceId");
                if (invoiceIdStr == null) {
                    return ResponseEntity.badRequest().body("Missing invoiceId metadata");
                }

                Long invoiceId;
                try {
                    invoiceId = Long.valueOf(invoiceIdStr);
                } catch (NumberFormatException ex) {
                    return ResponseEntity.badRequest().body("Invalid invoiceId format");
                }

                Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
                if (invoice != null && !invoice.isPaid()) {
                    invoice.setPaid(true);
                    invoice.setDate(LocalDate.from(LocalDateTime.now()));
                    invoiceRepository.save(invoice);
                }
            }
        }

        return ResponseEntity.ok("");
    }
}
