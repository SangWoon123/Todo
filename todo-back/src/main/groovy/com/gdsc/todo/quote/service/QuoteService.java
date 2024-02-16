package com.gdsc.todo.quote.service;

import com.gdsc.todo.quote.QuoteClient;
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
