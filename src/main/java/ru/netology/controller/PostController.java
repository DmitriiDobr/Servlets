package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;
  private static final Gson gson = new Gson();

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = this.service.all();
    serializeResponse(response,data);
  }

  public void getById(Integer id, HttpServletResponse response) throws IOException {
    // TODO: deserialize request & serialize response
    Post post=this.service.getById(id);
    serializeResponse(response,post);
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    final var post = gson.fromJson(body, Post.class);
    final var data = this.service.save(post);
    serializeResponse(response,data);
  }

  public void removeById(Integer id, HttpServletResponse response) throws IOException {
    // TODO: deserialize request & serialize response
    this.service.removeById(id);
    serializeResponse(response, "post with id=" + id + " deleted successfully");
  }


  public static <T> void serializeResponse(HttpServletResponse response,T data) throws IOException {
    response.setContentType(APPLICATION_JSON);
    String toJson = gson.toJson(data);
    response.getWriter().print(toJson);

  }



}
