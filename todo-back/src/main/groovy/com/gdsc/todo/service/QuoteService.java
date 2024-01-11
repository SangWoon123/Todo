package com.gdsc.todo.service;

import com.gdsc.todo.controller.QuoteClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteClient quoteClient;

    public String updateQuote() {
        return quoteClient.getData();
    }
}
