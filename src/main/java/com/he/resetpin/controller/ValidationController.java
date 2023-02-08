package com.he.resetpin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.he.resetpin.model.Response;
import com.he.resetpin.service.ValidationService;

@RestController
@RequestMapping("/api")
public class ValidationController {
    
    @Autowired
    private ValidationService validationService;

    @GetMapping(path="/validation", produces= "application/json")
	public Response getCourByNom(@RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
		Response response = new Response();
		response.setCode(200);
		response.setMessage("lites des validations en attente");
		response.setData(validationService.getAll(paging));
		
		return response;
	}
}