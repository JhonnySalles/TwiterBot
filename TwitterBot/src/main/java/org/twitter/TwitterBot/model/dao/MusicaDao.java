package org.twitter.TwitterBot.model.dao;

import java.util.List;

import org.twitter.TwitterBot.model.entities.Musica;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;

public interface MusicaDao {

	void insert(Musica obj) throws ExcessaoBd;

	void update(Musica obj) throws ExcessaoBd;

	void delete(Musica obj) throws ExcessaoBd;
	
	Musica select(Integer id) throws ExcessaoBd;

	List<Musica> selectAll() throws ExcessaoBd;
	
	List<Musica> selectPost() throws ExcessaoBd;

}
