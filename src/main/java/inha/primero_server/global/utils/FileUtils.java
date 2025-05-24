package inha.primero_server.global.utils;

import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtils {
    @Value("${file.upload.path}")
    private String uploadPath;

    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT, "파일이 비어있습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String savedFileName = UUID.randomUUID().toString() + extension;
        String savedFilePath = uploadPath + savedFileName;

        try {
            file.transferTo(new File(savedFilePath));
            return savedFileName;
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR, "파일 업로드에 실패했습니다.");
        }
    }

    public void deleteImage(String fileName) {
        File file = new File(uploadPath + fileName);
        if (file.exists() && !file.delete()) {
            throw new CustomException(ErrorCode.INTERNAL_ERROR, "파일 삭제에 실패했습니다.");
        }
    }
} 