package az.shuseynli.sales_project
.service.payment_strategy.impl;

import az.shuseynli.sales_project
.model.entity.Payment;
import az.shuseynli.sales_project
.model.enums.PaymentMethod;
import az.shuseynli.sales_project
.service.payment_strategy.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankTransferPaymentStrategy implements PaymentStrategy {

   @Override
   public void process(Payment payment) {
      log.info("BankTransferPaymentStrategy başladı. Hesab faktura yoxlanılır. Tranzaksiya: {}", payment.getTransactionId());

      if (payment.getTransactionId() == null || payment.getTransactionId().isBlank()) {
         throw new IllegalArgumentException("Bank transfer requires a valid transaction reference.");
      }

      payment.setTransactionId(payment.getTransactionId() + "_BANK_VERIFIED");
      log.info("BankTransferPaymentStrategy: Hesablaşma təsdiqləndi.");
   }

   @Override
   public PaymentMethod getSupportedMethod() {
      return PaymentMethod.BANK_TRANSFER;
   }
}