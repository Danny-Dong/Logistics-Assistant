package com.dong.logistics.assistant;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class LogisticsAssistantApplication {

    public static void main(String[] args) throws IOException {

        // 创建资源解析器
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        try {
            // 扫描 resources 目录下的所有文件
            Resource[] resources = resolver.getResources("classpath*:**");

            // 打印每个文件的路径
            for (Resource resource : resources) {
                System.out.println("Found resource: " + resource.getURL());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to scan resources.");
        }

        SpringApplication.run(LogisticsAssistantApplication.class, args);
    }

    @Bean
    CommandLineRunner ingestTermOfServiceToVectorStore(EmbeddingModel embeddingModel, VectorStore vectorStore,
                                                       @Value("classpath:rag/terms-of-service.txt") Resource termsOfServiceDocs) {
        return args -> {
            // Ingest the document into the vector store
            vectorStore.write(                                  // 3.写入
                    new TokenTextSplitter().transform(          // 2.转换
                            new TextReader(termsOfServiceDocs).read())  // 1.读取
            );

        };
    }

    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }
}
