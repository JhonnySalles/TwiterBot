package org.twitter.TwitterBot.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.twitter.TwitterBot.model.dao.MusicaDao;
import org.twitter.TwitterBot.model.dao.VocabularioDao;
import org.twitter.TwitterBot.model.entities.Musica;
import org.twitter.TwitterBot.model.entities.Vocabulario;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;
import org.twitter.TwitterBot.model.message.Mensagens;
import org.twitter.TwitterBot.util.mysql.DB;

public class MusicaDaoJDBC implements MusicaDao {

	private Connection conn;

	final private String INSERT = "INSERT IGNORE INTO vocabulario (Descricao, Frase, Traducao, Kanjis, Nivel, JLPT, Postado, Ativo) VALUES (?,?,?,?,?,?,?,?);";
	final private String UPDATE = "UPDATE vocabulario SET Descricao = ?, Frase = ?, Traducao = ?, Kanjis = ?, Nivel = ?, JLPT = ?, Postado = ?, Ativo = ? WHERE id = ?;";
	final private String DELETE = "DELETE FROM vocabulario WHERE id = ?;";
	final private String SELECT = "SELECT id, Descricao, Frase, Traducao, Kanjis, Nivel, JLPT, Postado, Ativo FROM vocabulario WHERE id = ? ;";
	final private String SELECT_ALL = "SELECT id, descricao, frase, traducao, kanjis, nivel, jlpt, postado, ativo FROM vocabulario";
	final private String SELECT_POST = "SELECT id, descricao, frase, traducao, kanjis, nivel, jlpt, postado, ativo FROM vocabulario "
			+ " WHERE Postado = (SELECT MIN(postado) FROM vocabulario) LIMIT 100;";

	public MusicaDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Musica obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getFrase());
			st.setString(3, obj.getTraducao());

			st.setInt(7, obj.getPostado());
			st.setBoolean(8, obj.getAtivo());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println(st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
			}
		} catch (SQLException e) {
			System.out.println(st.toString());
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Musica obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getFrase());
			st.setString(3, obj.getTraducao());
	
			st.setInt(7, obj.getPostado());
			st.setBoolean(8, obj.getAtivo());
			st.setLong(9, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println(st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_UPDATE);
			}
			;
		} catch (SQLException e) {
			System.out.println(st.toString());
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_UPDATE);
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Musica obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(DELETE);

			st.setLong(1, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println(st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
			}
		} catch (SQLException e) {
			System.out.println(st.toString());
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Musica select(Integer id) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(SELECT);
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT);
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Musica> selectAll() throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(SELECT_ALL);
			rs = st.executeQuery();

			List<Musica> list = new ArrayList<>();

			while (rs.next()) {
				
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT);
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public List<Musica> selectPost() throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(SELECT_POST);
			rs = st.executeQuery();

			List<Musica> list = new ArrayList<>();

			while (rs.next()) {
	
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT);
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
