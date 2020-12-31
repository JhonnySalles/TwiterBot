package org.twitter.TwitterBot.model.services;

import org.twitter.TwitterBot.model.dao.ConfiguracaoDao;
import org.twitter.TwitterBot.model.dao.DaoFactory;
import org.twitter.TwitterBot.model.entities.Configuracao;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;

public class ConfiguracaoServices {

	private ConfiguracaoDao configuracaoDao = DaoFactory.createConfiguracaoDao();

	public void update(Configuracao obj) throws ExcessaoBd {
		configuracaoDao.update(obj);
	}

	public Configuracao select() throws ExcessaoBd {
		return configuracaoDao.select();
	}

}
