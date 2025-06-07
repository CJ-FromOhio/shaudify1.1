package com.hezix.shaudifymain.service;

import com.hezix.shaudifymain.entity.likedSong.dto.ReadLikedSongDto;
import com.hezix.shaudifymain.entity.song.Song;
import com.hezix.shaudifymain.entity.song.dto.CreateSongDto;
import com.hezix.shaudifymain.entity.song.dto.ReadSongDto;
import com.hezix.shaudifymain.exception.EntityNotFoundException;
import com.hezix.shaudifymain.mapper.song.SongCreateMapper;
import com.hezix.shaudifymain.mapper.song.SongReadMapper;
import com.hezix.shaudifymain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final UserService userService;
    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;

    @Transactional()
    public ReadSongDto save(CreateSongDto createSongDto, UserDetails userDetails) {
        Song song = songCreateMapper.toEntity(createSongDto);
        song.setCreatedAt(Instant.now());
        var user = userService.findUserEntityByUsernameWithCreatedSongs(userDetails.getUsername());
        user.getCreatedSong().add(song);
        song.setCreator(user);
        songRepository.save(song);
        return songReadMapper.toDto(song);
    }
    @Cacheable(value = "SongService::findSongById", key = "#id")
    @Transactional(readOnly = true)
    public ReadSongDto findSongById(Long id) {
        return songReadMapper.toDto(songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with id " + id + " not found")));
    }
    @Transactional(readOnly = true)
    public List<ReadSongDto> findSonsIdgByLikedSongList(List<ReadLikedSongDto> readLikedSongDtoList) {
        return readLikedSongDtoList.stream()
                .map(ReadLikedSongDto::getSongId)
                .map(this::findSongById)
                .toList();
    }
    @Cacheable(value = "SongService::findSongEntityById", key = "#id")
    @Transactional(readOnly = true)
    public Song findSongEntityById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song Entity with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<ReadSongDto> findAllSongs() {
        return songReadMapper.toDtoList(songRepository.findAll());
    }
    @Transactional()
    public ReadSongDto deleteSongById(Long id) {
        var song = findSongById(id);
        songRepository.delete(songReadMapper.toEntity(song));
        return song;
    }

}
