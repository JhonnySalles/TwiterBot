package org.twitter.TwitterBot.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.twitter.TwitterBot.model.dao.VocabularioDao;
import org.twitter.TwitterBot.model.entities.Vocabulario;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;
import org.twitter.TwitterBot.model.message.Mensagens;
import org.twitter.TwitterBot.util.mysql.DB;

public class VocabularioDaoJDBC implements VocabularioDao {

	private Connection conn;

	final private String INSERT = "INSERT IGNORE INTO vocabulario (vocabulario, significado, frase, traducao, kanjis, nivel, jlpt, observacao, postado, ativo) VALUES (?,?,?,?,?,?,?,?,?,?);";
	final private String UPDATE = "UPDATE vocabulario SET vocabulario = ?, significado = ?, frase = ?, traducao = ?, kanjis = ?, nivel = ?, jlpt = ?, observacao = ?, postado = ?, ativo = ? WHERE id = ?;";
	final private String DELETE = "DELETE FROM vocabulario WHERE id = ?;";
	final private String SELECT = "SELECT id, vocabulario, significado, frase, traducao, kanjis, nivel, jlpt, observacao, postado, ativo FROM vocabulario WHERE id = ? ;";
	final private String SELECT_ALL = "SELECT id, vocabulario, significado, frase, traducao, kanjis, nivel, jlpt, observacao, postado, ativo FROM vocabulario";
	final private String SELECT_POST = "SELECT id, vocabulario, significado, frase, traducao, kanjis, nivel, jlpt, observacao, postado, ativo FROM vocabulario "
			+ " WHERE Postado = (SELECT MIN(postado) FROM vocabulario) LIMIT 100;";

	public VocabularioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Vocabulario obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getVocabulario());
			st.setString(2, obj.getSignificado());
			st.setString(3, obj.getFrase());
			st.setString(4, obj.getTraducao());
			st.setString(5, obj.getKanji());
			st.setInt(6, obj.getNivel());
			st.setInt(7, obj.getJlpt());
			st.setString(8, obj.getObservacao());
			st.setInt(9, obj.getPostado());
			st.setBoolean(10, obj.getAtivo());

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
	public void update(Vocabulario obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getVocabulario());
			st.setString(2, obj.getSignificado());
			st.setString(3, obj.getFrase());
			st.setString(4, obj.getTraducao());
			st.setString(5, obj.getKanji());
			st.setInt(6, obj.getNivel());
			st.setInt(7, obj.getJlpt());
			st.setString(8, obj.getObservacao());
			st.setInt(9, obj.getPostado());
			st.setBoolean(10, obj.getAtivo());
			st.setLong(11, obj.getId());

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
	public void delete(Vocabulario obj) throws ExcessaoBd {
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
	public Vocabulario select(Integer id) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(SELECT);
			st.setLong(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				return new Vocabulario(rs.getLong("id"), rs.getString("vocabulario"), rs.getString("significado"),
						rs.getString("frase"), rs.getString("traducao"), rs.getString("kanjis"), rs.getInt("nivel"),
						rs.getInt("jlpt"), rs.getString("observacao"), rs.getInt("postado"), rs.getBoolean("ativo"));
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
	public List<Vocabulario> selectAll() throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(SELECT_ALL);
			rs = st.executeQuery();

			List<Vocabulario> list = new ArrayList<>();

			while (rs.next()) {
				list.add(new Vocabulario(rs.getLong("id"), rs.getString("vocabulario"), rs.getString("significado"),
						rs.getString("frase"), rs.getString("traducao"), rs.getString("kanjis"), rs.getInt("nivel"),
						rs.getInt("jlpt"), rs.getString("observacao"), rs.getInt("postado"), rs.getBoolean("ativo")));
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

	public List<Vocabulario> selectPost() throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(SELECT_POST);
			rs = st.executeQuery();

			List<Vocabulario> list = new ArrayList<>();

			while (rs.next()) {
				list.add(new Vocabulario(rs.getLong("id"), rs.getString("vocabulario"), rs.getString("significado"),
						rs.getString("frase"), rs.getString("traducao"), rs.getString("kanjis"), rs.getInt("nivel"),
						rs.getInt("jlpt"), rs.getString("observacao"), rs.getInt("postado"), rs.getBoolean("ativo")));
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
