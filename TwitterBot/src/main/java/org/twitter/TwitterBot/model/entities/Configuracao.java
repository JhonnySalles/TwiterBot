package org.twitter.TwitterBot.model.entities;

import java.time.LocalDateTime;

public class Configuracao {

	private Long id;
	private String tipo;
	private Long postDiario;
	private Long postTotal;
	private LocalDateTime ultimoPost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getPostDiario() {
		return postDiario;
	}

	public void setPostDiario(Long postDiario) {
		this.postDiario = postDiario;
	}

	public Long getPostTotal() {
		return postTotal;
	}

	public void setPostTotal(Long postTotal) {
		this.postTotal = postTotal;
	}

	public LocalDateTime getUltimoPost() {
		return ultimoPost;
	}

	public void setUltimoPost(LocalDateTime ultimoPost) {
		this.ultimoPost = ultimoPost;
	}

	public void incrementa() {
		postDiario++;
		postTotal++;
	}

	@Override
	public String toString() {
		return "Configuracao [id=" + id + ", tipo=" + tipo + ", postDiario=" + postDiario + ", postTotal=" + postTotal
				+ ", ultimoPost=" + ultimoPost + "]";
	}

	public Configuracao() {
		this.id = (long) 0;
		this.tipo = "";
		this.postDiario = (long) 0;
		this.postTotal = (long) 0;
		this.ultimoPost = LocalDateTime.now();
	}

	public Configuracao(Long id, String tipo, Long postDiario, Long postTotal, LocalDateTime ultimoPost) {
		this.id = id;
		this.tipo = tipo;
		this.postDiario = postDiario;
		this.postTotal = postTotal;
		this.ultimoPost = ultimoPost;
	}

}
