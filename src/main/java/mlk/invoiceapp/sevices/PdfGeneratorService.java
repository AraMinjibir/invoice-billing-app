package mlk.invoiceapp.sevices;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import mlk.invoiceapp.entities.Invoice;
import mlk.invoiceapp.entities.Items;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
public class PdfGeneratorService {

    public byte[] generateInvoicePdf(Invoice invoice) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Paragraph header = new Paragraph("Invoice", fontHeader);
            header.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(header);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
            document.add(new Paragraph("Date: " + invoice.getDate()));
            document.add(new Paragraph("Customer: " + invoice.getCustomer().getName()));
            document.add(new Paragraph("Email: " + invoice.getCustomer().getEmail()));
            document.add(new Paragraph("Address: " + invoice.getCustomer().getAddress()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 2, 2});

            Stream.of("Description", "Quantity", "Price", "Total").forEach(columnTitle -> {
                PdfPCell headerCell = new PdfPCell();
                headerCell.setPhrase(new Phrase(columnTitle));
                headerCell.setBackgroundColor(Color.LIGHT_GRAY);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(headerCell);
            });

            double grandTotal = 0.0;
            for (Items item : invoice.getItems()) {
                table.addCell(item.getDescription());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.format("%.2f", item.getPrice()));
                double total = item.getQuantity() * item.getPrice();
                table.addCell(String.format("%.2f", total));
                grandTotal += total;
            }

            document.add(table);
            document.add(new Paragraph(" "));

            Paragraph total = new Paragraph("Grand Total: ₦" + String.format("%.2f", grandTotal));
            total.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(total);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

}
