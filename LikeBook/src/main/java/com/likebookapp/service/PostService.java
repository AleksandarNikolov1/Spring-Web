package com.likebookapp.service;

import com.likebookapp.model.dto.CreatePostDTO;
import com.likebookapp.model.dto.PostDTO;
import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.model.user.LikeBookUserDetails;
import com.likebookapp.repository.MoodRepository;
import com.likebookapp.repository.PostRepository;
import com.likebookapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MoodRepository moodRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostService(PostRepository postRepository, MoodRepository moodRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.moodRepository = moodRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void createPost(CreatePostDTO createPostDTO, LikeBookUserDetails userDetails) {
        Mood mood = moodRepository.findByName(createPostDTO.getMood());
        User creator = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        Post post = new Post();
        post.setContent(createPostDTO.getContent());
        post.setMood(mood);
        post.setCreator(creator);

        postRepository.save(post);
    }

    public List<PostDTO> getLoggedUserPosts(String username) {
        return postRepository.findAllByCreator_Username(username)
                .stream()
                .map(post -> {
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                    postDTO.setMood(post.getMood().getName());
                    postDTO.setPostLikesCount(post.getPostLikes().size());
                    return postDTO;
                })
                .collect(Collectors.toList());
    }

    public List<PostDTO> getAllOtherUserPosts(String username) {
        return postRepository.findAllByCreator_UsernameNot(username)
                .stream()
                .map(post -> {
                    PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                    postDTO.setMood(post.getMood().getName());
                    postDTO.setPostLikesCount(post.getPostLikes().size());
                    postDTO.setCreatorName(post.getCreator().getUsername());
                    return postDTO;
                })
                .collect(Collectors.toList());
    }

    public void removePostByIdAndUsername(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (post.getCreator().getUsername().equals(username)) {
            postRepository.delete(post);
        }

        else {
            throw new SecurityException("You are not allowed to delete this post");
        }
    }

    public void likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        User postLiker = userRepository.findByUsername(username).orElseThrow();

        post.getPostLikes().add(postLiker);
        postRepository.save(post);
    }
}
