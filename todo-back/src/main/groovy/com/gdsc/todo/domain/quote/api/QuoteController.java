package com.gdsc.todo.domain.quote.api;

import com.gdsc.todo.domain.quote.service.QuoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "명언 구문 API호출",description = "단순 API호출")
@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;
    @Operation(summary = "명언 구문 호출하기")
    @GetMapping()
    public String getMessage() {
        return quoteService.updateQuote();
    }
}
