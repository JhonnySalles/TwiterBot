package org.twitter.TwitterBot.model.entities;

public class Anime {

	private Long id;
	private String descricao;
	private String frase;
	private String ingles;
	private String traducao;
	private String anime;
	private String imagem;
	private String tag;
	private Integer postado;
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}

	public String getIngles() {
		return ingles;
	}

	public void setIngles(String ingles) {
		this.ingles = ingles;
	}

	public String getTraducao() {
		return traducao;
	}

	public void setTraducao(String traducao) {
		this.traducao = traducao;
	}

	public String getAnime() {
		return anime;
	}

	public void setAnime(String anime) {
		this.anime = anime;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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
		return "Anime [id=" + id + ", descricao=" + descricao + ", frase=" + frase + ", ingles=" + ingles
				+ ", traducao=" + traducao + ", anime=" + anime + ", imagem=" + imagem + ", tag=" + tag + ", postado="
				+ postado + ", ativo=" + ativo + "]";
	}

	public Anime() {
		this.id = (long) 0;
		this.descricao = "";
		this.frase = "";
		this.ingles = "";
		this.traducao = "";
		this.anime = "";
		this.imagem = "";
		this.tag = "";
		this.postado = 0;
		this.ativo = true;
	}

	public Anime(Long id, String descricao, String frase, String ingles, String traducao, String anime, String imagem,
			String tag, Integer postado, Boolean ativo) {
		this.id = id;
		this.descricao = descricao;
		this.frase = frase;
		this.ingles = ingles;
		this.traducao = traducao;
		this.anime = anime;
		this.imagem = imagem;
		this.tag = tag;
		this.postado = postado;
		this.ativo = ativo;
	}

}
