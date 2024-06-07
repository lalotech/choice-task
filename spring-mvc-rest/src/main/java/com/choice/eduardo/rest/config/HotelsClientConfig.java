package com.choice.eduardo.rest.config;

import com.choice.eduardo.rest.ws.client.HotelsWSClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@PropertySource("classpath:application.properties")
public class HotelsClientConfig {
    Logger log = Logger.getLogger(HotelsClientConfig.class);

    @Value("${ws.url}")
    private String wsurl;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.choice.eduardo.spring.soap.gen");
        return marshaller;
    }

    @Bean
    public HotelsWSClient hotelsWSClient(Jaxb2Marshaller marshaller) {
        log.info("build hotelsWSClient with wsurl: " + wsurl);
        HotelsWSClient client = new HotelsWSClient();
        client.setDefaultUri(wsurl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
