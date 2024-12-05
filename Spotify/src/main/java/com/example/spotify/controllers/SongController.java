package com.example.spotify.controllers;

import com.example.spotify.models.dtos.AddSongDTO;
import com.example.spotify.models.users.SpotifyUserDetails;
import com.example.spotify.services.SongService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs/add")
    public String getAddSongPage(Model model) {
        if (!model.containsAttribute("addSongModel")) {
            model.addAttribute("addSongModel", new AddSongDTO());
        }
        return "song-add";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/songs/add")
    public String addSong(@Valid @ModelAttribute("addSongModel") AddSongDTO addSongDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addSongModel", addSongDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addSongModel", bindingResult);

            return "redirect:/songs/add";
        }

        songService.addSong(addSongDTO);

        return "redirect:/home";
    }

    @PostMapping("/home")
    public String addSongToPlaylist(@RequestParam("songId") Long songId,
                                @AuthenticationPrincipal SpotifyUserDetails userDetails) {
        songService.addSongToPlaylist(songId, userDetails.getUsername());
        return "redirect:/home";
    }

    @DeleteMapping("/home/delete-all")
    public String deletePlaylist(@AuthenticationPrincipal SpotifyUserDetails userDetails){
        songService.deletePlaylist(userDetails.getUsername());
        return "redirect:/home";
    }
}
