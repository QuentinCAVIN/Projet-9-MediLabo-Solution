package com.projet9.noteservice;

import com.projet9.noteservice.service.LoadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteServiceApplication implements CommandLineRunner {

    @Autowired
    private LoadDataService loader;

    public static void main(String[] args) {
        SpringApplication.run(NoteServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        loader.loadData();
    }
}