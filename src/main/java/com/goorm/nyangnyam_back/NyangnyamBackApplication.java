package com.goorm.nyangnyam_back;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.catalina.Server;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NyangnyamBackApplication {

	public static void main(String[] args) {
		String connectionString = "mongodb+srv://testuser:1234@test.9qgbvz3.mongodb.net/?retryWrites=true&w=majority&appName=test";

		ServerApi serverApi = ServerApi.builder()
				.version(ServerApiVersion.V1)
				.build();

		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString((connectionString)))
				.serverApi((serverApi))
				.build();

		try (MongoClient mongoClient = MongoClients.create(settings)) {
			try {
				MongoDatabase database = mongoClient.getDatabase("admin");
				database.runCommand(new Document("ping", 1));
				System.out.println("Pinged your development. Your Successfully connect to Mongodb");
			} catch (MongoException e) {
				e.printStackTrace();
			}
		}


		SpringApplication.run(NyangnyamBackApplication.class, args);
	}

}
