package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {

    private final ConcurrentHashMap<Integer, Post> repositoryStorage = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public Collection<Post> all() {
        return repositoryStorage.values();
    }

    public Optional<Post> getById(Integer id) {
        Post post = repositoryStorage.get(id);
        return post != null ? Optional.of(post) : Optional.empty();
    }

    ;

    public Post save(Post post) {
        System.out.println("nanan");
        if (post.getId() == 0) {
            post.setId(counter.get());
            repositoryStorage.put(counter.get(), post);
            counter.addAndGet(1);
            return post;

        } else {
            Post p = repositoryStorage.get(post.getId());
            if (p != null) {
                repositoryStorage.replace(post.getId(), p);
            }
            return p;
        }


    }

    public boolean removeById(Integer id) {
        if (id != null) {
            repositoryStorage.remove(id);
            return true;
        }
        return false;
    }


}
