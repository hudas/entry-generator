package org.ignas.datagenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DataGeneratorApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(DataGeneratorApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(DataGeneratorApplication.class, args);
	}

	@Autowired
	private BehaviourGenerator customerBehaviourGenerator;

	@Override
	public void run(String... args) {
		LOG.debug("Starting publishing!");

		for (int i = 0; i < 2000; i++) {
			customerBehaviourGenerator.generateDebtor(i + "");
            LOG.debug("Debtor published ! " + i);
        }
	}
}
