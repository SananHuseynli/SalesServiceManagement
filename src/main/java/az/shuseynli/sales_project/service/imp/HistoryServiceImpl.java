package az.shuseynli.sales_project
.service.imp;

import az.shuseynli.sales_project
.model.entity.History;
import az.shuseynli.sales_project
.model.entity.Order;
import az.shuseynli.sales_project
.repository.HistoryRepository;
import az.shuseynli.sales_project
.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

   private final HistoryRepository historyRepository;

   @Override
   @Transactional
   public void saveHistory(Order order, String notes) {
      History history = new History();
      history.setOrder(order);
      history.setStatus(order.getCurrentStatus());
      history.setNotes(notes);

      historyRepository.save(history);
   }

}