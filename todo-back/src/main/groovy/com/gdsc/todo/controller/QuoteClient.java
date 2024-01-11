package com.gdsc.todo.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "quoteClient", url = "https://api.adviceslip.com")
public interface QuoteClient {
    @GetMapping("/advice")
    String getData();
}
