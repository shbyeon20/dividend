package com.example.dividen.web;

import com.example.dividen.model.Company;
import com.example.dividen.persist.entity.CompanyEntity;
import com.example.dividen.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocompelete")
    public ResponseEntity<?> autoComplete(@RequestParam String keyword) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageable) {
        Page<CompanyEntity> companies = companyService.getAllCompany(pageable);
        return ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company company){
        String ticker = company.getTicker();
        if(ObjectUtils.isEmpty(ticker)){
            throw new RuntimeException("ticker is empty");
        }
        Company save = this.companyService.save(ticker);

        return ResponseEntity.ok(save);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany(){
        return null;
    }
}
