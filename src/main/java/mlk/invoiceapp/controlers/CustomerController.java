package mlk.invoiceapp.controlers;

import lombok.RequiredArgsConstructor;
import mlk.invoiceapp.entities.Customer;
import mlk.invoiceapp.entities.Invoice;
import mlk.invoiceapp.sevices.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.findAllCustomers();
    }
    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id){
        return customerService.findCustomerById(id);

    }
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return  customerService.updateCustomer(id, customer);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
         customerService.deleteCustomer(id);
    }
}
