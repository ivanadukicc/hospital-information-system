package com.ivana.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ivana.demo.dto.RegistrationForm;
import com.ivana.demo.repositories.KorisnikRepo;
import com.ivana.demo.services.KorisnikService;

import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	KorisnikService ks;
	@Autowired
	KorisnikRepo kr;
	
	
	@GetMapping("/login")
	public String loginPage(Authentication auth) {
		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
		return "login";
	}
	
	@GetMapping("/")
	public String root(Authentication auth) {
		if (auth == null || !auth.isAuthenticated() || (auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/login";
        }
		 boolean lekar = auth.getAuthorities().stream()
	                .anyMatch(a -> a.getAuthority().equals("ROLE_LEKAR"));
	        return lekar ? "redirect:/lekar" : "redirect:/pacijent";
	}

	
	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("form", new RegistrationForm());
		return "register";
	}
	
	@PostMapping("/register")
	public String doRegister(@Valid @ModelAttribute("form") RegistrationForm form,BindingResult errors, Model model,RedirectAttributes ra) {
		 
		if (kr.existsByUsername(form.getUsername())) {
		        errors.rejectValue("username", null, "Korisničko ime je zauzeto.");
		    }

		 if (errors.hasErrors()) {
		        return "register";
		    }
		try {
			ks.registerPatient(form);
			ra.addFlashAttribute("ok", "Uspešno ste se registrovali. Ulogujte se.");
			return "redirect:/login?registered";
		} catch (IllegalArgumentException e) {
			model.addAttribute("err", e.getMessage());
			return "register";
		}
		
		
	}
}
