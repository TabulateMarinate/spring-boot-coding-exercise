package com.telstra.codechallenge.client;

import com.telstra.codechallenge.exceptions.CustomException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
@EnableRetry
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HottestRepoClientWrapperWithRetry.class})
public class HottestRepoClientWrapperWithRetryTest {

    @Autowired
    HottestRepoClientWrapperWithRetry hottestRepoClientWrapperWithRetry;

    @MockBean
    HottestRepoClient hottestRepoClient;

    @Test
    public void getHottestRepoCreatedLastWeek() {
        when(hottestRepoClient.getHottestRepoCreatedLastWeek(Mockito.anyInt())).thenThrow(new CustomException(null, 400));
        try {
            hottestRepoClientWrapperWithRetry.getHottestRepoCreatedLastWeek(3);
        } catch (CustomException e) {
            verify(hottestRepoClient, times(3)).getHottestRepoCreatedLastWeek(Mockito.anyInt());
        }
    }

}
