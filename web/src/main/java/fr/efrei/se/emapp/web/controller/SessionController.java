package fr.efrei.se.emapp.web.controller;

import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.model.exception.UnauthorizedException;
import fr.efrei.se.emapp.common.model.exception.WrongParameterException;
import fr.efrei.se.emapp.common.model.exception.EmptyResultException;
import fr.efrei.se.emapp.web.utils.HttpRequestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static fr.efrei.se.emapp.web.utils.Constants.*;

/**
 * this controller handles everything session-related
 * it logs the user in and out
 */
public class SessionController implements IController {
    private HttpServletRequest request;
    private HttpSession session;

    /**
     * default constructor
     * @param request http servlet request
     */
    SessionController(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
    }

    /**
     * allows the controller to handle a request
     * it can handle log-related requests, including
     * login, logout and the default case (null)
     * @param action user's request
     * @return a string representing the view to serve
     * @throws IOException unexpected
     */
    @Override
    public String handle(WordOfPower action) throws IOException {
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
    private String logUser() throws IOException {
        try {
            CredentialTranscript user = new CredentialTranscript();
            user.setLogin(request.getParameter("loginField"));
            user.setPassword(request.getParameter("pwdField"));

            //token is null cause the user is not yet authenticated
            user = HttpRequestHelper.post(CREDENTIALS_URI, null, CredentialTranscript.class, "user", user);
            session.setAttribute("user", user);
            return JSP_WELCOME_PAGE;
        } catch (EmptyResultException e) {
            request.setAttribute("errKey", ERR_MESSAGE_INVALID_CREDENTIALS);
            return JSP_HOME_PAGE;
        } catch (WrongParameterException e) {
            request.setAttribute("errKey", ERR_MESSAGE_FIELD_EMPTY);
            return JSP_HOME_PAGE;
        }
    }
}
