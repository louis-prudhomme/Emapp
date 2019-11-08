package fr.efrei.se.emapp.web.controller;

import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.security.Role;
import fr.efrei.se.emapp.web.TheOneServlet;
import fr.efrei.se.emapp.web.utils.HttpRequestHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static fr.efrei.se.emapp.web.utils.Constants.*;

/**
 * this controller handles everything session-related
 * it logs the user in and out
 */
public class SessionController implements IController {
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * session and dblink are shortcuts to avoid cluttering the code with calls through request
     */
    private HttpSession session;

    /**
     * default constructor
     * @param request http servlet request
     * @param response http servlet response
     */
    SessionController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * allows the controller to handle a request
     * it can handle log-related requests, including
     * login, logout and the default case (null)
     * @param action user's request
     * @return a string representing the view to serve
     * @throws ServletException unexpected
     * @throws IOException unexpected
     */
    @Override
    public String handle(WordOfPower action) throws ServletException, IOException {
        switch (action) {
            case LOGIN:
                return logUser();
            case LOGOUT:
                session.invalidate();
                return JSP_GOODBYE_PAGE;
            default:
                return JSP_HOME_PAGE;
        }
    }

    /**
     * tries to log the user in
     * @return either welcome page if the user was successfully logged in, or an error page
     */
    private String logUser() {
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("login", request.getParameter("loginField"));
            params.put("password", request.getParameter("pwdField"));

            CredentialTranscript user = HttpRequestHelper.post(CREDENTIALS_URI, null, CredentialTranscript.class, params);
            session.setAttribute("user", user);
            return JSP_WELCOME_PAGE;
        } catch (Exception e) {
            TheOneServlet.setErrorMessage(request, e, DB_COM_ERROR_CODE);
            return JSP_ERROR_PAGE;/*
        } catch (EmptyResultException e) {
            request.setAttribute("errKey", ERR_MESSAGE_INVALID_CREDENTIALS);
            return JSP_HOME_PAGE;
        } catch (EmptyParameterException e) {
            request.setAttribute("errKey", ERR_MESSAGE_FIELD_EMPTY);
            return JSP_HOME_PAGE;*/
        }
    }
}
