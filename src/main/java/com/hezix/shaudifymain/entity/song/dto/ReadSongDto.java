package com.hezix.shaudifymain.entity.song.dto;

import com.hezix.shaudifymain.entity.user.User;
import com.hezix.shaudifymain.entity.user.dto.ReadUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadSongDto {

    private Long id;

    private String title;

    private String description;

    private Long creatorId;

    private Long albumId;

    private Instant createdAt;

}
