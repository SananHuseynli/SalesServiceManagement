package az.shuseynli.sales_project
.service;

public interface RedisService {
   void save(String key, Object value);
   void saveWithTimeout(String key, Object value, long timeoutHours);
   boolean exists(String key);
   Object get(String key);
   void delete(String key);
}