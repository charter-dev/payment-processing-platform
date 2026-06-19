package payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payment.entity.PaymentTransaction;

@Repository
public interface PaymentRepository
extends JpaRepository<PaymentTransaction,Long> {

    Optional<PaymentTransaction>
    findByTrxId(String trxId);

}
