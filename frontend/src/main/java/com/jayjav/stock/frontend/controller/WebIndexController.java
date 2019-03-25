package com.jayjav.stock.frontend.controller;

import com.google.gson.Gson;
import com.jayjav.stock.frontend.model.DisplayTable;
import com.jayjav.stock.frontend.model.GetQuote;
import com.jayjav.stock.frontend.model.Quote;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/")
public class WebIndexController {

    @Autowired
    private RestTemplate restTemplate;

//    private String addUserUrl = "http://192.9.201.163:8300/rest/db/add";
    private String addUserUrl = "http://192.9.201.163:8302/api/db-service/rest/db/add";
    private String getQuoteUrl = "http://192.9.201.163:8302/api/stock-service/rest/stock/";

    private static final Logger LOGGER = LoggerFactory.getLogger(WebIndexController.class);


    @GetMapping("/index")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView();
        Quote quotes = new Quote();
        modelAndView.addObject("quote", quotes);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping(value="/index")
    public ModelAndView addUser(@Valid Quote quote, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        HttpEntity<Quote> request = new HttpEntity<>(quote);
        LOGGER.info("<<<Calling DB Service >>>");
        String response = restTemplate.postForObject(addUserUrl, request, String.class);
        LOGGER.info("<<<Call response >>>" + response);
        modelAndView.addObject("quote", new Quote());
        modelAndView.addObject("reponse", response);
        modelAndView.setViewName("index");
        return modelAndView;

    }

    @GetMapping(value="/getquote")
    public ModelAndView getQuote(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("getquote", new GetQuote());
        modelAndView.setViewName("getquote");
        return modelAndView;

    }

    @PostMapping(value="/getquote")
    public ModelAndView getQuote(@Valid GetQuote getQuote, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        HttpEntity<GetQuote> request = new HttpEntity<>(getQuote);
//        DisplayTable displayTable = new DisplayTable();


        LOGGER.info("<<<Calling DB Service >>>");
        String getquotereponse = restTemplate.getForObject(getQuoteUrl + getQuote.getUserName(), String.class);
        String streamResponse = getquotereponse.replace("[", "");
        JSONObject responseToJson = new JSONObject(
                streamResponse.replace("]", "")
        );
        responseToJson.put("username", getQuote.getUserName());


        getQuote.setPrice((Double) responseToJson.get("price"));
        getQuote.setQuote((String) responseToJson.get("quote"));

        LOGGER.info("<<<Getting response >>>" + getQuote);
//        LOGGER.info("<<<Call response >>>" + jo);

        modelAndView.addObject("getquote", getQuote);
        modelAndView.addObject("getquotereponse", getquotereponse);
        modelAndView.setViewName("getquote");
        return modelAndView;

    }

    private HashMap<String, String> resolveResponse(String value){

        HashMap resolveString = new HashMap();
        if(value.contains("price")){

            String[] spliString = value.split(",");
            String resolvedPrice = spliString[1].replace("[{", "");
            String resolvedQuote = spliString[2].replace("}]", "");
            resolveString.put("Price", resolvedPrice.split(":")[1]);
            resolveString.put("Quote", resolvedQuote.split(":")[1]);

        }else {
            resolveString.put("Error", "Invalid Action");

        }
        return resolveString;
    }
//
//    public static void main(String[] args){
//        String nn = "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}";
//        String value = "[{\"price\":1205.5,\"quote\":\"GOOG\"}]";
//        String new_val = value.replace("[", "");
//        String new_val2 = new_val.replace("]", "");
////        WebIndexController w = new WebIndexController();
//
//        JSONObject jo = new JSONObject(
//                new_val2
//        );
//        System.out.println(jo);
//        System.out.println(jo.get("price"));
//    }
}
