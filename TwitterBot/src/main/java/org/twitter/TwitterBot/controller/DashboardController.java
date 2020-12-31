package org.twitter.TwitterBot.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.twitter.TwitterBot.model.entities.Configuracao;
import org.twitter.TwitterBot.model.entities.Vocabulario;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;
import org.twitter.TwitterBot.model.services.ConfiguracaoServices;
import org.twitter.TwitterBot.model.services.VocabularioServices;
import org.twitter.TwitterBot.util.Processar;
import org.twitter.TwitterBot.util.mysql.ConexaoMysql;
import org.twitter.TwitterBot.util.mysql.ConexaoTwitter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class DashboardController implements Initializable {

	final static Image IMG_EXPORTA = new Image(DashboardController.class
			.getResourceAsStream("/org/twitter/TwitterBot/resources/images/export/icoBDBackup_Exportando_48.png"));

	final static Image IMG_IMPORTA = new Image(DashboardController.class
			.getResourceAsStream("/org/twitter/TwitterBot/resources/images/export/icoBDBackup_Importando_48.png"));

	@FXML
	private AnchorPane apGlobal;

	@FXML
	private JFXButton btnImportar;

	@FXML
	private JFXButton btnPostarAgora;

	@FXML
	private JFXButton btnExecutar;

	@FXML
	private JFXButton btnConfiguracao;

	@FXML
	private JFXTextField txtTempoRestante;

	@FXML
	private JFXTextArea txtAreaLog;

	@FXML
	private Label lblExclusoes;

	@FXML
	private ProgressBar barraProgresso;

	private List<Vocabulario> frasesVocabulario = new ArrayList<>();
	private VocabularioServices serviceVocabulario = new VocabularioServices();
	private ConfiguracaoServices serviceConfiguracao = new ConfiguracaoServices();
	private Configuracao configuracao;

	private PopOver pop;
	private static Thread POSTAGEM;
	private static Task<Void> TIMER;

	private static LocalDateTime PROXIMA_POSTAGEM = LocalDateTime.now();

	@FXML
	private void onBtnImportar() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione o arquivo para importar");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Files", "*.csv"),
				new ExtensionFilter("Text Files", "*.txt"));
		File arquivo = fileChooser.showOpenDialog(null);

		if (arquivo == null)
			setLog("Arquivo não encontrado");
		else {
			Processar importar = new Processar(this);
			importar.importaDados(arquivo);
		}
	}

	@FXML
	private void onBtnExecutar() {
		if (btnExecutar.getAccessibleText().equals("PARAR"))
			stopExecucao();
		else {
			setLog("Executando a postagem automática...................");
			POSTAGEM.start();
			btnExecutar.setText("Parar");
			btnExecutar.setAccessibleText("PARAR");
		}
	}

	private void stopExecucao() {
		if (btnExecutar.getAccessibleText().equals("PARAR"))
			setLog("Parando a postagem automática...................");

		POSTAGEM.interrupt();
		btnExecutar.setText("Iniciar");
		btnExecutar.setAccessibleText("INICIAR");
	}

	@FXML
	private void onBtnPostarAgora() {
		getDados();
	}

	@FXML
	private void onBtnConfiguracao() {
		if (!pop.isShowing())
			mostrarConfiguracao();
	}

	public void setLog(String menssagem) {
		System.out.println(menssagem);

		if (!txtAreaLog.getText().isEmpty())
			txtAreaLog.setText(txtAreaLog.getText() + "\n");

		txtAreaLog.setText(txtAreaLog.getText() + menssagem);
	}

	public void setTimer(String timer) {
		txtTempoRestante.setText(timer);
	}

	public void getDados() {
		try {
			setLog("Consultando frases.");
			frasesVocabulario = serviceVocabulario.selectPost();

			if (configuracao == null) {
				configuracao = serviceConfiguracao.select();

				if (configuracao.getUltimoPost().getDayOfMonth() != LocalDateTime.now().getDayOfMonth())
					configuracao.setPostDiario((long) 0);
			}

			if (frasesVocabulario != null && !frasesVocabulario.isEmpty())
				postarVocabulario();
			else
				setLog("Nenhuma menssagem encontrada.");

		} catch (ExcessaoBd e) {
			setLog("Erro ao consultar os posts. \n" + e.getMessage());
			e.printStackTrace();
			stopExecucao();
		}
	}

	private Twitter configuraTwitter() {
		ConexaoTwitter.getDadosConexao();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(ConexaoTwitter.getConsumerKey())
				.setOAuthConsumerSecret(ConexaoTwitter.getConsumerSecret())
				.setOAuthAccessToken(ConexaoTwitter.getAccessToken())
				.setOAuthAccessTokenSecret(ConexaoTwitter.getAccessTokenSecret());
		TwitterFactory tf = new TwitterFactory(cb.build());
		return tf.getInstance();
	}

	public void postarVocabulario() {
		Random rand = new Random();
		Vocabulario postar = frasesVocabulario.get(rand.nextInt(frasesVocabulario.size()));

		String frase = postar.getVocabulario() + " - " + postar.getSignificado() + "\n\n" + postar.getFrase()
				+ "\n\n#Vocabulary #JLPT #JLPTN" + postar.getJlpt();

		if (frase.length() > 250) {
			postar.setObservacao("Tamanho da frase incompatível, maior que 250 caracteres.");

			try {
				serviceVocabulario.update(postar);
			} catch (ExcessaoBd e) {
				setLog("Erro ao atualizar a menssagem no banco. \n" + e.getMessage());
				e.printStackTrace();
				stopExecucao();
				return;
			}

			frasesVocabulario.remove(postar);
			setLog("Tamanho da frase incompatível, maior que 250 caracteres:" + postar.toString());
			postarVocabulario();
			return;
		}

		Twitter twitter = configuraTwitter();

		try {
			User user = twitter.verifyCredentials();
			setLog("Conectado ao twitter no usuario: " + user.getName());
		} catch (TwitterException e1) {
			setLog("Erro na verificação das credenciais. \n" + e1.getMessage());
			e1.printStackTrace();
			stopExecucao();
			return;
		}

		try {
			setLog("Postando a frase: " + postar.toString());
			Status post = twitter.updateStatus(frase);
			configuracao.incrementa();

			if (!postar.getKanji().isEmpty()) {
				StatusUpdate comentaKanji = new StatusUpdate(postar.getKanji());
				comentaKanji.inReplyToStatusId(post.getId());
				twitter.updateStatus(comentaKanji);
				configuracao.incrementa();
			}

			StatusUpdate comentaTraducao = new StatusUpdate(postar.getTraducao());
			comentaTraducao.inReplyToStatusId(post.getId());
			twitter.updateStatus(comentaTraducao);
			configuracao.incrementa();
			configuracao.setUltimoPost(LocalDateTime.now());

			postar.setPostado(postar.getPostado() + 1);
			serviceVocabulario.update(postar);
			serviceConfiguracao.update(configuracao);
			setLog("Postagem concluida.");
		} catch (TwitterException e) {
			setLog("Erro ao postar a menssagem. \n" + e.getMessage());
			e.printStackTrace();
			stopExecucao();
		} catch (ExcessaoBd e) {
			setLog("Erro ao atualizar a menssagem no banco. \n" + e.getMessage());
			e.printStackTrace();
			stopExecucao();
		}
	}

	public void postarAnime() {
		Random rand = new Random();
		Vocabulario postar = frasesVocabulario.get(rand.nextInt(frasesVocabulario.size()));

		if (postar.getFrase().length() > 281) {
			postar.setObservacao("Tamanho da frase incompatível, maior que 281 caracteres.");

			try {
				serviceVocabulario.update(postar);
			} catch (ExcessaoBd e) {
				setLog("Erro ao atualizar a menssagem no banco. \n" + e.getMessage());
				e.printStackTrace();
				stopExecucao();
				return;
			}

			frasesVocabulario.remove(postar);
			setLog("Tamanho da frase incompatível, maior que 281 caracteres:" + postar.toString());
			postarVocabulario();
			return;
		}

		Twitter twitter = configuraTwitter();

		try {
			User user = twitter.verifyCredentials();
			setLog("Conectado ao twitter no usuario: " + user.getName());
		} catch (TwitterException e1) {
			setLog("Erro na verificação das credenciais. \n" + e1.getMessage());
			e1.printStackTrace();
			stopExecucao();
			return;
		}

		try {
			setLog("Postando a frase: " + postar.toString());

			File file = new File("/images/Done.jpg");

			StatusUpdate status = new StatusUpdate(postar.getFrase());
			status.setMedia(file);
			Status post = twitter.updateStatus(status);

			StatusUpdate comentaTraducao = new StatusUpdate(postar.getTraducao());
			comentaTraducao.inReplyToStatusId(post.getId());
			twitter.updateStatus(comentaTraducao);

			postar.setPostado(postar.getPostado() + 1);
			serviceVocabulario.update(postar);
			setLog("Postagem concluida.");
		} catch (TwitterException e) {
			setLog("Erro ao postar a menssagem. \n" + e.getMessage());
			e.printStackTrace();
			stopExecucao();
		} catch (ExcessaoBd e) {
			setLog("Erro ao atualizar a menssagem no banco. \n" + e.getMessage());
			e.printStackTrace();
			stopExecucao();
		}
	}

	public void verificaConexao() {
		// Criacao da thread para que esteja validando a conexao e nao trave a tela.
		Task<String> verificaConexao = new Task<String>() {

			@Override
			protected String call() throws Exception {
				TimeUnit.SECONDS.sleep(1);
				return ConexaoMysql.testaConexaoMySQL();
			}

			@Override
			protected void succeeded() {
				String conectado = getValue();

				if (!conectado.isEmpty())
					setLog("Conectado com sucesso.");
				else
					setLog("Erro ao conectar no banco informado, verifique as configurações.");
			}
		};

		Thread t = new Thread(verificaConexao);
		t.start();
		setLog("Verificando a conexão com o banco de dados....");
	}

	static Integer TEMPO = 1;
	private void configuraThread() {
		Task<Void> realizarPostagem = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {

				while (true) {
					Platform.runLater(() -> setLog("Iniciando a postagem automática."));

					Random rand = new Random();
					TEMPO = rand.nextInt(60);
					CONTADOR = 0;
							
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							getDados();
							setLog("Aguardando " + TEMPO + " minutos para próxima postagem.");
							PROXIMA_POSTAGEM = LocalDateTime.now().plusMinutes(TEMPO);
						}
					});
					
					TimeUnit.MINUTES.sleep(TEMPO);
				}
			}
		};

		POSTAGEM = new Thread(realizarPostagem);
	}

	static Integer CONTADOR = 0;
	private void configuraTimer() {
		TIMER = new Task<Void>() {
			@Override
			public Void call() throws IOException, InterruptedException {
				while (true) {

					if (btnExecutar.getAccessibleText().equals("INICIAR")) {
						updateMessage("Timer desabilitado.");
						updateProgress(0, 100);
					} else {
						LocalDateTime diferenca = LocalDateTime.now();
						long minutos = diferenca.until(PROXIMA_POSTAGEM, ChronoUnit.MINUTES);
						long segundos = diferenca.until(PROXIMA_POSTAGEM, ChronoUnit.SECONDS);

						if (minutos < 0 || segundos < 0) {
							minutos = 0;
							segundos = 0;
						}
						segundos = segundos - (minutos * 60);
						
						CONTADOR++;
						updateProgress(CONTADOR, TEMPO * 60);						
						updateMessage("Aguardando...: 00:" + String.format("%02d", minutos) + ":"
								+ String.format("%02d", segundos));
					}
					TimeUnit.SECONDS.sleep(1);
				}
			}
		};

		barraProgresso.progressProperty().bind(TIMER.progressProperty());
		txtTempoRestante.textProperty().bind(TIMER.messageProperty());
		Thread t = new Thread(TIMER);
		t.start();
	}

	public void cleanBind() {
		barraProgresso.progressProperty().bind(TIMER.progressProperty());
	}

	public DashboardController mostrarConfiguracao() {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = btnConfiguracao.getScene();
		Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
		Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
		Point2D nodeCoord = btnConfiguracao.localToScene(0.0, 0.0);
		double cordenadaX = Math.round(windowCoord.getX() + sceneCoord.getX() + nodeCoord.getX());

		if (cordenadaX < screenBounds.getWidth() / 2)
			pop.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
		else
			pop.arrowLocationProperty().set(ArrowLocation.RIGHT_TOP);

		pop.show(btnConfiguracao, 30);
		return this;
	}

	public ProgressBar getBarraProgresso() {
		return barraProgresso;
	}

	public PopOver getPopPup() {
		return pop;
	}

	public JFXTextArea getLogTextArea() {
		return txtAreaLog;
	}

	private void criaMenuBackup() {
		ContextMenu menuBackup = new ContextMenu();

		MenuItem miBackup = new MenuItem("Backup");
		ImageView imgExporta = new ImageView(IMG_EXPORTA);
		imgExporta.setFitHeight(20);
		imgExporta.setFitWidth(20);
		miBackup.setGraphic(imgExporta);
		miBackup.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// exportaBackup();
			}
		});

		MenuItem miRestaurar = new MenuItem("Restaurar");
		ImageView imgImporta = new ImageView(IMG_IMPORTA);
		imgImporta.setFitHeight(20);
		imgImporta.setFitWidth(20);
		miRestaurar.setGraphic(imgImporta);
		miRestaurar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Backup.importarBackup(this);
			}
		});

		menuBackup.getItems().addAll(miBackup, miRestaurar);

		btnConfiguracao.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.SECONDARY) {
					Scene scene = btnConfiguracao.getScene();
					Point2D windowCoord = new Point2D(scene.getWindow().getX(), scene.getWindow().getY());
					Point2D sceneCoord = new Point2D(scene.getX(), scene.getY());
					Point2D nodeCoord = btnConfiguracao.localToScene(0.0, 0.0);
					double cordenadaX = Math.round(windowCoord.getX() + sceneCoord.getX() + nodeCoord.getX());
					double cordenadaY = Math.round(windowCoord.getY() + sceneCoord.getY() + nodeCoord.getY());
					menuBackup.show(btnConfiguracao, cordenadaX + 95, cordenadaY + 30);
				}
			}
		});

	}

	private DashboardController criaConfiguracao() {
		pop = new PopOver();
		URL url = ConfiguracaoController.getFxmlLocate();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(3));// 10px padding todos os lados
		try {
			vbox.getChildren().add(loader.load());

			ConfiguracaoController cntConfiguracao = loader.getController();
			cntConfiguracao.setControllerPai(this);
			pop.setTitle("Configuração banco de dados");
			pop.setContentNode(vbox);
			pop.setCornerRadius(5);
			pop.setHideOnEscape(true);
			pop.setAutoFix(true);
			pop.setAutoHide(true);
			pop.setOnHidden(e -> cntConfiguracao.salvar());
			pop.setOnShowing(e -> cntConfiguracao.carregar());
			pop.getRoot().getStylesheets().add(DashboardController.class
					.getResource("/org/twitter/TwitterBot/resources/css/Dark_PopOver.css").toExternalForm());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		criaConfiguracao();
		criaMenuBackup();
		configuraThread();
		configuraTimer();
	}

	public static URL getFxmlLocate() {
		return DashboardController.class.getResource("/org/twitter/TwitterBot/view/DashBoard.fxml");
	}

	public static String getIconLocate() {
		return "/org/twitter/TwitterBot/resources/images/icoTwitterBot_128.png";
	}

}
