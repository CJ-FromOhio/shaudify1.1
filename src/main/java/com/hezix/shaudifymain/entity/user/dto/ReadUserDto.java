package com.hezix.shaudifymain.entity.user.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserDto implements Serializable {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private Role role;
    @JsonManagedReference
    private List<ReadSongDto> createdSongs = new ArrayList<>();
    @JsonManagedReference
    private List<ReadLikedSongDto> likedSongs = new ArrayList<>();

    private Instant createdAt;
}
