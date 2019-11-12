package fr.efrei.se.emapp.web.controller;

import fr.efrei.se.emapp.web.TheOneServlet;

import javax.servlet.http.HttpServletRequest;

import static fr.efrei.se.emapp.web.utils.Constants.USER_WRONG_PAGE_CODE;

/**
 * this factory dispatches a controller depending on the situation
 */
public class ControllerFactory {
    public static IController dispatch(HttpServletRequest request, StateOfPower state) {
        switch (state) {
            case SESSION:
                return new SessionController(request);
            case EMPLOYEE:
                return new EmployeeController(request);
            default:
                return null;
        }
    }
}
