package com.naina.k.security.demo;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accountant")
public class AccountantController {

    @GetMapping
    public String get(){
        return  "GET:: accountant controller";
    }

    @PostMapping
    public String post(){
        return  "POST:: accountant controller";
    }

    @PutMapping
    public String put(){
        return  "PUT:: accountant controller";
    }

    @DeleteMapping
    public String delete(){
        return  "DELETE:: accountant controller";
    }
}
