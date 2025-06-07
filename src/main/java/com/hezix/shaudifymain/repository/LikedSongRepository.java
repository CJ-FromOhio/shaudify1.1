package com.hezix.shaudifymain.repository;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikedSongRepository extends JpaRepository<LikedSong, Long> {
    LikedSong deleteLikedSongById(Long id);
    Optional<LikedSong> findLikedSongByUserId(Long userId);
    Optional<LikedSong> findLikedSongByUserIdAndSongId(Long userId, Long songId);
}
