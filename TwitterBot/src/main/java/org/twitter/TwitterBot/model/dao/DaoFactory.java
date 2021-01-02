package org.twitter.TwitterBot.model.dao;

import org.twitter.TwitterBot.model.dao.impl.ConfiguracaoDaoJDBC;
import org.twitter.TwitterBot.model.dao.impl.VocabularioDaoJDBC;
import org.twitter.TwitterBot.util.mysql.DB;

public class DaoFactory {

	public static VocabularioDao createVocabularioDao() {
		return new VocabularioDaoJDBC(DB.getConnection());
	}

	public static ConfiguracaoDao createConfiguracaoDao() {
		return new ConfiguracaoDaoJDBC(DB.getConnection());
	}

}
