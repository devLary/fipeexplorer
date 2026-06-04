package com.laryssa.fipeexplorer;

import com.laryssa.fipeexplorer.service.ConsumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FipeexplorerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipeexplorerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();

		consumoAPI.obterDados("https://parallelum.com.br/fipe/api/v1/carros/marcas");
		System.out.println(consumoAPI);

	}

}
