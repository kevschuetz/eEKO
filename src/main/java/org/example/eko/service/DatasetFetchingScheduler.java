package org.example.eko.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class DatasetFetchingScheduler {

    public DatasetFetchingScheduler(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(fetchDataset(), 1, 24, TimeUnit.HOURS);
    }

    private Runnable fetchDataset(){
        return () -> {
        };

    }
}
