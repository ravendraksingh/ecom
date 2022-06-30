package com.rks.mcommon.utility;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommonIdGenerator {

    public String generate() throws InterruptedException {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        IdGenerator idGenerator = instance.getIdGenerator("newId");

        while (true) {
            Long id = idGenerator.newId();
            System.out.println("Id: " + id);
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runnable job1 = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                CommonIdGenerator generator = new CommonIdGenerator();
                generator.generate();
            }
        };
        Runnable job2 = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                CommonIdGenerator generator = new CommonIdGenerator();
                generator.generate();
            }
        };
        Runnable job3 = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                CommonIdGenerator generator = new CommonIdGenerator();
                generator.generate();
            }
        };
        executorService.execute(job1);
        executorService.execute(job2);
        executorService.execute(job3);
    }
}
