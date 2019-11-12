package se.m1.emapp.controller;

import se.m1.emapp.TheOneServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se.m1.emapp.model.core.JPAManager;

import static se.m1.emapp.utils.Constants.USER_WRONG_PAGE_CODE;

/**
 * this factory dispatches a controller
 */
public class ControllerFactory {
    /**
     * 
     * @param request
     * @param response
     * @param jpa
     * @param state
     * @return 
     */
    public static IController dispatch(HttpServletRequest request, HttpServletResponse response, JPAManager jpa,StateOfPower state) {
        switch (state) {
            case SESSION:
                return new SessionController(request, response, jpa);
            case EMPLOYEE:
                return new EmployeeController(request, response, jpa);
            default:
                TheOneServlet.setErrorMessage(request, "", USER_WRONG_PAGE_CODE);
                return null;
        }
    }
}
