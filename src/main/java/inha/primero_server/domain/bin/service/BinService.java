package inha.primero_server.domain.bin.service;

import inha.primero_server.domain.bin.entity.Bin;
import inha.primero_server.domain.bin.repository.BinRepository;
import inha.primero_server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BinService {

    private final BinRepository binRepository;

    @Bean
    public CommandLineRunner createTestBins() {
        return args -> {
            // 첫 번째 Bin 데이터
            String location1 = "5호관 남쪽 2층";
            double residualCapacity1 = 5.5;
            boolean status1 = true;
            LocalDateTime installedDate1 = LocalDateTime.now();

            // 두 번째 Bin 데이터
            String location2 = "하이테크관 저층부 6층";
            double residualCapacity2 = 5.5;
            boolean status2 = true;
            LocalDateTime installedDate2 = LocalDateTime.now();

            // 첫 번째 Bin이 존재하는지 확인하고, 없으면 새로 생성
            if (binRepository.findByLocation(location1).isEmpty()) {
                Bin bin1 = new Bin();
                bin1.setLocation(location1);
                bin1.setResidualCapacity(residualCapacity1);
                bin1.setStatus(status1);
                bin1.setInstalledDate(installedDate1);

                binRepository.save(bin1); // Bin 저장
                System.out.println("Bin 1 생성 완료: " + location1);
            } else {
                System.out.println("Bin 1 이미 존재: " + location1);
            }

            // 두 번째 Bin이 존재하는지 확인하고, 없으면 새로 생성
            if (binRepository.findByLocation(location2).isEmpty()) {
                Bin bin2 = new Bin();
                bin2.setLocation(location2);
                bin2.setResidualCapacity(residualCapacity2);
                bin2.setStatus(status2);
                bin2.setInstalledDate(installedDate2);

                binRepository.save(bin2); // Bin 저장
                System.out.println("Bin 2 생성 완료: " + location2);
            } else {
                System.out.println("Bin 2 이미 존재: " + location2);
            }
        };
    }
}
