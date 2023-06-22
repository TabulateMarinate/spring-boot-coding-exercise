package com.telstra.codechallenge.service;

import com.telstra.codechallenge.client.HottestRepoClientWrapperWithRetry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HottestRepoServiceTest {

    @InjectMocks
    HottestRepoService hottestRepoService;

    @Mock
    HottestRepoClientWrapperWithRetry hottestRepoClientWrapperWithRetry;

    @Test
    public void getHottestRepoCreatedLastWeek() {
        hottestRepoService.getHottestRepoCreatedLastWeek(3);
    }

}
