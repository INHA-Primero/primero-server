package inha.primero_server.global.common.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class CloudStorageConfig {
    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;
    @Value("${gcp.key}")
    private Resource gcpKey;

    @Bean
    public Storage storage() throws IOException {
        return StorageOptions.newBuilder()
                .setCredentials(
                        ServiceAccountCredentials.fromStream(gcpKey.getInputStream())
                )
                .setProjectId(projectId)
                .build()
                .getService();
    }
}
