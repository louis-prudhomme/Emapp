package fr.efrei.se.emapp.web.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ws.rs.core.HttpHeaders;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static fr.efrei.se.emapp.web.utils.HttpMethod.*;

public class HttpRequestHelper {
    private static Gson cypher = new Gson();

    public static String request(HttpMethod method, String uri, String token) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod(method.name());
        connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        connection.setRequestProperty(HttpHeaders.AUTHORIZATION, token);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        if(connection.getResponseCode() == 500)
        {
            throw new IOException("Http request returned an error 500 !");
        }
        String result = readResponse(connection.getInputStream());
        connection.disconnect();
        return result;
    }

    private static String readResponse(InputStream response) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(response));

        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    public static <T> T get(String uri, String token, Class<T> clazz) throws IOException {
        return cypher.fromJson(HttpRequestHelper.request(GET, uri, token), clazz);
    }

    public static <T> T post(String uri, String token, Class<T> clazz, HashMap<String, String> params) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        byte[] encodedParams = convertToPostParameters(params);

        connection.setRequestMethod(POST.name());
        connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        connection.setRequestProperty(HttpHeaders.AUTHORIZATION, token);
        connection.setRequestProperty(HttpHeaders.CONTENT_LENGTH, String.valueOf(encodedParams.length));

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.getOutputStream().write(encodedParams);

        if(connection.getResponseCode() == 500)
        {
            throw new IOException("Http request returned an error 500 !");
        }
        String result = readResponse(connection.getInputStream());
        connection.disconnect();
        return cypher.fromJson(result, clazz);
    }

    public static <T> ArrayList<T> getAll(String uri, String token, Class<T> klass) throws IOException {
        Type listType = TypeToken.getParameterized(ArrayList.class, klass).getType();
        return cypher.<ArrayList>fromJson(HttpRequestHelper.request(GET, uri, token), listType);
    }

    public static void put(String uri, String token, Object parameter) throws IOException {
        //request(PUT, uri, token, parameter);
    }

    private static byte[] convertToPostParameters(HashMap<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();

        for(Map.Entry<String, String> param : parameters.entrySet()) {
            if (data.length() != 0) data.append('&');
            data.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.name()));
            data.append("=");
            data.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8.name()));
        }
        return data.toString().getBytes();
    }
}
