package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String apiPosts="/api/posts";
    private static final String apiPostsId="/api/posts/\\d+";
    @Override
    public void init() {

        final var context = new AnnotationConfigApplicationContext("ru.netology");
        controller =(PostController) context.getBean("controller");


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals("GET") && path.equals(apiPosts)) {
                controller.all(resp);
                return;
            }
            if (method.equals("GET") && path.matches(apiPostsId)) {
                // easy way
                final var id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
                return;
            }
            if (method.equals("POST") && path.equals(apiPosts)) {

                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals("DELETE") && path.matches(apiPostsId)) {
                // easy way
                final var id = Integer.parseInt(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

