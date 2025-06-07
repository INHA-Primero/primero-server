package inha.primero_server.domain.util.storage;

import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import inha.primero_server.domain.util.storage.dto.StorageUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class StorageProvider {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}") // application.yml 에 써둔 bucket 이름
    private String bucketName;

    public String multipartFileUpload(MultipartFile file, StorageUploadRequest storageUploadRequest){
        String uuid = UUID.randomUUID().toString(); // Cloud Storage 에 저장할 이름
        String fileName = storageUploadRequest.dirName() + "/" + storageUploadRequest.id() + "/" + uuid;
        String ext = file.getContentType(); //파일 형식 ex) JPG

        // Cloud 에 이미지 업로드
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo =
                BlobInfo.newBuilder(blobId)
                        .setContentType(ext)
                        .build();
        try(WriteChannel writer = storage.writer(blobInfo)){
            byte[] imageData = file.getBytes();
            writer.write(ByteBuffer.wrap(imageData));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }

    public void multipartFileDelete(String fileName){
        Blob blob = storage.get(bucketName, fileName);
        if (blob == null) {
            System.out.println("The file " + fileName + " wasn't found in "  + bucketName);
            return;
        }

        Storage.BlobSourceOption precondition =
                Storage.BlobSourceOption.generationMatch(blob.getGeneration());

        storage.delete(bucketName, fileName, precondition);
    }
}
