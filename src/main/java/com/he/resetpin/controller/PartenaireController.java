package com.he.resetpin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.he.resetpin.model.Partenaire;
import com.he.resetpin.model.Response;
import com.he.resetpin.service.PartenaireService;

@RestController
public class PartenaireController {
    
    @Autowired
    private PartenaireService partenaireService;

    @PostMapping("/partenaire")
    public Response savePartenaire(@PathVariable Partenaire p){
        
        Response response = new Response();
        response.setCode(200);
        response.setMessage("partenaire creer");
        response.setData(partenaireService.savePartenaire(p));
        return response;
    }

    @PostMapping("/partenaire/createpin")
    public Response createPin(@PathVariable Partenaire p){

        Response response = new Response();
        response.setCode(200);
        response.setMessage("pin creer");
        response.setData(partenaireService.savePartenaire(p));
        return response;
    }
}
