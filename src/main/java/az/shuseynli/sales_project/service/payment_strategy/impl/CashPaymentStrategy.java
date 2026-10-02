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
public class CashPaymentStrategy implements PaymentStrategy {

   @Override
   public void process(Payment payment) {
      log.info("CashPaymentStrategy başladı. Filial: {}, Kassa: {}",
              payment.getBranchId(), payment.getCashRegisterId());

      if (payment.getBranchId() == null || payment.getCashRegisterId() == null) {
         log.error("CashPaymentStrategy xətası: Filial və ya Kassa məlumatı əskikdir.");
         throw new IllegalStateException("Offline cash payment requires branch and register identification.");
      }

      log.info("CashPaymentStrategy: Nəqd vəsait kassaya daxil edildi. Məbləğ: {}", payment.getAmount());
   }

   @Override
   public PaymentMethod getSupportedMethod() {
      return PaymentMethod.CASH;
   }

}