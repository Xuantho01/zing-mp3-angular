package com.bolero.boleroteam.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;

    @NotEmpty
    private String name;

    @ManyToOne
    private User user;

    @ManyToMany()
    @JoinTable(
            name = "playlist_songList",
            joinColumns = @JoinColumn(name = "playList_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> songList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
