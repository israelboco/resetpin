package com.he.resetpin.controller;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import com.he.resetpin.model.Partenaire;
import com.he.resetpin.model.Response;
import com.he.resetpin.service.PartenaireService;

@RestController
@RequestMapping("/api/partenaire")
public class PartenaireController {
    
    @Autowired
    private PartenaireService partenaireService;

    @PostMapping(path="", consumes = {"application/json"})
    public Response savePartenaire(@RequestBody Partenaire p){
        
        Response response = new Response();
        response.setCode(200);
        response.setMessage("partenaire creer");
        response.setData(partenaireService.savePartenaire(p));
        return response;
    }

    @PostMapping(path="/create pin", consumes = {"application/json"})
    public Response createPin(@RequestBody String email){
        Partenaire p = partenaireService.getPartenaireByEmail(email);
        Response response = new Response();
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete");
        }
        else {
            response.setCode(200);
            response.setMessage("pin creer");
            response.setData(partenaireService.createPin(p));
        }
        return response;
    }

    @PostMapping("/demande reinitialisation")
    public Response demandeReinitialisation(@RequestBody String email){
        Partenaire p = partenaireService.getPartenaireByEmail(email);
        Response response = new Response();
        // Email incorrete
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete");
        }
        // Reset pin
        else if(p.getReinitialisable()) {
            response.setCode(200);
            response.setMessage("reset pin");
            // response.setData(partenaireService.createPin(p));
        }
        // Creer code OTP
        else{
            response.setCode(200);
            response.setMessage("code OTP creer: ");
            // response.setData(partenaireService.createPin(p));
        }
        return response;
    }

    // @GetMapping(path="/cours/{nom}", produces= "application/json")
	// public Response getCourByNom(@PathVariable String nom) {
		
	// 	Response response = new Response();
	// 	response.setCode(200);
	// 	response.setMessage("get recherche effectué");
	// 	response.setData(courService.nomCour(nom));
		
	// 	return response;
	// }
	
	
	// @DeleteMapping(path="/cours/{codeID}", produces= "application/json")
	// public Response deleteCour(@PathVariable int codeID) {
		
	// 	courService.deleteCourById(codeID);
	// 	Response response = new Response();
	// 	response.setCode(1);
	// 	response.setMessage("cour supprimé");
		
	// 	return response;
	// }
}
