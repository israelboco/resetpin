package com.he.resetpin.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestBody;


import com.he.resetpin.model.Partenaire;
import com.he.resetpin.model.Response;
import com.he.resetpin.model.VerificationCodeOTP;
import com.he.resetpin.service.CodeOTPService;
import com.he.resetpin.service.PartenaireService;

@RestController
@RequestMapping("/api/partenaire")
public class PartenaireController {
    
    @Autowired
    private PartenaireService partenaireService;

    @Autowired
    private CodeOTPService codeOTPService;

    @PostMapping(path="", consumes = {"application/json"})
    public Response savePartenaire(@RequestBody Partenaire p){
        
        Response response = new Response();
        response.setCode(200);
        response.setMessage("partenaire creer");
        response.setData(partenaireService.savePartenaire(p));
        return response;
    }

    @PostMapping(path="/createpin", consumes = {"application/json"})
    public Response createPin(@RequestBody String[] emailcode) throws NoSuchAlgorithmException, InvalidKeySpecException{
        Partenaire p = partenaireService.getPartenaireByEmail(emailcode[0]);
        Response response = new Response();
        // Email incorrete
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete : " + emailcode);
        }
        // Creation du pin
        else {
            response.setCode(200);
            response.setMessage("pin creer");
            response.setData(partenaireService.createPin(p, emailcode[1]));
        }
        return response;
    }

    @PostMapping("/demandereinitialisation")
    public Response demandeReinitialisation(@RequestBody String email) throws NoSuchAlgorithmException, InvalidKeySpecException{
        Partenaire p = partenaireService.getPartenaireByEmail(email);
        Response response = new Response();
        // Email incorrete
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete : " + email);
        }
        // Reset pin
        else if(p.getReinitialisable()) {
            String url = "127.0.0.1:8080/api/partenaire/resetpin";
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.getForObject(url, Response.class, p);
        }
        // Creation de code OTP
        else{
            response.setCode(200);
            response.setMessage("code OTP creer dans l'objet ");
            response.setData(codeOTPService.createCode(p));
        }
        return response;
    }

    @PostMapping(path="/resetpin", consumes = {"application/json"})
    public Response resetPin(@RequestBody Partenaire partenaire) throws NoSuchAlgorithmException, InvalidKeySpecException{
        Partenaire p = partenaireService.getPartenaireByEmail(partenaire.getEmail());
        Response response = new Response();
        // Email incorrete
        if(p.getId() == 0){
            response.setCode(200);
            response.setMessage("email incorrete");
        }
        // Creation du pin
        else {
            response.setCode(200);
            response.setMessage("pin generé");
            response.setData(partenaireService.createPin(p, partenaire.getPin()));
        }
        return response;
    }

    @PostMapping(path="/verificationpin", consumes = {"application/json"})
    public Response verificatePin(@RequestBody Partenaire partenaire) throws NoSuchAlgorithmException, InvalidKeySpecException{
        Partenaire p = partenaireService.getPartenaireByEmail(partenaire.getEmail());
        Response response = new Response();
        // Email incorrete
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete");
        }
        // Verification du pin
        else if(partenaireService.verificatePin(p, partenaire.getPin())){
            response.setCode(200);
            response.setMessage("pin authentique");
        }
        else{
            response.setCode(200);
            response.setMessage("pin non authentique");
        }
        return response;
    }

    @PostMapping(path="/certificationdemande", consumes = {"application/json"})
    public Response certificationDemande(@RequestBody VerificationCodeOTP valeur){
        Partenaire p = partenaireService.getPartenaireByEmail(valeur.getEmail());
        Response response = new Response();
        // Email incorrete
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete");
        }
        // Enregitrement de la validation si certification reussi
        else{
            response.setCode(200);
            response.setMessage("codeOTP certifié");
            response.setData(codeOTPService.verificateCodeOTP(valeur));
        }
        return response;
    }
	
	
	// @DeleteMapping(path="/cours/{codeID}", produces= "application/json")
	// public Response deleteCour(@PathVariable int codeID) {
		
	// 	courService.deleteCourById(codeID);
	// 	Response response = new Response();
	// 	response.setCode(1);
	// 	response.setMessage("cour supprimé");
		
	// 	return response;
	// }
}
