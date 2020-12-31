package org.twitter.TwitterBot.model.dao;

import org.twitter.TwitterBot.model.entities.Configuracao;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;

public interface ConfiguracaoDao {

	void insert(Configuracao obj) throws ExcessaoBd;

	void update(Configuracao obj) throws ExcessaoBd;

	void delete(Configuracao obj) throws ExcessaoBd;

	Configuracao select() throws ExcessaoBd;

}
