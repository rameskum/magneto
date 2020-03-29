package com.demo.magneto.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:security.properties")
@PropertySource("classpath:env.properties")
@EnableTransactionManagement
@EnableBatchProcessing
public class ApplicationConfiguration {

	/*@Bean
	Job job(JobBuilderFactory jbf, StepBuilderFactory sbf, Step1Configuration step1Configuration, Step2Configuration seStep2Configuration) {

		Step s1 = sbf.get("file-to-db")
				.<Person, Person>chunk(100)
				.reader(step1Configuration.fileReader(null))
				.processor((Function<Person, Person>) person -> {
					person.setName(person.getName().toUpperCase());
					person.setEmail(person.getEmail().toUpperCase());
					return person;
				})
				.writer(step1Configuration.jdbcWriter(null))
				.build();

		Step s2 = sbf.get("db-file")
				.<Map<Integer, Integer>, Map<Integer, Integer>>chunk(100)
				.reader(seStep2Configuration.jdbcReader(null))
				.writer(seStep2Configuration.fileWriter(null))
				.build();

		return jbf.get("etl")
				.incrementer(new RunIdIncrementer())
				.start(s1)
				.next(s1)
				.next(s2)
				.build();
	}

	@Configuration
	public static class Step2Configuration {

		@Bean
		ItemReader<Map<Integer, Integer>> jdbcReader(DataSource dataSource) {
			return new JdbcCursorItemReaderBuilder<Map<Integer, Integer>>()
					.dataSource(dataSource)
					.name("jdbc-reader")
					.sql("select count(age) c, age a from people group by age")
					.rowMapper((resultSet, i) -> Collections.singletonMap(resultSet.getInt("a"), resultSet.getInt("c")))
					.build();
		}

		@Bean
		ItemWriter<Map<Integer, Integer>> fileWriter(@Value("${output}") Resource resource) {
			return new FlatFileItemWriterBuilder<Map<Integer, Integer>>()
					.name("file-writer")
					.resource(resource)
					.lineAggregator(new DelimitedLineAggregator<Map<Integer, Integer>>() {
						{
							setDelimiter(",");
							setFieldExtractor(integerIntegerMap -> {
								Entry<Integer, Integer> next = integerIntegerMap.entrySet().iterator().next();
								return new Object[]{next.getKey(), next.getValue()};
							});
						}
					})
					.build();
		}
	}

	@Configuration
	public static class Step1Configuration {
		@Bean
		FlatFileItemReader<Person> fileReader(@Value("${input}") Resource resource) {
			return new FlatFileItemReaderBuilder<Person>()
					.name("file-reader")
					.resource(resource)
					.targetType(Person.class)
					.delimited().delimiter(",").names("name", "age", "email")
					.build();
		}

		@Bean
		JdbcBatchItemWriter<Person> jdbcWriter(DataSource dataSource) {
			return new JdbcBatchItemWriterBuilder<Person>()
					.dataSource(dataSource)
					.sql("insert into people (age, name, email) values (:age, :name , :email)")
					.beanMapped()
					.build();
		}
	}

	@Data
	public static class Person {
		private String name;
		private int age;
		private String email;
	}*/
}
