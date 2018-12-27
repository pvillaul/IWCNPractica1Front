package com.iw.pract1c.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pelicula {
	@Id
	private long code;
	private String name;
	private String trailer;
	private String info;
	private int year;
	private String director;
	private String reparto;
	private String portada;
	private double rate;
	
	public Pelicula() {}
	
	public Pelicula(long code,String name, String trailer, String info, int year, String director, String reparto, String portada, double rate) {
		this.code = code;
		this.name = name;
		this.trailer = trailer;
		this.info = info;
		this.year = year;
		this.director = director;
		this.reparto = reparto;
		this.portada = portada;
		this.rate = rate;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}