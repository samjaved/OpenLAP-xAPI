package de.ude.openlap.xapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

/**
 * The MongoConfig class is the configuration class which is reading environment
 * variables databasename,host and port from mongo.properties file to create
 * MongoClient
 * 
 * @author Sammar Javed
 * @version 1.0
 */
@Configuration
@EnableMongoRepositories(basePackages = "de.ude.openlap.xapi.repo")
@PropertySource("classpath:mongo.properties")
public class MongoConfig extends AbstractMongoConfiguration {
	@Autowired
	private Environment env;

	@Override
	protected String getDatabaseName() {
		return env.getProperty("mongo.database");
	}

	@Override
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		return new MongoClient(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port")));
	}

}
