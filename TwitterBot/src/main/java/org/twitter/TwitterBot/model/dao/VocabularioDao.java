package org.twitter.TwitterBot.model.dao;

import java.util.List;

import org.twitter.TwitterBot.model.entities.Vocabulario;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;

public interface VocabularioDao {

	void insert(Vocabulario obj) throws ExcessaoBd;

	void update(Vocabulario obj) throws ExcessaoBd;

	void delete(Vocabulario obj) throws ExcessaoBd;
	
	Vocabulario select(Integer id) throws ExcessaoBd;

	List<Vocabulario> selectAll() throws ExcessaoBd;
	
	List<Vocabulario> selectPost() throws ExcessaoBd;

}
