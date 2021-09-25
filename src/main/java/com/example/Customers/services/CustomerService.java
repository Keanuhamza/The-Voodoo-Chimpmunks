package com.example.Customers.services;
import java.util.List;

import com.example.Customers.exceptions.*;
import com.example.Customers.models.*;
import com.example.Customers.repositories.*;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    
    private final CustomerRepository repository;
    private final ContactRepository contactRepository;

    CustomerService(CustomerRepository repository, ContactRepository contactrepo) {
        this.repository = repository;
        this.contactRepository = contactrepo;
    }

    public Customer addCustomer(Customer customer) {   
        return repository.save(customer);
    }

    public Customer getCustomer(Long id) {   
        return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public List<Customer> getAllCustomers() {   
        return repository.findAll();
    }

    // update customer
    public Customer updateCustomer(Customer newCustomer, Long id) {
    return repository.findById(id)
      .map(customer -> {
        customer.setCompanyName(newCustomer.getCompanyName());
        customer.setAddress(newCustomer.getAddress());
        customer.setCountry(newCustomer.getCountry());
        return repository.save(customer);
      })
      .orElseGet(() -> {
        newCustomer.setId(id);
        return repository.save(newCustomer);
      });
    }

    // update customer contact
    public Customer updateCustomerContact(Long id, long contactid) {
        Customer customer = repository.findById(id).orElseThrow(RuntimeException::new);
        Contact contact = contactRepository.findById(contactid).orElseThrow(RuntimeException::new);
        customer.setContact(contact);
        return repository.save(customer);
    }

    public void deleteCustomer(Long id) {   
        repository.deleteById(id);
    }

}
