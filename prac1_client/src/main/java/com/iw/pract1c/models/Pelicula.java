package com.iw.pract1c.models;

public class Pelicula {
	private long code;
	private String name;
	private String trailer;
	private String info;
	private Integer year;
	private String director;
	private String reparto;
	private String portada;
	private Float rate;
	private String genre;
	
	public Pelicula() {}

	public Pelicula(long code, String name, String trailer, String info, Integer year, String director, String reparto,
			String portada, Float rate, String genre) {
		super();
		this.code = code;
		this.name = name;
		this.trailer = trailer;
		this.info = info;
		this.year = year;
		this.director = director;
		this.reparto = reparto;
		this.portada = portada;
		this.rate = rate;
		this.genre = genre;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getReparto() {
		return reparto;
	}

	public void setReparto(String reparto) {
		this.reparto = reparto;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}