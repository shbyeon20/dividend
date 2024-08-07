package com.example.dividen.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company")
public class CompanyController {
    @GetMapping("/autocompelete")
    public ResponseEntity<?> autoComplete(@RequestParam String keyword) {
        return null;
    }
    @GetMapping()
    public ResponseEntity<?> seachCompany() {
        return null;
    }

    @PostMapping()
    public ResponseEntity<?> addCompany(){
        return null;
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteCompany(){
        return null;
    }
}
