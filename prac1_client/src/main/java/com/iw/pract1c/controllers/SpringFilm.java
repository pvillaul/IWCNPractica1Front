package com.iw.pract1c.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import com.iw.pract1c.models.Error;
import com.iw.pract1c.models.Pelicula;
import com.iw.pract1c.models.User;
import com.iw.pract1c.repositories.UserRepository;

@Controller
public class SpringFilm {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${rest.server.url}")
    private String restServerUrl;
	
	@RequestMapping(value = {"/","/login"})
	public String login() {
		return "index";
	}
	
	@RequestMapping(value = {"/logout"})
	public String logout() {
		return "redirect:/login";
	}
	
	@Secured({"ROLE_VIEWER","ROLE_ADMIN"})
	@GetMapping("/search")
	public String searchFilm(Model model, @ModelAttribute("searchFilm") Pelicula searchFilm, BindingResult result) {
		Pelicula pelicula = restTemplate.getForObject(restServerUrl + "movie/find/" + searchFilm.getName(),Pelicula.class);
		model.addAttribute("pelicula",pelicula);
		return "resumeFilm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/listUsers")
	public String listUsers(Model model) {
		List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
		return "listUsers";
	}
	
	@SuppressWarnings("unchecked")
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/listFilms")
	public String listFilms(Model model) {
		List<Pelicula> films = restTemplate.getForObject(restServerUrl + "movie/list",List.class);
		model.addAttribute("films", films);
		return "listFilms";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/user/{id}")
	public String showUser(@PathVariable String id, Model model) {
		User user = userRepo.findByCode(id);
		model.addAttribute("user",user);
		return "resumeUser";
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/pelicula/{id}")
	public String showFilm(@PathVariable String id, Model model) {
		Pelicula pelicula = restTemplate.getForObject(restServerUrl + "movie/find/" + id,Pelicula.class);
		model.addAttribute("pelicula",pelicula);
		return "resumeFilm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/removeUser/{id}")
	public String deleteUser(@PathVariable String id, Model model) {
		if(userRepo.existsById(id)) {
			userRepo.deleteById(id);
		}
		return "redirect:/listUsers";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/removeFilm/{id}")
	public String deleteFilm(@PathVariable String id, Model model) {
		restTemplate.exchange(restServerUrl + "movie/remove/"+id, HttpMethod.DELETE, new HttpEntity<>(id),String.class);
		return "redirect:/listFilms";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/addUserForm")
	public String addUser(@ModelAttribute User user) {
		return "addUserForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/addFilmForm")
	public String addFilm(@ModelAttribute Pelicula peli) {
		return "addFilmForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/addUser")
	public String newProd(@ModelAttribute("user") User user,BindingResult result, ModelMap model) {
		if(user != null) {
			model.addAttribute("code", user.getCode());
			model.addAttribute("name", user.getName());
	        model.addAttribute("password", user.getPassword());
	        model.addAttribute("rol", user.getRol());
	        if(!userRepo.existsById(user.getCode())) {
	        	userRepo.save(user);
	        }
	        return "redirect:/listUsers";
		} else {
			Error error = new Error("Error al Crear el Usuario","El codigo introducido coincide con un usuario existente","addUserForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/addFilm")
	public String newProd(@ModelAttribute("film") Pelicula film,BindingResult result, ModelMap model) {
		if(film != null) {
			model.addAttribute("code", film.getName());
	        model.addAttribute("name", film.getName());
	        model.addAttribute("trailer", film.getTrailer());
	        model.addAttribute("info", film.getInfo());
	        model.addAttribute("year", film.getYear());
	        model.addAttribute("director", film.getDirector());
	        model.addAttribute("reparto", film.getReparto());
	        model.addAttribute("portada", film.getPortada());
	        model.addAttribute("rate", film.getRate());
	        restTemplate.exchange(restServerUrl + "movie/add", HttpMethod.POST, new HttpEntity<>(film),Pelicula.class);
	        return "redirect:/listFilms";
		} else {
			Error error = new Error("Error al Crear la Pelicula","El codigo introducido coincide con una pelicula existente","addFilmForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modifyUserForm/{id}")
	public String modifyUser(@PathVariable String id, Model model,@ModelAttribute User user) {
		User olduser = userRepo.findByCode(id);
		model.addAttribute("olduser",olduser);
		return "modifyUserForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modifyFilmForm/{id}")
	public String modifyFilm(@PathVariable String id, Model model,@ModelAttribute Pelicula peli) {
		Pelicula oldpeli = restTemplate.getForObject(restServerUrl + "movie/find/" + id,Pelicula.class);
		model.addAttribute("oldpeli",oldpeli);
		return "modifyFilmForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping(value = "/modifyUser")
	public String modifyUser(@ModelAttribute("user") User user,BindingResult result, ModelMap model) {
		if(user != null) {
			model.addAttribute("code", user.getCode());
			model.addAttribute("name", user.getName());
	        model.addAttribute("password", user.getPassword());
	        model.addAttribute("rol", user.getRol());
	        if(userRepo.existsById(user.getCode())) {
	        	userRepo.deleteById(user.getCode());
	        	userRepo.save(user);
	        }
	        return "resumeUser";
		} else {
			Error error = new Error("Error al Modificar el Usuario","El codigo introducido coincide con un usuario existente","addUserForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping(value = "/modifyFilm")
	public String modifyFilm(@ModelAttribute("film") Pelicula film,BindingResult result, ModelMap model) {
		if(film != null) {
			model.addAttribute("code", film.getName());
	        model.addAttribute("name", film.getName());
	        model.addAttribute("trailer", film.getTrailer());
	        model.addAttribute("info", film.getInfo());
	        model.addAttribute("year", film.getYear());
	        model.addAttribute("director", film.getDirector());
	        model.addAttribute("reparto", film.getReparto());
	        model.addAttribute("portada", film.getPortada());
	        model.addAttribute("rate", film.getRate());
	        restTemplate.exchange(restServerUrl + "movie/modify", HttpMethod.PUT, new HttpEntity<>(film),Pelicula.class);
	        return "resumeFilm";
		} else {
			Error error = new Error("Error al Modificar la Pelicula","El codigo introducido coincide con una pelicula existente","addFilmForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
}