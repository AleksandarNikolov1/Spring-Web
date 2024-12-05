package com.likebookapp.model.dto;

import com.likebookapp.model.enums.EMood;

public class PostDTO {
    private Long id;
    private String creatorName;
    private EMood mood;
    private int postLikesCount;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public EMood getMood() {
        return mood;
    }

    public void setMood(EMood mood) {
        this.mood = mood;
    }

    public int getPostLikesCount() {
        return postLikesCount;
    }

    public void setPostLikesCount(int postLikesCount) {
        this.postLikesCount = postLikesCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
