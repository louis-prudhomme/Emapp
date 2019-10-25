package se.m1.emapp.controller;

public class ControllerFactory {
    public static IController dispatch(ServletContext context, StateOfPower state) {
        switch (state) {
            case SESSION:
                return new SessionController(context);
            case EMPLOYEE:
                return new EmployeeController(context);
            default:
                //todo throw error
                return null;
        }
    }
}
