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
		@SuppressWarnings("unchecked")
		List<Pelicula> peliculas = restTemplate.getForObject(restServerUrl + "movie/find?name=" + name + "&year=" + year + "&genre=" + genre + "&director=" + director + "&cast=" + cast + "&score=" + score,List.class);
	    model.addAttribute("peliculas", peliculas);
	    if(peliculas != null) {
	    	return "index";
	    } else {
	    	Error error = new Error("Error!!","No se ha podido realizar la busqueda","","Back Home");
			model.addAttribute("error",error);
			return "aviso";
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
	
	@SuppressWarnings("unchecked")
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/listFilms")
	public String listFilms(Model model) {
		List<Pelicula> films = restTemplate.getForObject(restServerUrl + "movie/list",List.class);
		model.addAttribute("films", films);
		if(films != null) {
        	return "listFilms";
        } else {
        	Error error = new Error("Error!!","No se han cargado las peliculas","","Back Home");
			model.addAttribute("error",error);
			return "aviso";
        }
	}
	
	@SuppressWarnings("unchecked")
	@Secured({"ROLE_VIEWER","ROLE_ADMIN"})
	@GetMapping("/catalogo")
	public String catalogo(Model model) {
		List<Pelicula> films = restTemplate.getForObject(restServerUrl + "movie/list",List.class);
		model.addAttribute("films", films);
		if(films != null) {
        	return "catalogo";
        } else {
        	Error error = new Error("Error!!","No se ha cargado el catalogo","","Back Home");
			model.addAttribute("error",error);
			return "aviso";
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
			User user = userRepo.findByName(id);
			if(user.getName() == "root") {
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
	public String newProd(@ModelAttribute("user") User user,BindingResult result, ModelMap model) throws UserException {
		if(user != null) {
			model.addAttribute("name", user.getName());
	        model.addAttribute("password", user.getPassword());
	        model.addAttribute("rol", user.getRol());
	        if(!userRepo.existsById(user.getName())) {
	        	user.setPassword(passwordEncoder().encode(user.getPassword()));
	        	userRepo.save(user);
	        	return "redirect:/listUsers";
	        } else {
	        	throw new UserException(1,"EXITS");
	        }
		} else {
			Error error = new Error("Error al Crear el Usuario","El codigo introducido coincide con un usuario existente","addUserForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/addFilm")
	public String newProd(@ModelAttribute("film") Pelicula film,BindingResult result, ModelMap model) throws PeliculaException {
		if(film != null) {
			model.addAttribute("code", film.getName());
	        model.addAttribute("name", film.getName());
	        model.addAttribute("trailer", film.getTrailer());
	        model.addAttribute("info", film.getInfo());
	        model.addAttribute("year", film.getYear());
	        model.addAttribute("director", film.getDirector());
	        model.addAttribute("genre", film.getGenre());
	        model.addAttribute("reparto", film.getReparto());
	        model.addAttribute("portada", film.getPortada());
	        model.addAttribute("rate", film.getRate());
	        Pelicula pelicula = restTemplate.getForObject(restServerUrl + "movie/find/" + film.getCode(),Pelicula.class);
	        if(pelicula != null) {
	        	throw new PeliculaException(1,"EXITS");
	        } else {
	        	restTemplate.exchange(restServerUrl + "movie/add", HttpMethod.POST, new HttpEntity<>(film),Pelicula.class);
		        return "redirect:/listFilms";
	        }
		} else {
			Error error = new Error("Error al Crear la Pelicula","El codigo introducido coincide con una pelicula existente","addFilmForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modifyUserForm")
	public String modifyUser(@RequestParam String id, Model model, @ModelAttribute User user) {
		User olduser = userRepo.findByName(id);
		model.addAttribute("olduser",olduser);
		return "modifyUserForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modifyFilmForm")
	public String modifyFilm(@RequestParam String id, Model model, @ModelAttribute Pelicula film) {
		Pelicula oldpeli = restTemplate.getForObject(restServerUrl + "movie/find/" + id,Pelicula.class);
		model.addAttribute("oldpeli",oldpeli);
		return "modifyFilmForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/modifyUser")
	public String modifyUser(@ModelAttribute("user") User user,BindingResult result, ModelMap model) throws UserException {
		if(user != null) {
			model.addAttribute("name", user.getName());
	        model.addAttribute("password", user.getPassword());
	        model.addAttribute("rol", user.getRol());
	        if(userRepo.existsById(user.getName())) {
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
	public String modifyFilm(@ModelAttribute("film") Pelicula film,BindingResult result, ModelMap model) throws PeliculaException {
		if(film != null) {
			model.addAttribute("code", film.getName());
	        model.addAttribute("name", film.getName());
	        model.addAttribute("trailer", film.getTrailer());
	        model.addAttribute("info", film.getInfo());
	        model.addAttribute("year", film.getYear());
	        model.addAttribute("director", film.getDirector());
	        model.addAttribute("genre", film.getGenre());
	        model.addAttribute("reparto", film.getReparto());
	        model.addAttribute("portada", film.getPortada());
	        model.addAttribute("rate", film.getRate());
	        Pelicula pelicula = restTemplate.getForObject(restServerUrl + "movie/find/" + film.getCode(),Pelicula.class);
	        if(pelicula == null) {
	        	throw new PeliculaException(2,"NOT_EXITS");
	        } else {
	        	restTemplate.exchange(restServerUrl + "movie/modify", HttpMethod.PUT, new HttpEntity<>(film),Pelicula.class);
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