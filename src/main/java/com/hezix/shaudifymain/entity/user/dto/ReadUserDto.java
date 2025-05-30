package com.hezix.shaudifymain.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserDto {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Role role;
    @JsonManagedReference
    private List<Song> createdSongs = new ArrayList<>();
    @JsonManagedReference
    private List<LikedSong> likedSongs = new ArrayList<>();

    private Instant createdAt;
}
