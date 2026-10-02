package az.shuseynli.sales_project.controller;

import az.shuseynli.sales_project
.model.dto.payment.PaymentRequest;
import az.shuseynli.sales_project
.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

   private final PaymentService paymentService;

   @PostMapping
   public ResponseEntity<Void> processPayment(@Valid @RequestBody PaymentRequest request) {
      paymentService.executePayment(request);
      return ResponseEntity.noContent().build();
   }
}