package mlk.invoiceapp.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

    private Long totalAmount;
    private Long amount;
    private boolean paid;
    private String invoiceNumber;
    private LocalDate date;


    public Long getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }


   @ManyToOne
   private Customer customer;

   @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
   private List<Items> items;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getId() {
        return id;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Invoice() {
    }

    public Long getAmount() {
        return amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Invoice(Long id, String invoiceNumber, LocalDate date, Customer customer, List<Items> Items) {
        this.id = id;
        this. invoiceNumber = invoiceNumber;
        this.date = date;
        this.customer = customer;
        this.items = items;
    }


}
