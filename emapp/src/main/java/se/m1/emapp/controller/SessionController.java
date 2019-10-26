package se.m1.emapp.controller;

import se.m1.emapp.TheOneServlet;
import se.m1.emapp.model.business.AppDbHelper;
import se.m1.emapp.model.business.Credential;
import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.exception.DBComException;
import se.m1.emapp.model.exception.EmptyParameterException;
import se.m1.emapp.model.exception.EmptyResultException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static se.m1.emapp.utils.Constants.*;

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
    private DBLink dbLink;

    /**
     * default constructor
     * @param request http servlet request
     * @param response http servlet response
     */
    SessionController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.dbLink = (DBLink)request.getAttribute("dbLink");
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
     * @throws ServletException unexpected
     * @throws IOException unexpected
     */
    private String logUser() throws ServletException, IOException {
        String login = request.getParameter("loginField");
        String password = request.getParameter("pwdField");

        try {
            Credential user = new AppDbHelper(dbLink).checkCredentials(login, password);
            session.setAttribute("user", user);
            return JSP_WELCOME_PAGE;
        } catch (DBComException e) {
            TheOneServlet.setErrorMessage(request, e, DB_COM_ERROR_CODE);
            return JSP_ERROR_PAGE;
        } catch (EmptyResultException e) {
            request.setAttribute("errKey", ERR_MESSAGE_INVALID_CREDENTIALS);
            return JSP_HOME_PAGE;
        } catch (EmptyParameterException e) {
            request.setAttribute("errKey", ERR_MESSAGE_FIELD_EMPTY);
            return JSP_HOME_PAGE;
        }
    }
}
