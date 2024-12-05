package com.example.spotify.controllers;

import com.example.spotify.models.dtos.SongDTO;
import com.example.spotify.models.users.SpotifyUserDetails;
import com.example.spotify.services.SongService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final SongService songService;

    public HomeController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal SpotifyUserDetails userDetails){
        List<SongDTO> popSongs = songService.getAllSongsByStyle("pop");
        List<SongDTO> rockSongs = songService.getAllSongsByStyle("rock");
        List<SongDTO> jazzSongs = songService.getAllSongsByStyle("jazz");

        List<SongDTO> loggedUserSongs = songService.getLoggedUserSongs(userDetails.getUsername());

        String totalDurationOfPlaylist = songService.getTotalDurationOfPlaylist(userDetails.getUsername());

        model.addAttribute("allPopSongs", popSongs);
        model.addAttribute("allRockSongs", rockSongs);
        model.addAttribute("allJazzSongs", jazzSongs);

        model.addAttribute("loggedUserSongs", loggedUserSongs);

        model.addAttribute("totalDurationOfPlaylist", totalDurationOfPlaylist);

        return "home";
    }
}
