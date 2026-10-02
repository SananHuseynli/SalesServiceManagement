package az.shuseynli.sales_project
.service;

import az.shuseynli.sales_project
.model.entity.Order;

public interface HistoryService {
   void saveHistory(Order order, String notes);
}
