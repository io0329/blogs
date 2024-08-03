package com.example.log4shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String output, email = request.getParameter("email");
        String password = request.getParameter("password");
        Logger logger = LogManager.getLogger(Login.class);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        out.println("Welcome " + email + "!!");
        out.println("Password: " + password);
        if (email.startsWith("msglookup@")) {
            out.println("Mode: Message Lookup");
            output = "Message Lookup: " + password;
        } else if (email.startsWith("threadcontext@")) {
            ThreadContext.put("apiversion", password);
            out.println("Mode: Thread Context");
            output = "No Message Lookup! Thread Context instead!";
        } else {
            out.println("Mode: Message Lookup (default)");
            output = "Message Lookup (default): " + password;
        }
        logger.error(output);
    }
}
