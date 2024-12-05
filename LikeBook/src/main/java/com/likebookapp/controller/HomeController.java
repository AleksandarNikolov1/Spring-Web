package com.likebookapp.controller;

import com.likebookapp.model.dto.PostDTO;
import com.likebookapp.model.user.LikeBookUserDetails;
import com.likebookapp.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal LikeBookUserDetails userDetails){
        List<PostDTO> loggedUserPosts = postService.getLoggedUserPosts(userDetails.getUsername());
        List<PostDTO> allOtherPosts = postService.getAllOtherUserPosts(userDetails.getUsername());

        model.addAttribute("userPosts", loggedUserPosts);
        model.addAttribute("otherPosts", allOtherPosts);

        return "home";
    }
}
