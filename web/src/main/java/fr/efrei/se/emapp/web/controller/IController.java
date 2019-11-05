package fr.efrei.se.emapp.web.controller;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * this interface specifies what should the application expect from a controller
 * a controller should be able to handle a request
 */
public interface IController {
    /**
     * tells the controller to handle user’s request
     * @param action user's request
     * @return a string representing the page to serve
     * @throws ServletException unexpected, usually a database problem
     * @throws IOException unexpected
     */
    String handle(WordOfPower action) throws ServletException, IOException;
}
