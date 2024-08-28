package com.coma.coma.common.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    // 이미지 업로드 메서드
    String uploadFile(MultipartFile file, String username);

    // 이미지 다운로드 메서드
    byte[] downloadFile(String fileName);
    // 이미지 삭제 메서드
    void deleteFile(String fileName);
}
