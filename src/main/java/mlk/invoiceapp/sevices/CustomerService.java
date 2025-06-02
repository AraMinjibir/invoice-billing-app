package mlk.invoiceapp.sevices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mlk.invoiceapp.entities.Customer;
import mlk.invoiceapp.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Long id){
        return customerRepository.findById(id);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer updatedCustomer){
        return customerRepository.findById(id).map(customer ->{
            customer.setId(updatedCustomer.getId());
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());

            return customerRepository.save(customer);

        }).orElseThrow(() -> new RuntimeException("Customer not found!."));
    }
}
