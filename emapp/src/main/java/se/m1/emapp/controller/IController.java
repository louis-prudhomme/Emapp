package se.m1.emapp.controller;

import javax.servlet.ServletException;
import java.io.IOException;

public interface IController {
    String handle(WordOfPower action) throws ServletException, IOException;
}
