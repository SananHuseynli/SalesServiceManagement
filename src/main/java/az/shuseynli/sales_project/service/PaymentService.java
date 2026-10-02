package az.shuseynli.sales_project
.service;

import az.shuseynli.sales_project
.model.dto.payment.PaymentRequest;

public interface PaymentService {
   void executePayment(PaymentRequest request);
}