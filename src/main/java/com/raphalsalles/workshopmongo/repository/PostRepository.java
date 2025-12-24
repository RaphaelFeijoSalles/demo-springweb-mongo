package com.raphalsalles.workshopmongo.repository;

import com.raphalsalles.workshopmongo.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{

    List<Post> findByTitle(String text);

    List<Post> findByTitleContainingIgnoreCase(String title);
}
