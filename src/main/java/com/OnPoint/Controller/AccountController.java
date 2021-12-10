// TODO sesuaikan dengan package Anda: package com.alvinJmartRK.controller;
package com.OnPoint.Controller;

// TODO sesuaikan dengan package Anda: import com.alvinJmartRK.Account;
import com.OnPoint.Account;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController
{
    @GetMapping
    String index() { return "account page"; }

    @PostMapping("/register")
    Account register
            (
                    @RequestParam String name,
                    @RequestParam String email,
                    @RequestParam String password
            )
    {
        return new Account();
    }

    @GetMapping("/{id}")
    String getById(@PathVariable int id) { return "account id " + id + " not found!"; }
}