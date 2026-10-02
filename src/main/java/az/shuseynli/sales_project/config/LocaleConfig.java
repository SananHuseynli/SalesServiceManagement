package az.shuseynli.sales_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Arrays;
import java.util.Locale;



@Configuration
public class LocaleConfig {

   @Bean
   public LocaleResolver localeResolver() {
      AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
      resolver.setSupportedLocales(Arrays.asList(
              Locale.of("az"),
              Locale.of("en"),
              Locale.of("ru")
      ));
      resolver.setDefaultLocale(Locale.of("az"));
      return resolver;
   }

   @Bean
   public MessageSource messageSource() {
      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
      messageSource.setBasename("messages");
      messageSource.setDefaultEncoding("UTF-8");
      messageSource.setUseCodeAsDefaultMessage(false);
      return messageSource;
   }

   @Bean
   public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
      LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
      bean.setValidationMessageSource(messageSource);
      return bean;
   }

}
