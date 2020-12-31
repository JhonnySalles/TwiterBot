package org.twitter.TwitterBot.model.entities;

public class Vocabulario {

	private Long id;
	private String vocabulario;
	private String frase;
	private String traducao;
	private String kanji;
	private Integer nivel;
	private Integer jlpt;
	private String observacao;
	private Integer postado;
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVocabulario() {
		return vocabulario;
	}

	public void setVocabulario(String vocabulario) {
		this.vocabulario = vocabulario;
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}

	public String getTraducao() {
		return traducao;
	}

	public void setTraducao(String traducao) {
		this.traducao = traducao;
	}

	public String getKanji() {
		return kanji;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getJlpt() {
		return jlpt;
	}

	public void setJlpt(Integer jlpt) {
		this.jlpt = jlpt;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getPostado() {
		return postado;
	}

	public void setPostado(Integer postado) {
		this.postado = postado;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "Vocabulario [id=" + id + ", vocabulario=" + vocabulario + ", frase=" + frase + ", traducao=" + traducao
				+ ", kanji=" + kanji + ", nivel=" + nivel + ", jlpt=" + jlpt + ", observacao=" + observacao
				+ ", postado=" + postado + ", ativo=" + ativo + "]";
	}

	public Vocabulario() {
		this.id = (long) 0;
		this.vocabulario = "";
		this.frase = "";
		this.traducao = "";
		this.kanji = "";
		this.nivel = 0;
		this.jlpt = 0;
		this.observacao = "";
		this.postado = 0;
		this.ativo = true;
	}

	public Vocabulario(Long id, String vocabulario, String frase, String traducao, String kanji, Integer nivel,
			Integer jlpt, String observacao, Integer postado, Boolean ativo) {
		this.id = id;
		this.vocabulario = vocabulario;
		this.frase = frase;
		this.traducao = traducao;
		this.kanji = kanji;
		this.nivel = nivel;
		this.jlpt = jlpt;
		this.observacao = observacao;
		this.postado = postado;
		this.ativo = ativo;
	}

}
