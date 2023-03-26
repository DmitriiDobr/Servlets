package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;

import java.util.concurrent.atomic.AtomicInteger;

public class PostRepository {

    private final List<Post> repositoryStorage = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger counter = new AtomicInteger(0);

    public List<Post> all() {
        return repositoryStorage;
    }

    public Optional<Post> getById(Integer id) {
        Post post = repositoryStorage.get(id);
        return post != null ? Optional.of(post) : Optional.empty();
    }

    ;

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(counter.get());
            counter.addAndGet(1);
            repositoryStorage.add(post);

            return post;

        } else {
            Post p = findPostById(post.getId());
            if (p != null) {
                repositoryStorage.set(repositoryStorage.indexOf(p), post);
            }
            return p;
        }


    }

    public boolean removeById(Integer id) {
        Post p = findPostById(id);
        if (p != null) {
            repositoryStorage.remove(p);
            return true;
        }
        return false;
    }

    private Post findPostById(Integer id) {
        return repositoryStorage.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
