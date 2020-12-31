package org.twitter.TwitterBot.model.entities;

public class Musica {

	private Long id;
	private String descricao;
	private String frase;
	private String traducao;
	private String musica;
	private String cantor;
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

	public String getTraducao() {
		return traducao;
	}

	public void setTraducao(String traducao) {
		this.traducao = traducao;
	}

	public String getMusica() {
		return musica;
	}

	public void setMusica(String musica) {
		this.musica = musica;
	}

	public String getCantor() {
		return cantor;
	}

	public void setCantor(String cantor) {
		this.cantor = cantor;
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
		return "Musica [id=" + id + ", descricao=" + descricao + ", frase=" + frase + ", traducao=" + traducao
				+ ", musica=" + musica + ", cantor=" + cantor + ", imagem=" + imagem + ", tag=" + tag + ", postado="
				+ postado + ", ativo=" + ativo + "]";
	}

	public Musica() {
		this.id = (long) 0;
		this.descricao = "";
		this.frase = "";
		this.traducao = "";
		this.musica = "";
		this.cantor = "";
		this.imagem = "";
		this.tag = "";
		this.postado = 0;
		this.ativo = true;
	}

	public Musica(Long id, String descricao, String frase, String traducao, String musica, String cantor, String imagem,
			String tag, Integer postado, Boolean ativo) {
		this.id = id;
		this.descricao = descricao;
		this.frase = frase;
		this.traducao = traducao;
		this.musica = musica;
		this.cantor = cantor;
		this.imagem = imagem;
		this.tag = tag;
		this.postado = postado;
		this.ativo = ativo;
	}

}
