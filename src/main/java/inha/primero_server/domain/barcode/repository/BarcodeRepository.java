package inha.primero_server.domain.barcode.repository;

import inha.primero_server.domain.barcode.entity.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
    Optional<Barcode> findByBarcode(String barcode);
}