package az.shuseynli.sales_project
.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableCheckUtil {

   public static Pageable getPageable(Integer page, Integer size) {
      int resolvedPage = (page == null || page < 0)
              ? 0
              : page;

      int resolvedSize = (size == null || size < 1)
              ? 10
              : size;

      if (resolvedSize > 100) {
         resolvedSize = 100;
      }

      return PageRequest.of(resolvedPage, resolvedSize);
   }

}
