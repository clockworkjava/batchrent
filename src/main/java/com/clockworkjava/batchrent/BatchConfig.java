package com.clockworkjava.batchrent;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {


    @Bean
    public FlatFileItemReader<String> reader() {
        return new FlatFileItemReaderBuilder<String>()
                .name("lineReader")
                .resource(new FileSystemResource("input/lines.txt"))
                .lineMapper(new PassThroughLineMapper())
                .build();
    }

    @Bean
    public FlatFileItemWriter<String> writer() {
        return new FlatFileItemWriterBuilder<String>()
                .name("lineWriter")
                .resource(new FileSystemResource("output/upppercasedLines.txt"))
                .lineAggregator(new PassThroughLineAggregator<>())
                .build();
    }
}
