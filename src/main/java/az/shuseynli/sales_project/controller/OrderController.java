package az.shuseynli.sales_project.controller;
import az.shuseynli.sales_project
.model.dto.order.*;
import az.shuseynli.sales_project
.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

   private final OrderService orderService;

   @PostMapping("/online")
   public ResponseEntity<OrderResponse> createOnlineOrder(@Valid @RequestBody OnlineOrderRequest request) {
      OrderResponse response = orderService.createOnlineOrder(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @PostMapping("/offline")
   public ResponseEntity<OrderResponse> createOfflineOrder(@Valid @RequestBody OfflineOrderRequest request) {
      OrderResponse response = orderService.createOfflineOrder(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

   @GetMapping("/{id}")
   public ResponseEntity<OrderFullResponse> getOrderById(@PathVariable Long id) {
      return ResponseEntity.ok(orderService.getOrderById(id));
   }

   @PatchMapping("/{id}/cancel")
   public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
      orderService.cancelOrder(id);
      return ResponseEntity.noContent().build();
   }

   @PatchMapping("/{id}/ship")
   public ResponseEntity<Void> shipOrder(@PathVariable Long id) {
      orderService.shipOrder(id);
      return ResponseEntity.noContent().build();
   }

   @PatchMapping("/{id}/complete")
   public ResponseEntity<Void> completeOrder(@PathVariable Long id) {
      orderService.completeOrder(id);
      return ResponseEntity.noContent().build();
   }

   @GetMapping("/search")
   public ResponseEntity<Page<OrderResponse>> searchOrders(@ModelAttribute SearchOrder filter) {
      Page<OrderResponse> response = orderService.searchOrders(filter);
      return ResponseEntity.ok(response);
   }

}