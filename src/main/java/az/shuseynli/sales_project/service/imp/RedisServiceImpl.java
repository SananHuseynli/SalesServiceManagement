package az.shuseynli.sales_project
.service.imp;

import az.shuseynli.sales_project
.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

   private final RedisTemplate<String, Object> redisTemplate;

   @Override
   public void save(String key, Object value) {
      redisTemplate.opsForValue().set(key, value);
   }

   @Override
   public void saveWithTimeout(String key, Object value, long timeoutHours) {
      redisTemplate.opsForValue().set(key, value, timeoutHours, TimeUnit.HOURS);
   }

   @Override
   public boolean exists(String key) {
      return Boolean.TRUE.equals(redisTemplate.hasKey(key));
   }

   @Override
   public Object get(String key) {
      return redisTemplate.opsForValue().get(key);
   }

   @Override
   public void delete(String key) {
      redisTemplate.delete(key);
   }

}