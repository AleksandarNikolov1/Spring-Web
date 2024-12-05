package com.likebookapp.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "posts_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> postLikes = new LinkedHashSet<>();
    @ManyToOne
    @JoinColumn(name = "mood_id", nullable = false)
    private Mood mood;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<User> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(Set<User> postLikes) {
        this.postLikes = postLikes;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }
}
