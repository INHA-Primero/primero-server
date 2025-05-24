package inha.primero_server.domain.auth.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class EmailSender {
    public void send(String to, String subject, String content) {
        System.out.println("[실제 메일 발송] 수신자: " + to + ", 제목: " + subject + ", 내용: " + content);
    }
}