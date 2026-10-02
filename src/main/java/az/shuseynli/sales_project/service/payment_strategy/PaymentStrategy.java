package az.shuseynli.sales_project
.service.payment_strategy;

import az.shuseynli.sales_project
.model.entity.Payment;
import az.shuseynli.sales_project
.model.enums.PaymentMethod;

public interface PaymentStrategy {
   void process(Payment payment);
   PaymentMethod getSupportedMethod();
}