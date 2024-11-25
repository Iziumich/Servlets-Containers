package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  private void setResponseHeaders(HttpServletResponse response) {
    response.setContentType(APPLICATION_JSON);
    response.setCharacterEncoding("UTF-8");
  }

  public void all(HttpServletResponse response) throws IOException {
    setResponseHeaders(response);
    final var data = service.all();
    final var gson = new Gson();
    response.getWriter().print(gson.toJson(data));
    response.setStatus(HttpServletResponse.SC_OK);
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    setResponseHeaders(response);
    try {
      final var post = service.getById(id);
      final var gson = new Gson();
      response.getWriter().print(gson.toJson(post));
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
    }
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    setResponseHeaders(response);
    final var gson = new Gson();
    final var post = gson.fromJson(body, Post.class);
    final var data = service.save(post);
    response.getWriter().print(gson.toJson(data));
    response.setStatus(HttpServletResponse.SC_OK);
  }

  public void removeById(long id, HttpServletResponse response) throws IOException {
    setResponseHeaders(response);
    try {
      service.removeById(id);
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
    }
  }
}