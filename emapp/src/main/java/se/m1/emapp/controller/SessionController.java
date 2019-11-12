package se.m1.emapp.controller;

import se.m1.emapp.model.business.Credential;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javax.ejb.EJBException;
import se.m1.emapp.model.core.JPAManager;

import static se.m1.emapp.utils.Constants.*;

/**
 * this controller handles everything session-related
 * it logs the user in and out
 */
public class SessionController implements IController {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Credential user;
    

    private JPAManager jpa;
    
    
    private HttpSession session;


    /**
     * default constructor
     * @param request http servlet request
     * @param response http servlet response
     */
    SessionController(HttpServletRequest request, HttpServletResponse response, JPAManager jpa) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.jpa = jpa;

    }

    /**
     * allows the controller to handle a request
     * it can handle log-related requests, including
     * login, logout and the default case (null)
     * @param action user's request
     * @return a string representing the view to serve
     * @throws ServletException unexpected
     * @throws IOException unexpected
     * @throws EJBException
     */
    @Override
    public String handle(WordOfPower action) throws ServletException, IOException,EJBException {
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
     * @throws EJBException
     */
    private String logUser() throws ServletException, IOException,EJBException {
                    String login = request.getParameter("loginField");
                    String password = request.getParameter("pwdField");
                    user = null;
                    
                    if(login.equals("") || password.equals(""))
                    {
                        request.setAttribute("errKey", ERR_MESSAGE_FIELD_EMPTY);
                        return JSP_HOME_PAGE;
                       
                    }
                    
                    user = new Credential(login, password);
                        if(jpa.checkCredentials(user))
                        {
                            session.setAttribute("empList", jpa.getAll());
                            session.setAttribute("user", user);
                            return JSP_WELCOME_PAGE;
                        }else{
                            request.setAttribute("errKey", ERR_MESSAGE_INVALID_CREDENTIALS);
                            return JSP_HOME_PAGE;
                        }

        
    }
}
