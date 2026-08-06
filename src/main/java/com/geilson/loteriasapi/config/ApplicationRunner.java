package com.geilson.loteriasapi.config;

import com.geilson.loteriasapi.consumer.LoteriasUpdate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
@Profile("!test")
public class ApplicationRunner implements CommandLineRunner {

    private final LoteriasUpdate loteriasUpdate;

    public ApplicationRunner(LoteriasUpdate loteriasUpdate) {
        this.loteriasUpdate = loteriasUpdate;
    }

    @Override
    public void run(String... args) {
        loteriasUpdate.checkForUpdates();
    }
}