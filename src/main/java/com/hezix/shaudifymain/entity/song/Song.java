package com.hezix.shaudifymain.entity.song;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hezix.shaudifymain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "songs")
public class Song  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "author_id")
    private User creator;

    @Column(nullable = false)
    private Instant createdAt;
}
