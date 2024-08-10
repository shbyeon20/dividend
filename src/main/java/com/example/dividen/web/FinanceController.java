package com.example.dividen.web;

import com.example.dividen.service.FinanceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor

public class FinanceController {
    private final FinanceService financeService;


    @GetMapping("/dividend/{companyname}")
    public ResponseEntity<?> searchFinance(@PathVariable String companyname) {

        return ResponseEntity.ok(financeService.getDividendByName(companyname));
    }
}
