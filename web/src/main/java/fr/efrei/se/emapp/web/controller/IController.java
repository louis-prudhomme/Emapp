package fr.efrei.se.emapp.web.controller;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * This interface specifies what should the application expect from a controller
 */
public interface IController {
    /**
     * Tells the controller to handle user’s request
     * @param action user's request
     * @return a {@link String} representing the page to serve
     * @throws ServletException unexpected, usually a database problem
     * @throws IOException unexpected
     */
    String handle(WordOfPower action) throws ServletException, IOException;
}
