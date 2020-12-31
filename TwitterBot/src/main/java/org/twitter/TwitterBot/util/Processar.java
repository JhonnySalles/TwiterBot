package org.twitter.TwitterBot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.twitter.TwitterBot.controller.DashboardController;
import org.twitter.TwitterBot.model.entities.Vocabulario;
import org.twitter.TwitterBot.model.exceptions.ExcessaoBd;
import org.twitter.TwitterBot.model.services.VocabularioServices;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class Processar {

	private DashboardController dashboard;
	private VocabularioServices serviceVocabulario = new VocabularioServices();
	private List<Vocabulario> lista = new ArrayList<>();

	final private String pattern = ".*[\u4E00-\u9FAF].*";
	final private String split = "\\|\\|";

	public void importaDados(File arquivo) {

		Task<Boolean> importacao = new Task<Boolean>() {

			@Override
			protected Boolean call() {
				Platform.runLater(() -> dashboard.setLog("Iniciando a importação......"));

				BufferedReader buffRead;
				try {
					buffRead = new BufferedReader(new FileReader(arquivo.getAbsolutePath()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Platform.runLater(() -> dashboard.setLog("Arquivo não encontrado."));
					return false;
				}

				int qtdLinha = 1;
				int numeroLinha = 0;
				try {
					LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(arquivo));
					lineNumberReader.skip(Long.MAX_VALUE);
					qtdLinha = lineNumberReader.getLineNumber();
					lineNumberReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					Platform.runLater(() -> dashboard.setLog("Falha na leitura do arquivo."));
					return false;
				}

				String linha = "";
				String colunas[];

				lista.clear();

				try {
					while (buffRead.ready()) {
						linha = buffRead.readLine();

						numeroLinha++;
						updateProgress(numeroLinha, qtdLinha);

						if (!linha.isEmpty()) {

							while (linha.contains("<br />"))
								linha = linha.replace("<br />", "<br>");

							while (linha.contains("<br/>"))
								linha = linha.replace("<br/>", "<br>");

							colunas = linha.split(split);

							Integer nivel = 0;
							try {
								nivel = Integer.valueOf(colunas[2].replaceAll("[^0-9]", "").trim());
							} catch (NumberFormatException e) {
								System.out.println(
										"Nivel: " + colunas[2] + " - " + colunas[2].replaceAll("[^0-9]", "").trim());
								e.printStackTrace();
								Platform.runLater(() -> dashboard.setLog("Erro ao converter o nível do vocabulário."));
								buffRead.close();
								return false;
							}

							String frase = colunas[3].replace(split, "");
							String traducao = colunas[4].replace(split, "");

							frase = frase.replaceAll("<div>", "").replaceAll("</div>", "");
							traducao = traducao.replaceAll("<div>", "").replaceAll("</div>", "");

							while (frase.contains("<br><br>"))
								frase = frase.replace("<br><br>", "<br>");

							while (traducao.contains("<br><br>"))
								traducao = traducao.replace("<br><br>", "<br>");

							String[] splitFrase = frase.split("<br>");
							String[] splitTraducao = traducao.split("<br>");
							Integer deslocamento = 0;

							for (int x = 1; x < splitFrase.length; x++) {
								Vocabulario vocabulario = new Vocabulario();
								vocabulario.setVocabulario(colunas[0].replace(split, "").trim());
								vocabulario.setSignificado(splitTraducao[0].trim());
								vocabulario.setNivel(nivel);
								vocabulario.setJlpt(nivel);

								vocabulario.setFrase(splitFrase[x].trim());
								
								if ((x + deslocamento) >= splitTraducao.length || splitTraducao[x + deslocamento].isEmpty() ) {
									vocabulario.setObservacao("Erro no tamanho do split e a tradução não foi localizada.");
									lista.add(vocabulario);
									continue;
								}
								
								if (splitTraducao[x + deslocamento].matches(pattern)) {
									String message = "Verificar";
									vocabulario.setKanji(splitTraducao[x + deslocamento].trim());
									++deslocamento;

									if ((x + deslocamento) <= splitTraducao.length)
										vocabulario.setTraducao(splitTraducao[x + deslocamento].trim());
									else
										message += " - erro no tamanho do split e a tradução não foi localizada.";

									vocabulario.setObservacao(message);
								} else
									vocabulario.setTraducao(splitTraducao[x + deslocamento].trim());
								

								System.out.println("Vocab: " + vocabulario.getVocabulario() + "  - Frase: "
										+ vocabulario.getFrase() + "  - Kanji: " + vocabulario.getKanji()
										+ "  - Traducao: " + vocabulario.getTraducao());

								lista.add(vocabulario);
							}
						}
					}
					buffRead.close();
				} catch (IOException e) {
					e.printStackTrace();
					Platform.runLater(() -> dashboard.setLog("Erro ao importar os arquivos."));
					return false;
				}
				
				if (!lista.isEmpty()) {
					try {
						serviceVocabulario.insertAll(lista);
					} catch (ExcessaoBd e) {
						e.printStackTrace();
						Platform.runLater(() -> dashboard.setLog("Erro ao salvar informações."));
						return false;
					}
				}

				return true;
			}

			@Override
			protected void succeeded() {
				if (getValue()) {
					Platform.runLater(() -> dashboard.setLog("Importação concluída."));
					// TaskbarProgressbar.stopProgress(Run.getPrimaryStage());
				}

				dashboard.getBarraProgresso().progressProperty().unbind();
				dashboard.cleanBind();
			}

			@Override
			protected void failed() {
				super.failed();
				// TaskbarProgressbar.showFullErrorProgress(Run.getPrimaryStage());
				dashboard.getBarraProgresso().progressProperty().unbind();
				dashboard.cleanBind();
				Platform.runLater(() -> dashboard.setLog("Erro ao importar os arquivos."));
			}
		};
		dashboard.getBarraProgresso().progressProperty().bind(importacao.progressProperty());

		Thread t = new Thread(importacao);
		t.setDaemon(true);
		t.start();

	}

	public Processar(DashboardController ctn) {
		dashboard = ctn;
	}
}
