package com.clockworkjava.batchrent;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public EntityManagerFactory emf;

    @Bean
    public Job basicJob(Step step1) {
        return jobBuilderFactory
                .get("basicjob")
                .flow(step1)
                .end()
                .build();
    }


    @Bean
    public LineProcessor processor() {
        return new LineProcessor();
    }

    @Bean
    public Step step1(FlatFileItemWriter<String> writer) {
        return stepBuilderFactory.get("step1")
                .<String,String>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

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

    @Bean
    public FlatFileItemReader<Car> carCsvReader() {
        return new FlatFileItemReaderBuilder<Car>()
                .name("carCsvReader")
                .resource(new FileSystemResource("input/johns.csv"))
                .delimited()
                .delimiter(",")
                .names(new String[]{"make","model","rentingCostPerHour"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Car>(){{
                    setTargetType(Car.class);
                }})
                .build();
    }

    public JpaItemWriter<Car> carDbWriter() {
        JpaItemWriter<Car> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }
}
