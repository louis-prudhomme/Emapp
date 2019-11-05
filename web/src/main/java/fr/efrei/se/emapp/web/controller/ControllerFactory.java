package fr.efrei.se.emapp.web.controller;

import fr.efrei.se.emapp.web.TheOneServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static fr.efrei.se.emapp.web.utils.Constants.USER_WRONG_PAGE_CODE;

/**
 * this factory dispatches a controller
 */
public class ControllerFactory {
    public static IController dispatch(HttpServletRequest request, HttpServletResponse response, StateOfPower state) {
        switch (state) {
            case SESSION:
                return new SessionController(request, response);
            case EMPLOYEE:
                return new EmployeeController(request, response);
            default:
                TheOneServlet.setErrorMessage(request, new Exception(""), USER_WRONG_PAGE_CODE);
                return null;
        }
    }
}
