package org.twitter.TwitterBot.util.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.twitter.TwitterBot.Run;


public class ConexaoTwitter {

	private static String CONSUMER_KEY = "";
	private static String CONSUMER_SECRRET = "";
	private static String ACCESS_TOKEN = "";
	private static String ACCESS_TOKEN_SECRET = "";

	private static Properties loadProperties() {
		File f = new File("twitter4j.properties");
		if (!f.exists())
			createProperties();
		
		try (FileInputStream fs = new FileInputStream("twitter4j.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			Run.getMainController().setLog("Erro ao carregar o properties");
			e.printStackTrace();
		}
		return null;
	}
	
	private static void createProperties() {
		try (OutputStream os = new FileOutputStream("twitter4j.properties")) {
			Properties props = new Properties();
			
			props.setProperty("oauth.consumerKey", CONSUMER_KEY);
			props.setProperty("oauth.consumerSecret", CONSUMER_SECRRET);
			props.setProperty("oauth.accessToken", ACCESS_TOKEN);
			props.setProperty("oauth.accessTokenSecret", ACCESS_TOKEN_SECRET);
			props.store(os, "");
		} catch (IOException e) {
			Run.getMainController().setLog("Erro ao salvar o properties.");
			e.printStackTrace();
		}
	}
	
	public static void setDadosConexao(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		CONSUMER_KEY = consumerKey;
		CONSUMER_SECRRET = consumerSecret;
		ACCESS_TOKEN = accessToken;
		ACCESS_TOKEN_SECRET = accessTokenSecret;
		createProperties();
	}

	public static String getConsumerKey() {
		return CONSUMER_KEY;
	}

	public static String getConsumerSecret() {
		return CONSUMER_SECRRET;
	}

	public static String getAccessToken() {
		return ACCESS_TOKEN;
	}
	public static String getAccessTokenSecret() {
		return ACCESS_TOKEN_SECRET;
	}

	public static void getDadosConexao() {
		Properties props = loadProperties();
		CONSUMER_KEY = props.getProperty("oauth.consumerKey");
		CONSUMER_SECRRET = props.getProperty("oauth.consumerSecret");
		ACCESS_TOKEN = props.getProperty("oauth.accessToken");
		ACCESS_TOKEN_SECRET = props.getProperty("oauth.accessTokenSecret");
	}

}
