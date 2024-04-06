package com.gdsc.todo.domain.quote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "quoteClient", url = "https://api.adviceslip.com")
public interface QuoteClient {
    @GetMapping("/advice")
    String getData();
}
