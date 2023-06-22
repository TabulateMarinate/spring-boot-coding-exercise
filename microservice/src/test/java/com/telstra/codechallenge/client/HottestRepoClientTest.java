package com.telstra.codechallenge.client;

import com.telstra.codechallenge.exceptions.CustomException;
import com.telstra.codechallenge.models.HottestRepo;
import com.telstra.codechallenge.models.Repo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HottestRepoClientTest {

    @Autowired
    HottestRepoClient hottestRepoClient;

    @Value("${git.client.accessToken}")
    private String gitToken;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(hottestRepoClient, "gitToken", gitToken);
    }

    @Test
    public void getHottestRepoCreatedLastWeekSuccess() {

        int numberOfRepo = 3;
        HttpEntity<HottestRepo> response = hottestRepoClient.getHottestRepoCreatedLastWeek(numberOfRepo);
        assertEquals(numberOfRepo, response.getBody().getItems().size());
        List<Repo> responseRepoList = response.getBody().getItems();
        int prevWatchersCount = responseRepoList.get(0).getWatchers_count();
        for (int i = 1; i < numberOfRepo; i++) {
            assertTrue(prevWatchersCount > responseRepoList.get(i).getWatchers_count());
            prevWatchersCount = responseRepoList.get(i).getWatchers_count();
        }

    }

    @Test (expected = CustomException.class)
    public void getHottestRepoCreatedLastWeekFail() {
        ReflectionTestUtils.setField(hottestRepoClient, "gitToken", "invalidToken");
        int numberOfRepo = 3;
        ResponseEntity<HottestRepo> response = hottestRepoClient.getHottestRepoCreatedLastWeek(numberOfRepo);

    }
}
