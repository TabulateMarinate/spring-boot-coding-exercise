package com.telstra.codechallenge.service;

import com.telstra.codechallenge.client.HottestRepoClient;
import com.telstra.codechallenge.client.HottestRepoClientWrapperWithRetry;
import com.telstra.codechallenge.exceptions.CustomException;
import com.telstra.codechallenge.models.HottestRepo;
import com.telstra.codechallenge.models.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HottestRepoService {
    @Autowired
    private HottestRepoClientWrapperWithRetry hottestRepoClientWrapperWithRetry;

    public List<Repo> getHottestRepoCreatedLastWeek(int numberOfRepo) throws CustomException {

        List<Repo> repoList = null;

        try {
            ResponseEntity<HottestRepo> response = hottestRepoClientWrapperWithRetry.getHottestRepoCreatedLastWeek(numberOfRepo);
            if (response != null) {
                repoList = response.getBody().getItems();
            }
        } catch (CustomException customException) {
            throw new CustomException(customException.getMessage(), customException.getErrorCode());
        }



        return repoList;
    }
}
