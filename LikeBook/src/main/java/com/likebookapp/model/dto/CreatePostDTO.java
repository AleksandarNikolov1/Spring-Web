package com.likebookapp.model.dto;

import com.likebookapp.model.enums.EMood;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePostDTO {
    @NotBlank(message = "Content can not be empty!")
    @Size(min = 2, max = 50, message = "Content length must be between 2 and 50 characters!")
    private String content;

    @NotNull(message = "You must select a mood!")
    private EMood mood;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EMood getMood() {
        return mood;
    }

    public void setMood(EMood mood) {
        this.mood = mood;
    }
}
