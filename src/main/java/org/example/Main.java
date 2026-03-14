package org.example;

import org.example.infrastructure.GptImage;
import org.example.infrastructure.S3ClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
        System.getenv().forEach((key, value) -> {
            System.out.printf("%s = %s%n", key, value);
        });
        GptImage gptImage = new GptImage(S3ClientFactory.transferManager);
        System.out.println("現在の作業ディレクトリ: " + System.getProperty("user.dir"));
    }
}
