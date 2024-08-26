package com.coma.coma.post.mapper;

import com.coma.coma.post.dto.PostResponseDto;
import com.coma.coma.post.entity.Post;
import com.coma.coma.post.dto.PostRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(PostRequestDto postRequestDto);
    PostResponseDto toResponseDto(Post post);
}
