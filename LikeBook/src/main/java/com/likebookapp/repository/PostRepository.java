package com.likebookapp.repository;

import com.likebookapp.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByCreator_Username(String username);
    List<Post> findAllByCreator_UsernameNot(String username);

    void deleteByIdAndCreator_Username(Long id, String username);

}
