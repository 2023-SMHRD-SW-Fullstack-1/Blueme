package com.blueme.backend.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileStorageUtil {
	private final String STORAGE_FOLDER = "/usr/blueme/musics/";

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비었습니다.");
        }

        try {
            byte[] bytes = file.getBytes();

            // 각 파일에 고유한 이름을 부여하기 위해 UUID를 사용.
            String fileNameWithUUID = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Path path = Paths.get(STORAGE_FOLDER + fileNameWithUUID);

            Files.write(path, bytes);

            return path.toString(); // 파일 경로 반환

        } catch (IOException e) {
            throw new RuntimeException("Failed to store the file", e);
        }
    }
}
