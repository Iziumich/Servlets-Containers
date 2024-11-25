package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong(1);

  public List<Post> all() {
    return posts.values().stream().collect(Collectors.toList());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      // Create new post
      long newId = idCounter.getAndIncrement();
      post.setId(newId);
      posts.put(newId, post);
    } else {
      // Update existing post
      if (posts.containsKey(post.getId())) {
        posts.put(post.getId(), post);
      } else {
        throw new NotFoundException("Post with id " + post.getId() + " not found");
      }
    }
    return post;
  }

  public void removeById(long id) {
    if (posts.remove(id) == null) {
      throw new NotFoundException("Post with id " + id + " not found");
    }
  }
}