package com.jayjav.stock.dbservice.controller;


import com.jayjav.stock.dbservice.model.Quote;
import com.jayjav.stock.dbservice.model.Quotes;
import com.jayjav.stock.dbservice.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceController {

    @Autowired
    private QuotesRepository quotesRepository;

    public DbServiceController(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username") final String username){

        return getQuotesByUserName(username);
    }

//    @PostMapping("/add")
//    public List<String> add(@RequestBody final Quotes quotes){
//
//        quotes.getQuotes()
//                .stream()
//                .map(quote -> new Quote(quotes.getUserName(), quote))
//                .forEach(quote -> quotesRepository.save(quote));
//
//        return getQuotesByUserName(quotes.getUserName());
//    }

    @PostMapping("/add")
    public List<String> add(@RequestBody final Quote quote){

        quotesRepository.save(quote);

        return getQuotesByUserName(quote.getUserName());
    }

    @DeleteMapping("/delete/{username}")
    public List<String> delete(@PathVariable("username" )final String username){

        List<Quote> quote = quotesRepository.findByUserName(username);
        quotesRepository.delete(quote);

        return getQuotesByUserName(username);
    }

    private List<String> getQuotesByUserName(@PathVariable("username") String username) {
        return quotesRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
    }
}
