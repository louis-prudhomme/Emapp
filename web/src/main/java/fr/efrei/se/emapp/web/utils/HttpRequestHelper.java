package fr.efrei.se.emapp.web.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.efrei.se.emapp.common.model.exception.UnauthorizedException;
import fr.efrei.se.emapp.common.model.exception.WrongParameterException;
import fr.efrei.se.emapp.common.model.exception.EmptyResultException;

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

/**
 * intermediary class helping with formatting the http requests to the rest api
 */
public class HttpRequestHelper {
    /**
     * json serializer
     */
    private static Gson cypher = new Gson();

    /**
     * launches an http request with the provided method, uri and token
     * @param method htttp method
     * @param uri actual url
     * @param token authorization token
     * @return result of the request
     * @throws IOException ¯\_(ツ)_/¯
     */
    public static String request(HttpMethod method, String uri, String token) throws IOException {
        return request(method, uri, token, null);
    }

    /**
     * reads an http response
     * @param response http response
     * @return string-formated response content
     * @throws IOException ¯\_(ツ)_/¯
     */
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

    public static <T> T post(String uri, String token, Class<T> clazz, String paramName, Object param) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put(paramName, cypher.toJson(param));
        return cypher.fromJson(request(POST, uri, token, map), clazz);
    }

    public static String put(String uri, String token, String paramName, Object param) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put(paramName, cypher.toJson(param));
        return request(PUT, uri, token, map);
    }

    public static String post(String uri, String token, String paramName, Object param) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put(paramName, cypher.toJson(param));
        return request(POST, uri, token, map);
    }

    /**
     * launches an http get request and desrializes the response in a list of the provided class
     * @param uri address of the service
     * @param token authorization token
     * @param klass wanted class for the list
     * @param <T> insures type consistency
     * @return new instance of a list of the provided class
     * @throws IOException ¯\_(ツ)_/¯
     */
    public static <T> ArrayList<T> getAll(String uri, String token, Class<T> klass) throws IOException {
        Type listType = TypeToken.getParameterized(ArrayList.class, klass).getType();
        return cypher.<ArrayList>fromJson(HttpRequestHelper.request(GET, uri, token), listType);
    }

    /**
     * launches an http request with the provided method, uri and token
     * @param method http method
     * @param uri address of the service
     * @param token authorization token
     * @param params hashmap of parameters and their names
     * @return received response
     * @throws IOException ¯\_(ツ)_/¯
     */
    public static String request(HttpMethod method, String uri, String token, HashMap<String, String> params) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod(method.name());
        connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        connection.setRequestProperty(HttpHeaders.AUTHORIZATION, token);

        if(params != null) {
            byte[] encodedParams = convertToPostParameters(params);
            connection.setRequestProperty(HttpHeaders.CONTENT_LENGTH, String.valueOf(encodedParams.length));
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.getOutputStream().write(encodedParams);
        }

        if(connection.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR)
            throw new IOException();
        else if(connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND)
            throw new EmptyResultException();
        else if(connection.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN)
            throw new UnauthorizedException();
        else if(connection.getResponseCode() == HttpURLConnection.HTTP_BAD_METHOD)
            throw new WrongParameterException();

        String result = readResponse(connection.getInputStream());
        connection.disconnect();
        return result;
    }

    /**
     * converts an hashmap of parameters to a byte array for post purposes
     * @param parameters hashmap of the desired parameters, with the key being the identifier of the parameter
     * @return byte array encoded parameters
     * @throws UnsupportedEncodingException should not throw that
     */
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
