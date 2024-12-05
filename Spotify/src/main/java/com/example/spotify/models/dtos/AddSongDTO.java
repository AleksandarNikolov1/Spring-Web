package com.example.spotify.models.dtos;

import com.example.spotify.models.enums.EStyle;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AddSongDTO {
    @NotBlank(message = "Performer's name is required!")
    @Size(min = 3, max = 20, message = "Performer's name length must be between 3 and 20 characters!")
    private String performer;

    @NotBlank(message = "Song's title is required!")
    @Size(min = 2, max = 20, message = "Title length must be between 2 and 20 characters!")
    private String title;

    @PastOrPresent(message = "The date cannot be in the future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull(message = "Song's duration is required!")
    @Positive(message = "Duration must be positive!")
    private int duration;

    @NotNull(message = "Song's style is required!")
    private EStyle style;

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public EStyle getStyle() {
        return style;
    }

    public void setStyle(EStyle style) {
        this.style = style;
    }
}
