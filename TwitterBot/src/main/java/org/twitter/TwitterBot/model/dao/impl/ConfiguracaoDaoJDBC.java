package org.twitter.TwitterBot.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.twitter.TwitterBot.model.dao.ConfiguracaoDao;
import org.twitter.TwitterBot.model.entities.Configuracao;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;
import org.twitter.TwitterBot.model.message.Mensagens;
import org.twitter.TwitterBot.util.mysql.DB;

public class ConfiguracaoDaoJDBC implements ConfiguracaoDao {

	private Connection conn;

	final private String INSERT = "INSERT INTO configuracao (tipo, PostDiario, PostTotal, UltimoPost ) VALUES (?,?,?,?);";
	final private String UPDATE = "UPDATE configuracao SET PostDiario = ?, PostTotal = ?, UltimoPost = NOW() WHERE id = ?;";
	final private String DELETE = "DELETE FROM configuracao WHERE id = ?;";
	final private String SELECT = "SELECT id, tipo, PostDiario, PostTotal, UltimoPost FROM configuracao WHERE id = 1;";

	public ConfiguracaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Configuracao obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

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
	public void update(Configuracao obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getPostDiario());
			st.setLong(2, obj.getPostTotal());
			st.setLong(3, obj.getId());

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
	public void delete(Configuracao obj) throws ExcessaoBd {
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
	public Configuracao select() throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(SELECT);
			rs = st.executeQuery();

			if (rs.next()) {
				return new Configuracao(rs.getLong("id"), rs.getString("tipo"), rs.getLong("PostDiario"),
						rs.getLong("PostTotal"), rs.getTimestamp("UltimoPost").toLocalDateTime());
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

}
