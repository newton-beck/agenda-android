package br.com.alura.agenda.http;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpClient {

    private static final String POST_URL = "https://www.caelum.com.br/mobile";
    private static final String CONTENT_TYPE = "application/json";

    private static final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

    public String post(String json) {
        try {
            URL url = new URL(POST_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", CONTENT_TYPE);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE);

            PrintStream out = new PrintStream(connection.getOutputStream());
            out.print(json);

            connection.connect();

            String resultado = new Scanner(connection.getInputStream()).next();

            connection.disconnect();

            return resultado;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
