package com.clockworkjava.batchrent;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
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
import javax.sql.DataSource;

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
    public Job basicJob(Step step1, Step step2, Step step3) {
        return jobBuilderFactory
                .get("basicjob")
                .flow(step1)
                .next(step2)
                .next(step3)
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
                .delimiter("#")
                .names(new String[]{"make","model","rentingCostPerHour"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Car>(){{
                    setTargetType(Car.class);
                }})
                .linesToSkip(1)
                .build();
    }

    @Bean
    public JpaItemWriter<Car> carDbWriter() {
        JpaItemWriter<Car> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    @Bean
    public JeepFilter jeepFilter() {
        return new JeepFilter();
    }

    @Bean
    public Step step2(JpaItemWriter<Car> writer) {
        return stepBuilderFactory.get("step2")
                .<Car,Car>chunk(10)
                .reader(carCsvReader())
                .processor(jeepFilter())
                .writer(writer)
                .build();
    }

    @Bean
    public Step step3(JpaItemWriter<Car> writer, DataSource dataSource) {
        return stepBuilderFactory.get("step3")
                .<Car,Car>chunk(10)
                .reader(dbReader(dataSource))
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Car> dbReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Car>()
                .name("dbReader")
                .dataSource(dataSource)
                .sql("SELECT make, model, cost FROM sunny_ride_rent")
                .rowMapper(new CarRowMapper())
                .build();
    }
}
