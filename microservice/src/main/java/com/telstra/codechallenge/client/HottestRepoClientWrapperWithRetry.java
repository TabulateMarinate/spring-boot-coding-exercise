package com.telstra.codechallenge.client;

import com.telstra.codechallenge.exceptions.CustomException;
import com.telstra.codechallenge.models.HottestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class HottestRepoClientWrapperWithRetry {

    @Autowired
    private HottestRepoClient hottestRepoClient;

    @Retryable(value = {CustomException.class},
            maxAttempts = 3, backoff = @Backoff(delay = 500))
    public ResponseEntity<HottestRepo> getHottestRepoCreatedLastWeek(int numberOfRepo) {

        ResponseEntity<HottestRepo> response = null;

        try {
            response = hottestRepoClient.getHottestRepoCreatedLastWeek(numberOfRepo);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage(), e.getErrorCode());
        }

        return response;
    }
}
