package com.example.bajaj;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class BajajApplication implements CommandLineRunner {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public static void main(String[] args) {
        SpringApplication.run(BajajApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Request Body
        Map<String, Object> requestBody = Map.of(
                "name", "Palem Sri Chaitanya",
                "regNo", "22BCE9672",
                "email", "srichaitanyapalem05@gmail.com"
        );

        // URL
        String genUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        // Create WebClient
        WebClient client = webClientBuilder.build();

        // 🔥 SEND THE FIRST REQUEST
        System.out.println("Sending generateWebhook request...");

        Map<?, ?> genResponse = client.post()
                .uri(genUrl)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block(Duration.ofSeconds(30));

        // 🔥 PRINT FULL RAW RESPONSE (THIS IS WHAT WE NEED)
        System.out.println("FULL RESPONSE FROM SERVER:");
        System.out.println(genResponse);


    }
}
