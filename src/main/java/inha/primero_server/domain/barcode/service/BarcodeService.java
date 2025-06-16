package inha.primero_server.domain.barcode.service;

import inha.primero_server.domain.barcode.entity.AuthLog;
import inha.primero_server.domain.barcode.entity.Barcode;
import inha.primero_server.domain.barcode.repository.AuthLogRepository;
import inha.primero_server.domain.barcode.repository.BarcodeRepository;
import inha.primero_server.domain.barcode.dto.response.AuthLogRes;
import inha.primero_server.domain.user.entity.User;
import inha.primero_server.global.common.error.CustomException;
import inha.primero_server.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BarcodeService {

    private final BarcodeRepository barcodeRepository;
    private final AuthLogRepository authLogRepository;
    private final String uploadDir = "uploads/auth_logs/";

    public void generateFor(User user) {
        String barcodeCode = generateUniqueBarcode(user.getUserId());

        if (barcodeRepository.findByBarcode(barcodeCode).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_OBJECT, "이미 존재하는 바코드입니다.");
        }

        Barcode barcode = new Barcode(user, barcodeCode);
        barcodeRepository.save(barcode);
    }

    private String generateUniqueBarcode(Long userId) {
        return "INHA" + String.format("%08d", userId);
    }

    public Long getUserIdByBarcode(String barcode) {
        return barcodeRepository.findByBarcode(barcode)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 사용자입니다."))
                .getUserId();
    }

    public void logRecyclingResult(Long userId, boolean success, String location, MultipartFile image) {
        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            try {
                String originalName = Optional.ofNullable(image.getOriginalFilename()).orElse("image.jpg");
                Path filePath = Path.of(uploadDir + System.currentTimeMillis() + "_" + originalName);
                Files.createDirectories(filePath.getParent());
                Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                imagePath = filePath.toString();
            } catch (IOException e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
        }

        AuthLog log = AuthLog.builder()
                .userId(userId)
                .success(success)
                .location(location)
                .timestamp(LocalDateTime.now())
                .imagePath(imagePath)
                .build();
        authLogRepository.save(log);
    }

    public List<AuthLogRes> getUserLogs(Long userId) {
        return authLogRepository.findByUserIdOrderByTimestampDesc(userId)
                .stream()
                .map(AuthLogRes::from)
                .collect(Collectors.toList());
    }
}
