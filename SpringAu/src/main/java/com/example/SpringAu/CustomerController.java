package com.example.SpringAu;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CustomerController {
    private final String BASE_URL = "https://qa2.sunbasedata.com/sunbase/portal/api";
    private final ApiService apiService;

    public CustomerController(ApiService apiService) {
        this.apiService = apiService;
    }


    @GetMapping("/create")
    public String createCustomerPage() {
        return "createCustomer";
    }

    @PostMapping("/create")
    public String createCustomer(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("street") String street,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            Model model
    ) {
        Customer newCustomer = new Customer(firstName, lastName, street, address, city, state, email, phone);

        ResponseEntity<String> response = apiService.createCustomer(newCustomer);

        if (response.getStatusCode().is2xxSuccessful()) {
            model.addAttribute("message", "Customer created successfully!");
        } else {
            model.addAttribute("message", "Failed to create customer. Please try again.");
        }

        return "createCustomer";
    }

    @GetMapping("/list")
    public String listCustomers(Model model) {
        String token = apiService.getBearerToken("joyalldeep885@gmail.com", "12345678"); // Replace with your actual login credentials
        if (token != null) {
            List<Customer> customers = apiService.getCustomerList(token);
            model.addAttribute("customers", customers);
        } else {
            // Handle token retrieval error
        }
        return "listCustomers"; // Return the name of the JSP file
    }

    @PostMapping("/delete/{uuid}")
    public String deleteCustomer(@PathVariable String uuid) {
        boolean success = apiService.deleteCustomer(uuid);
        if (success) {
            return "redirect:/customers/list";
        } else {
            return "errorPage"; // Redirect to an error page if deletion fails
        }
    }

    @GetMapping("/update/{uuid}")
    public String updateCustomerPage(@PathVariable String uuid, Model model) {
        Customer customer = apiService.getCustomerByUuid(uuid);
        model.addAttribute("customer", customer);
        return "updateCustomer"; // Return the name of the JSP file
    }

    @PostMapping("/update/{uuid}")
    public String updateCustomer(
            @PathVariable String uuid,
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName,
            @RequestParam("street") String street,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("state") String state,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ) {
        boolean success = apiService.updateCustomer(uuid, firstName, lastName, street, address, city, state, email, phone);
        if (success) {
            return "redirect:/customers/list";
        } else {
            return "errorPage"; // Redirect to an error page if update fails
        }
    }
    // Implement methods for listing, updating, and deleting customers
    // Implement methods for listing, updating, and deleting customers
}
