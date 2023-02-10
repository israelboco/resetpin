package com.he.resetpin.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import com.amdelamar.jhash.exception.InvalidHashException;
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

    @GetMapping(path="", produces= "application/json")
	public Response getpartenaire(@RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
		Response response = new Response();
		response.setCode(200);
		response.setMessage("lites des validations en attente");
		response.setData(partenaireService.getAll(paging));
		
		return response;
	}

    @PostMapping(path="", consumes = {"application/json"})
    public Response savePartenaire(@RequestBody Partenaire p){
        Partenaire partenaireEmail = partenaireService.getPartenaireByEmail(p.getEmail());
        Partenaire partenaireTelephone = partenaireService.getPartenaireByTelephone(p.getTelephone());
        Response response = new Response();
        // Email incorrete
        if(partenaireEmail != null || partenaireTelephone != null){
            response.setCode(200);
            response.setMessage("email ou telephone exite");
        }
        // Creation du pin
        else {
            response.setCode(200);
            response.setMessage("partenaire creer");
            response.setData(partenaireService.savePartenaire(p));
        }
        
        return response;
    }

    @PostMapping(path="/createpin", consumes = {"application/json"})
    public Response createPin(@RequestBody String[] emailcode) throws NoSuchAlgorithmException, InvalidKeySpecException{
        Partenaire p = partenaireService.getPartenaireByEmail(emailcode[0]);
        Response response = new Response();
        // Email incorrete
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete : ");
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
    public Response demandeReinitialisation(@RequestBody Partenaire partenaire) throws NoSuchAlgorithmException, InvalidKeySpecException{
        Partenaire p = partenaireService.getPartenaireByEmail(partenaire.getEmail());
        Response response = new Response();
        // Email incorrete
        if(p == null){
            response.setCode(200);
            response.setMessage("email incorrete : ");
        }
        // Reset pin
        else if(p.getReinitialisable()) {
            // String url = "127.0.0.1:8080/api/partenaire/resetpin";
            // RestTemplate restTemplate = new RestTemplate();
            // response = restTemplate.getForObject(url, Response.class, partenaire);
            response.setCode(200);
            response.setMessage("pin reinitialisable redirection:  127.0.0.1:8080/api/partenaire/resetpin");
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // Email incorrete
        if(p.getId() == 0){
            response.setCode(200);
            response.setMessage("email incorrete");
        }
        // Creation du pin
        else if(p.getReinitialisable()) {
            // if(p.getDateReinitialisation().after(cal.getTime()))
            response.setCode(200);
            response.setMessage("pin regeneré");
            response.setData(partenaireService.createPin(p, partenaire.getPin()));
        }
        else {
            response.setCode(200);
            response.setMessage("le pin n'est pas regenererable, faites une demande");
        }
        return response;
    }

    @PostMapping(path="/verificationpin", consumes = {"application/json"})
    public Response verificatePin(@RequestBody Partenaire partenaire) throws InvalidHashException{
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
        else if(codeOTPService.verificateCodeOTP(valeur)) {
            response.setCode(200);
            response.setMessage("codeOTP certifié");
        }
        else {
            response.setCode(200);
            response.setMessage("codeOTP incorrete ou expiré");
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
