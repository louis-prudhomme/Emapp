package se.m1.emapp.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import se.m1.emapp.model.core.DBLink;

import javax.servlet.http.HttpSession;

public class ServletContext {
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpSession getSession() {
        return request.getSession();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public DBLink getDbLink() {
        return (DBLink)request.getAttribute("dbLink");
    }
}
