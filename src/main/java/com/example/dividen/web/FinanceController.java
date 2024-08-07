package com.example.dividen.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
public class FinanceController {


    @GetMapping("/dividend/{companyname}")
    public ResponseEntity<?> searchFinance(@PathVariable String companyname) {
        return null;
    }
}
