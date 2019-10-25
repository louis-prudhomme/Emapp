package se.m1.emapp.controller;

import se.m1.emapp.model.business.AppDbHelper;
import se.m1.emapp.model.business.Credential;
import se.m1.emapp.model.core.exception.DBComException;
import se.m1.emapp.model.exception.EmptyParameterException;
import se.m1.emapp.model.exception.EmptyResultException;

import javax.servlet.ServletException;
import java.io.IOException;

import static se.m1.emapp.utils.Constants.*;

public class SessionController implements IController {
    private ServletContext servletContext;

    SessionController(ServletContext context) {
        this.servletContext = context;
    }

    @Override
    public String handle(WordOfPower action) throws ServletException, IOException {
        switch (action) {
            case LOGIN:
                return logUser();
            case LOGOUT:
                servletContext.getSession().invalidate();
                return JSP_GOODBYE_PAGE;
            default:
                return JSP_HOME_PAGE;
        }
    }

    private String logUser() throws ServletException, IOException {
        String login = servletContext.getRequest().getParameter("loginField");
        String password = servletContext.getRequest().getParameter("pwdField");

        try {
            Credential user = new AppDbHelper(servletContext.getDbLink()).checkCredentials(login, password);
            servletContext.getSession().setAttribute("user", user);
            return JSP_WELCOME_PAGE;
        } catch (DBComException e) {
            //todo sendError(request, response, e);
            TheOneServlet.sendError(servletContext.getRequest(), servletContext.getResponse(), e);
            return null;
        } catch (EmptyResultException e) {
            servletContext.getRequest().setAttribute("errKey", ERR_MESSAGE_INVALID_CREDENTIALS);
            return JSP_HOME_PAGE;
        } catch (EmptyParameterException e) {
            servletContext.getRequest().setAttribute("errKey", ERR_MESSAGE_FIELD_EMPTY);
            return JSP_HOME_PAGE;
        }
    }
}
