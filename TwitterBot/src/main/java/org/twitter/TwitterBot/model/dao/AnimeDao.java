package org.twitter.TwitterBot.model.dao;

import java.util.List;

import org.twitter.TwitterBot.model.entities.Anime;
import org.twitter.TwitterBot.model.entities.Vocabulario;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;

public interface AnimeDao {

	void insert(Anime obj) throws ExcessaoBd;

	void update(Anime obj) throws ExcessaoBd;

	void delete(Anime obj) throws ExcessaoBd;

	Anime select(Integer id) throws ExcessaoBd;

	List<Anime> selectAll() throws ExcessaoBd;

	List<Anime> selectPost() throws ExcessaoBd;

}
