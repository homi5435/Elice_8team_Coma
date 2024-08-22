package com.coma.coma.post.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    private List<T> contents;    // 페이지 안에 들어갈 객체들
    private int number;         // 현재 페이지 번호
    private int size;           // 페이지 크기
    private int totalElements;  // 총 객체 수
    private int totalPages;      // 총 페이지 수
}
