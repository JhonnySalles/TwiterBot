package org.twitter.TwitterBot.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.twitter.TwitterBot.util.mysql.ConexaoMysql;
import org.twitter.TwitterBot.util.mysql.ConexaoTwitter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;

public class ConfiguracaoController implements Initializable {

	@FXML
	public JFXTextField txtUsuario;

	@FXML
	public JFXPasswordField pswSenha;

	@FXML
	public JFXTextField txtServer;

	@FXML
	public JFXTextField txtPorta;

	@FXML
	public JFXTextField txtDataBase;

	@FXML
	public JFXTextField txtCaminhoMysql;

	@FXML
	public JFXButton btnCaminho;

	@FXML
	public JFXTextField txtConsumerKey;

	@FXML
	public JFXTextField txtConsumerSecret;

	@FXML
	public JFXTextField txtAcessToken;

	@FXML
	public JFXTextField txtAcessTokenSecret;

	private DashboardController dashboard;

	@FXML
	private void onBtnCarregarCaminhoMysql() {
		dashboard.getPopPup().setDetached(true);
		String caminho = selecionaPasta(txtCaminhoMysql.getText());
		txtCaminhoMysql.setText(caminho);
	}

	public void salvar() {
		ConexaoMysql.setDadosConexao(txtServer.getText(), txtPorta.getText(), txtDataBase.getText(),
				txtUsuario.getText(), pswSenha.getText(), txtCaminhoMysql.getText());
		ConexaoTwitter.setDadosConexao(txtConsumerKey.getText(), txtConsumerSecret.getText(), txtAcessToken.getText(),
				txtAcessTokenSecret.getText());
		dashboard.verificaConexao();
	}

	public void carregar() {
		ConexaoMysql.getDadosConexao();
		txtServer.setText(ConexaoMysql.getServer());
		txtPorta.setText(ConexaoMysql.getPort());
		txtDataBase.setText(ConexaoMysql.getDataBase());
		txtUsuario.setText(ConexaoMysql.getUser());
		pswSenha.setText(ConexaoMysql.getPassword());
		txtCaminhoMysql.setText(ConexaoMysql.getCaminhoMysql());

		ConexaoTwitter.getDadosConexao();
		txtConsumerKey.setText(ConexaoTwitter.getConsumerKey());
		txtConsumerSecret.setText(ConexaoTwitter.getConsumerSecret());
		txtAcessToken.setText(ConexaoTwitter.getAccessToken());
		txtAcessTokenSecret.setText(ConexaoTwitter.getAccessTokenSecret());

	}

	private String selecionaPasta(String pasta) {
		DirectoryChooser fileChooser = new DirectoryChooser();
		fileChooser.setTitle("Selecione a pasta do mysql");

		if (pasta != null && !pasta.isEmpty())
			fileChooser.setInitialDirectory(new File(pasta));
		File caminho = fileChooser.showDialog(null);

		if (caminho == null)
			return "";
		else
			return caminho.getAbsolutePath();
	}

	public void setControllerPai(DashboardController cnt) {
		this.dashboard = cnt;
	}

	public static URL getFxmlLocate() {
		return ConfiguracaoController.class.getResource("/org/twitter/TwitterBot/view/Configuracao.fxml");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
