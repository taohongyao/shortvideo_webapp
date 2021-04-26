package com.example.shortvideo_webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    private static final Logger logger= LoggerFactory.getLogger(HelloServlet.class);

    @Override
    public void init() {
        message = "Hello World!";
        logger.debug("Init: {}",message);
        logger.info("Init: {}",message);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("Do Get");
        logger.info("Do Get");
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        logger.debug("Destroy");
    }
}