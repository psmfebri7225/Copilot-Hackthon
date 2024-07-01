package com.myapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.myapp.service.NumberArrayService;
import com.myapp.exception.NoNumberArrayException;
import com.myapp.model.NumberArray;

@Controller
public class HomeController {

    private final NumberArrayService numberArrayService;

    public HomeController(NumberArrayService numberArrayService) {
        this.numberArrayService = numberArrayService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<NumberArray> numberArrays = numberArrayService.findAll();

        // Add numberArrays to the model
        model.addAttribute("numberArrays", numberArrays);

        // Add logic to populate the model with data
        return "home";
    }

    // add path to /number-array/using-code?code=xxxx
    @GetMapping("/number-array/using-code")
    public String numberArrayUsingCode(@RequestParam("code") String code, Model model) {
        
        try {
            UUID codeUuid = UUID.fromString(code);

            // get code from request query parameter
            // find NumberArray by code
            NumberArray numberArray = numberArrayService.findByCode(codeUuid);

            if (numberArray == null) {
                throw new NoNumberArrayException();
            }
            

            // convert numberArray to List<List<Integer>>
            List<List<Integer>> numberArrayList = convertJsonArrayToNumberArray(numberArray.getNumberArray());

            // add numberArray to the model
            model.addAttribute("code", code);
            model.addAttribute("numberArray", numberArrayList);

            
            return "number-array";
        } catch (IllegalArgumentException e) {
            // handle invalid UUID format
            // add error message to the model
            model.addAttribute("error", "Invalid UUID format");
            
            return "error";
        } catch (NoNumberArrayException e) {
            // handle NumberArray not found
            // add error message to the model
            model.addAttribute("error", "NumberArray not found");
            
            return "error";
        }
    }

    @GetMapping("/number-array/create")
    public String createNumberArray(Model model) {
        
        NumberArray numberArray = new NumberArray();
        numberArray = numberArrayService.save(numberArray);

        // redirect to /number-array/using-code?code=xxxx
        // with*ot port
        return "redirect:/number-array/using-code?code=" + numberArray.getCode();
    }

    // cretae rest api for /number-array/check
    // get [code, answer] from form data
    @PostMapping("/number-array/check")
    public ResponseEntity<Map<String, Object>> checkNumberArray(@RequestParam("code") String code, @RequestParam("answer") Integer answer) {
        try {
            UUID codeUuid = UUID.fromString(code);

            // get code from request query parameter
            // find NumberArray by code
            NumberArray numberArray = numberArrayService.findByCode(codeUuid);

            if (numberArray == null) {
                throw new NoNumberArrayException();
            }

            // compare numberArrayList and answerList
            boolean isCorrect = answer.equals(numberArray.getAnswer());

            // create response map
            Map<String, Object> response = new HashMap<>();
            response.put("isCorrect", isCorrect);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // handle invalid UUID format
            // create error response map
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid UUID format");

            return ResponseEntity.badRequest().body(errorResponse);
        } catch (NoNumberArrayException e) {
            // handle NumberArray not found
            // create error response map
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "NumberArray not found");

            return ResponseEntity.notFound().build();
        }
    }
    

    private List<List<Integer>> convertJsonArrayToNumberArray(String jsonArray) {
        ObjectMapper mapper = new ObjectMapper();
        List<List<Integer>> numberArray = null;
        try {
            numberArray = mapper.readValue(jsonArray, new TypeReference<List<List<Integer>>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberArray;
    }

}