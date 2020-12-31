package org.twitter.TwitterBot.model.services;

import java.util.List;

import org.twitter.TwitterBot.model.dao.DaoFactory;
import org.twitter.TwitterBot.model.dao.VocabularioDao;
import org.twitter.TwitterBot.model.entities.Vocabulario;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;

public class VocabularioServices {

	private VocabularioDao vocabularioDao = DaoFactory.createVocabularioDao();

	public List<Vocabulario> selectAll() throws ExcessaoBd {
		return vocabularioDao.selectAll();
	}

	public List<Vocabulario> selectPost() throws ExcessaoBd {
		return vocabularioDao.selectPost();
	}

	public void insertAll(List<Vocabulario> lista) throws ExcessaoBd {
		for (Vocabulario item: lista)
			vocabularioDao.insert(item);
	}
	
	public void save(Vocabulario obj) throws ExcessaoBd {
		if (obj.getId() == null || obj.getId().equals(Long.valueOf(0))) 
			insert(obj);
		else
			update(obj);
	}
	
	public void insert(Vocabulario obj) throws ExcessaoBd {
		vocabularioDao.insert(obj);
	}

	public void update(Vocabulario obj) throws ExcessaoBd {
		vocabularioDao.update(obj);
	}

	public void delete(Vocabulario obj) throws ExcessaoBd {
		vocabularioDao.delete(obj);
	}

	public Vocabulario select(Integer id) throws ExcessaoBd {
		return vocabularioDao.select(id);
	}

}
