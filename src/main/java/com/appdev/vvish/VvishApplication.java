package com.appdev.vvish;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VvishApplication {

	private static final Logger LOG = LoggerFactory.getLogger(VvishApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(VvishApplication.class, args);

		checkAndCreateDir("./tmp");
		checkAndCreateDir("./surprise_media");
	}

	public static void checkAndCreateDir(String dirName) {

		File directory = new File(dirName);
		if (!directory.exists()) {
			directory.mkdirs();
			LOG.info("Created " + dirName + " directory..");
		}

		LOG.info(dirName + " directory already exists.");
	}
}
