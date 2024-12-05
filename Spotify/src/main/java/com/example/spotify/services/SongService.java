package com.example.spotify.services;

import com.example.spotify.models.dtos.AddSongDTO;
import com.example.spotify.models.dtos.SongDTO;
import com.example.spotify.models.entities.Song;
import com.example.spotify.models.entities.Style;
import com.example.spotify.models.entities.User;
import com.example.spotify.models.enums.EStyle;
import com.example.spotify.repositories.SongRepository;
import com.example.spotify.repositories.StyleRepository;
import com.example.spotify.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final StyleRepository styleRepository;
    private final ModelMapper modelMapper;


    public SongService(SongRepository songRepository, UserRepository userRepository, StyleRepository styleRepository, ModelMapper modelMapper) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
        this.styleRepository = styleRepository;
        this.modelMapper = modelMapper;
    }

    public void addSong(AddSongDTO addSongDTO) {
        Song song = modelMapper.map(addSongDTO, Song.class);
        Style style = styleRepository.findByName(addSongDTO.getStyle());

        song.setStyle(style);
        songRepository.save(song);
    }

    public void addSongToPlaylist(Long songId, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Song> song = songRepository.findById(songId);

        if (user.isPresent() && song.isPresent()) {
            user.get().getSongList().add(song.get());
            userRepository.save(user.get());
        }
    }

    public List<SongDTO> getAllSongsByStyle(String style) {
        return songRepository
                .findAllByStyle_Name(EStyle.valueOf(style.toUpperCase()))
                .stream()
                .map(song -> {
                    SongDTO songDTO = modelMapper.map(song, SongDTO.class);
                    songDTO.setDurationInMinutes(getSongDurationInMinutes(song));
                    return songDTO;
                })
                .collect(Collectors.toList());
    }

    public List<SongDTO> getLoggedUserSongs(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        return user.map(value -> value.getSongList()
                .stream()
                .map(song -> {
                    SongDTO songDTO = modelMapper.map(song, SongDTO.class);
                    songDTO.setDurationInMinutes(getSongDurationInMinutes(song));
                    return songDTO;
                })
                .collect(Collectors.toList())).orElse(null);

    }

    public String getTotalDurationOfPlaylist(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            int totalDurationInSeconds = 0;

            for (Song song : user.get().getSongList()) {
                totalDurationInSeconds += song.getDuration();
            }

            int minutes = totalDurationInSeconds / 60;
            int seconds = totalDurationInSeconds - minutes * 60;

            if (seconds < 10)
                return String.format("%d:0%d", minutes, seconds);

            else
                return String.format("%d:%d", minutes, seconds);
        }

        return null;
    }

    private String getSongDurationInMinutes(Song song) {
        int songMinutes = song.getDuration() / 60;
        int songSeconds = song.getDuration() - songMinutes * 60;

        if (songSeconds < 10)
            return String.format("%d:0%d", songMinutes, songSeconds);

        else
            return String.format("%d:%d", songMinutes, songSeconds);
    }

    public void deletePlaylist(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            user.get().getSongList().clear();
            userRepository.save(user.get());
        }
    }
}
