/*package com.bootcampproject.controllers;


import com.bootcampproject.dto.SellerTO;
import com.bootcampproject.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping("/details")
    public SellerTO setDetails(SellerTO sellerTO)
    {
        return sellerService.sellerDetails(sellerTO);
    }
}*/
