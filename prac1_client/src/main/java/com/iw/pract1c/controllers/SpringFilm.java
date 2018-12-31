package com.iw.pract1c.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.iw.pract1c.models.Error;
import com.iw.pract1c.models.Pelicula;
import com.iw.pract1c.models.PeliculaException;
import com.iw.pract1c.models.User;
import com.iw.pract1c.models.UserException;
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
	@GetMapping("/searchFilm")
	public String searchFilm(@RequestParam (value = "name", required = true) String name, @RequestParam (value = "year", required = false) String year,
			@RequestParam (value = "genre", required = false) String genre, @RequestParam (value = "director", required = false) String director,
			@RequestParam (value = "cast", required = false) String cast, @RequestParam (value = "score", required = false) String score, Model model) {
		try{
			Pelicula[] peliculas = restTemplate.getForObject(restServerUrl + "movie/find?name=" + name + "&year=" + year + "&genre=" + genre + "&director=" + director + "&cast=" + cast + "&score=" + score,Pelicula[].class);
			if(peliculas != null) {
				model.addAttribute("peliculas", peliculas);
				return "index";
			}
			else{
				Error error = new Error("Error!!","No se ha obtenido resultados al realizar la busqueda","","Back Home");
				//String errorMessage = "No se ha obtenido resultados al realizar la busqueda";
				model.addAttribute("error",error);
				return "aviso";
			}
		}
		catch(HttpStatusCodeException exception){
			//Error error = new Error("Error!!","No se ha obtenido resultados al realizar la busqueda","","Back Home");
			String errorMessage = "No se ha obtenido resultados al realizar la busqueda";
			model.addAttribute("error",errorMessage);
			return "index";
		}

	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/listUsers")
	public String listUsers(Model model) {
		List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        if(users != null) {
        	return "listUsers";
        } else {
        	Error error = new Error("Error!!","No se han cargado los usuarios","","Back Home");
			model.addAttribute("error",error);
			return "aviso";
        }
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/listFilms")
	public String listFilms(Model model) {
		Pelicula[] films = restTemplate.getForObject(restServerUrl + "movie/list",Pelicula[].class);
		model.addAttribute("films", films);
		if(films != null) {
        	return "listFilms";
        } else {
        	Error error = new Error("Error!!","No se han cargado las peliculas","","Back Home");
			model.addAttribute("error",error);
			return "aviso";
        }
	}
	
	@Secured({"ROLE_VIEWER","ROLE_ADMIN"})
	@GetMapping("/catalogo")
	public String catalogo(Model model) {
		try{
			Pelicula[] films = restTemplate.getForObject(restServerUrl + "movie/list",Pelicula[].class);
			if(films.length>0) {
				model.addAttribute("films", films);
				return "catalogo";
			}
			else{
				String errorMessage = "No se ha obtenido resultados al realizar la busqueda";
				model.addAttribute("errorAll",errorMessage);
				return "index";
			}
		}
		catch(HttpStatusCodeException exception){
			String errorMessage = "No se ha obtenido resultados al realizar la busqueda";
			model.addAttribute("errorAll",errorMessage);
			return "index";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/user")
	public String showUser(@RequestParam String id, Model model) throws UserException {
		User user = userRepo.findByName(id);
		if (user == null) {
			throw new UserException(2,"NOT_EXISTS");
		} else {
			model.addAttribute("user",user);
			return "resumeUser";
		}
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/pelicula")
	public String showFilm(@RequestParam String id, Model model) throws PeliculaException {
		Pelicula pelicula = restTemplate.getForObject(restServerUrl + "movie/find/" + id,Pelicula.class);
		if (pelicula == null) {
			throw new PeliculaException(2,"NOT_EXISTS");
		} else {
			model.addAttribute("pelicula",pelicula);
			return "resumeFilm";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/removeUser/{id}")
	public String deleteUser(@PathVariable String id, Model model) throws UserException {
		if(!userRepo.existsById(id)) {
			throw new UserException(2,"NOT_EXITS");
		} else {
			if(id == "root") {
				Error error = new Error("Error!!","El usuario Root no se puede eliminar","listUsers","Back Users List");
				model.addAttribute("error",error);
				return "aviso";
			} else {
				userRepo.deleteById(id);
				return "redirect:/listUsers";
			}
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/removeFilm/{id}")
	public String deleteFilm(@PathVariable String id, Model model) throws PeliculaException {
		Pelicula pelicula = restTemplate.getForObject(restServerUrl + "movie/find/" + id,Pelicula.class);
		if (pelicula == null) {
			throw new PeliculaException(2,"NOT_EXITS");
        } else {
        	restTemplate.exchange(restServerUrl + "movie/remove/"+id, HttpMethod.DELETE, new HttpEntity<>(id),String.class);
    		return "redirect:/listFilms";
        }
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/addUserForm")
	public String addUser(@ModelAttribute User user) {
		return "addUserForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/addFilmForm")
	public String addFilm(@ModelAttribute Pelicula film) {
		return "addFilmForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/addUser")
	public String newUser(@ModelAttribute("user") User user,BindingResult result, ModelMap model) throws UserException {

		model.addAttribute("name", user.getName());
		model.addAttribute("password", user.getPassword());
		model.addAttribute("rol", user.getRol());
		if(!userRepo.existsById(user.getName())) {
			userRepo.save(user);
			return "redirect:/listUsers";
		} else {
			//Error error = new Error("Error al Crear el Usuario","El codigo introducido coincide con un usuario existente","addUserForm","Volver al formulario");
			String errorMessage = "El nombre introducido coincide con un usuario existente";
			model.addAttribute("error",errorMessage);
			return "addUserForm";
		}

	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/addFilm")
	public String newMovie(@ModelAttribute("pelicula") Pelicula film,BindingResult result, ModelMap model) throws PeliculaException {
		if(film.getName() != null && !film.getName().isEmpty()) {
	        model.addAttribute("name", film.getName());
	        model.addAttribute("trailer", film.getTrailer());
	        model.addAttribute("info", film.getInfo());
	        model.addAttribute("year", film.getYear());
	        model.addAttribute("director", film.getDirector());
	        model.addAttribute("genre", film.getGenre());
	        model.addAttribute("reparto", film.getReparto());
	        model.addAttribute("portada", film.getPortada());
	        model.addAttribute("rate", film.getRate());

	        restTemplate.exchange(restServerUrl + "movie/add", HttpMethod.POST, new HttpEntity<>(film),Pelicula.class);
	        return "redirect:/listFilms";
		} else {
			Error error = new Error("Error al Crear la Pelicula","No se introdujo datos a la pelicula","addFilmForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modifyUserForm")
	public String modifyUser(@RequestParam String id, Model model, @ModelAttribute User user) {
		User olduser = userRepo.findByName(id);
		model.addAttribute("user",olduser);
		return "modifyUserForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modifyFilmForm")
	public String modifyFilm(@RequestParam String id, Model model, @ModelAttribute Pelicula film) throws PeliculaException{
		Pelicula oldpeli = restTemplate.getForObject(restServerUrl + "movie/find/" + id,Pelicula.class);
		//System.out.println(oldpeli.getName());

		if (oldpeli == null) {
			throw new PeliculaException(2,"NOT_EXISTS");
		} else {
			model.addAttribute("pelicula",oldpeli);
			return "modifyFilmForm";
		}

	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/modifyUser")
	public String modifyUser(@ModelAttribute("user") User user,BindingResult result, ModelMap model) throws UserException {
		if(user != null) {
			model.addAttribute("name", user.getName());
	        model.addAttribute("password", user.getPassword());
	        model.addAttribute("rol", user.getRol());
	        if(userRepo.existsById(user.getName())) {
				if(user.getPassword() == null || user.getPassword().isEmpty()){
					user.setPassword("1234"); // If password is empty set default password
				}
				if(user.getRol() == null || user.getRol().isEmpty()){
					user.setRol(userRepo.findByName(user.getName()).getRol());
				}
	        	userRepo.deleteById(user.getName());

	        	userRepo.save(user);
	        	return "resumeUser";
	        } else {
	        	throw new UserException(2, "NOT_EXISTS");
	        }
	        
		} else {
			Error error = new Error("Error al Modificar el Usuario","El codigo introducido coincide con un usuario existente","addUserForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/modifyFilm")
	public String modifyFilm(@ModelAttribute("pelicula") Pelicula pelicula,BindingResult result, ModelMap model) throws PeliculaException {
		if(pelicula != null) {
			model.addAttribute("code", pelicula.getCode());
	        model.addAttribute("name", pelicula.getName());
	        model.addAttribute("trailer", pelicula.getTrailer());
	        model.addAttribute("info", pelicula.getInfo());
	        model.addAttribute("year", pelicula.getYear());
	        model.addAttribute("director", pelicula.getDirector());
	        model.addAttribute("genre", pelicula.getGenre());
	        model.addAttribute("reparto", pelicula.getReparto());
	        model.addAttribute("portada", pelicula.getPortada());
	        model.addAttribute("rate", pelicula.getRate());
	        Pelicula existingMovie = restTemplate.getForObject(restServerUrl + "movie/find/" + pelicula.getCode(),Pelicula.class);
	        if(existingMovie == null) {
	        	throw new PeliculaException(2,"NOT_EXITS");
	        } else {
	        	restTemplate.exchange(restServerUrl + "movie/modify", HttpMethod.PUT, new HttpEntity<>(pelicula),Pelicula.class);
		        return "resumeFilm";
	        }
		} else {
			Error error = new Error("Error al Modificar la Pelicula","El codigo introducido coincide con una pelicula existente","addFilmForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}