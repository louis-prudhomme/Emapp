package fr.efrei.se.emapp.web.controller;

import java.io.IOException;

/**
 * This interface specifies what should the application expect from a controller
 */
public interface IController {
    /**
     * Tells the controller to handle user’s request
     * @param action user's request
     * @return a {@link String} representing the page to serve
     * @throws IOException unexpected, usually a database problem
     */
    String handle(WordOfPower action) throws IOException;
}
