package ru.ivmiit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ru.ivmiit")
public class AppConfig {
    @Bean
    public Message message(){
        return new HelloMessage("hi");
    }

    @Bean
    public MessageRenderer renderer(){
        return  new MessageRendererErrorImpl(message());
    }

}
