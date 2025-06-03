package com.agustin.literalura;

import com.agustin.literalura.book.api.BookApiDTO;
import com.agustin.literalura.book.api.BookApi;
import com.agustin.literalura.book.api.GutendexApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

    }
}
