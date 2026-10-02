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

import java.math.BigDecimal;

@Component
@Slf4j
public class CreditCardPaymentStrategy implements PaymentStrategy {

   @Override
   public void process(Payment payment) {
      log.info("CreditCardPaymentStrategy başladı. Məbləğ: {}, Tranzaksiya ID: {}",
              payment.getAmount(), payment.getTransactionId());

      if (payment.getAmount().compareTo(new BigDecimal("10000")) > 0) {
         log.error("CreditCardPaymentStrategy uğursuz oldu: Bank limiti keçildi.");
         throw new RuntimeException("Card provider rejected: Insufficient daily limit.");
      }

      try {
         Thread.sleep(500);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }

      payment.setTransactionId(payment.getTransactionId() + "_CC_SUCCESS");
      log.info("CreditCardPaymentStrategy uğurla tamamlandı.");
   }

   @Override
   public PaymentMethod getSupportedMethod() {
      return PaymentMethod.CREDIT_CARD;
   }
}