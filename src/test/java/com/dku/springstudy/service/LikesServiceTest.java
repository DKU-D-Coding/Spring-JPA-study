package com.dku.springstudy.service;

import com.dku.springstudy.model.Items;
import com.dku.springstudy.repository.ItemsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class LikesServiceTest {
    @Autowired
    private LikesService likesService;
    @Autowired
    private ItemsRepository itemsRepository;
    @Test
    @Transactional
    public void 동시에_100개의_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                        try {
                            likesService.clickLikes("55aa6099b862ca33901862ca36fb60000",1L);
                        }
                        finally {
                            latch.countDown();
                        }
                    }
            );
        }
        latch.await();

        Items items = itemsRepository.findById(1L).get();
        assertThat(items.getTotalLikes()).isEqualTo(0);
    }
}