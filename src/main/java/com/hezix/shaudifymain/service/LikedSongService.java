package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.likedSong.LikedSong;
import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.mapper.likedSong.LikedSongReadMapper;
import com.hezix.shaudifymain.repository.LikedSongRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikedSongService {

    private final UserService userService;
    private final SongService songService;
    private final LikedSongRepository likedSongRepository;
    private final LikedSongReadMapper likedSongReadMapper;

    @Transactional()
    public ReadLikedSongDto like(Long songId, UserDetails userDetails) {
        User user = userService.findUserEntityByUsername(userDetails.getUsername());
        if (findLikedSongBooleanByUserIdAndSongId(songId, userDetails)){
            return null;
        }
        var likedSong = LikedSong.builder()
                .song(songService.findSongEntityById(songId))
                .user(user)
                .build();

        likedSongRepository.save(likedSong);
        return likedSongReadMapper.toDto(likedSong);
    }
    @Cacheable(value = "LikedSongService::findLikedSongBooleanByUserIdAndSongId", key = "#songId + '-' + #userDetails.username")
    @Transactional(readOnly = true)
    public Boolean findLikedSongBooleanByUserIdAndSongId(Long songId, UserDetails userDetails) {
        User user = userService.findUserEntityByUsername(userDetails.getUsername());
        boolean liked = likedSongRepository
                .findLikedSongByUserIdAndSongId(user.getId(), songId)
                .isPresent();
        return liked;
    }
    @Cacheable(value = "LikedSongService::findLikedSongByUserId", key = "#userId")
    @Transactional(readOnly = true)
    public ReadLikedSongDto findLikedSongByUserId(Long userId) {
        return likedSongReadMapper.toDto(likedSongRepository
                .findLikedSongByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Error finding liked song")));
    }
    @Cacheable(value = "LikedSongService::findLikedSongBySongId", key = "#songId")
    @Transactional(readOnly = true)
    public ReadLikedSongDto findLikedSongById(Long id) {
        return likedSongReadMapper.toDto(likedSongRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LikedSong with id " + id + " not found")));
    }
    @Transactional(readOnly = true)
    public List<ReadLikedSongDto> findAllLikedSongs() {
        return likedSongReadMapper.toDtoList(likedSongRepository.findAll());
    }
    @Caching(evict = {
            @CacheEvict(value = "LikedSongService::findLikedSongByUserId", key = "#result.userId"),
            @CacheEvict(value = "LikedSongService::findLikedSongBySongId", key = "#result.songId"),
    })
    @Transactional()
    public ReadLikedSongDto deleteLikedSong(Long id) {
        return likedSongReadMapper.toDto(likedSongRepository.deleteLikedSongById(id));
    }

}
