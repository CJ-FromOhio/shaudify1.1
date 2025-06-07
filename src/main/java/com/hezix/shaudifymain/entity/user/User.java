package com.hezix.shaudifymain.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.song.Song;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;


    @JsonBackReference
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Song> createdSong = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<LikedSong> likedSongs = new ArrayList<>();

    @Column()
    private Instant createdAt;
}
