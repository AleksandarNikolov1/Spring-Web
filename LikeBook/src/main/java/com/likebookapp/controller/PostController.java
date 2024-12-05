package com.likebookapp.controller;

import com.likebookapp.model.dto.CreatePostDTO;
import com.likebookapp.model.user.LikeBookUserDetails;
import com.likebookapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/add")
    public String getAddPostPage(Model model) {
        if (!model.containsAttribute("createPostModel")) {
            model.addAttribute("createPostModel", new CreatePostDTO());
        }

        return "post-add";
    }

    @PostMapping("/posts/add")
    public String createPost(@Valid @ModelAttribute("createPostModel") CreatePostDTO createPostDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal LikeBookUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createPostModel", createPostDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createPostModel",
                    bindingResult);

            return "redirect:/posts/add";
        }

        postService.createPost(createPostDTO, userDetails);

        return "redirect:/home";
    }

    @PostMapping("/post/remove/{id}")
    public String removePost(@PathVariable Long id, @AuthenticationPrincipal LikeBookUserDetails userDetails) {
        postService.removePostByIdAndUsername(id, userDetails.getUsername());
        return "redirect:/home";
    }

    @PostMapping("/post/like/{id}")
    public String likePost(@PathVariable Long id, @AuthenticationPrincipal LikeBookUserDetails userDetails) {
        postService.likePost(id, userDetails.getUsername());
        return "redirect:/home";
    }
}
