package com.testlab2.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ApplicationController {
//    log.trace();
    private CryptoService cpService;

    public ApplicationController(CryptoService cryptoService){
        this.cpService = cryptoService;
    }

    @GetMapping("/hello")
    public String shouldGreetDefault(@RequestParam(
            name = "targetName",
            defaultValue = "Oscar") String name) {
        log.trace("Running shouldGreetDefault() method...");
        return String.format("Hello %s", name);
    }

    @GetMapping("/status")
    public String status() {
        log.trace("Running status() method...");
        return "Congratulations - you must be an admin since you can see the application's status information";
    }

    @GetMapping("/getCryptoPrice/{cryptocurrency}")
    public String getPriceOfCoin(@PathVariable String cryptocurrency) {
        log.trace("Running getPriceOfCoin() method...");
        return "The Price of " + cryptocurrency + " is: " + cpService.getCoinPrice(cryptocurrency);
    }
}
