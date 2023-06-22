package com.telstra.codechallenge.controller;

import com.telstra.codechallenge.exceptions.CustomException;
import com.telstra.codechallenge.models.ErrorResponse;
import com.telstra.codechallenge.models.HottestRepoRequest;
import com.telstra.codechallenge.models.Repo;
import com.telstra.codechallenge.service.HottestRepoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HottestRepoControllerTest {

    @InjectMocks
    HottestRepoController hottestRepoController;

    @Mock
    HottestRepoService hottestRepoService;

    @Test
    public void testGetHottestRepoSuccess() {
        HottestRepoRequest hottestRepoRequest = new HottestRepoRequest();
        hottestRepoRequest.setNumberOfRepo(3);
        List<Repo> repoList = new ArrayList<>();
        Repo repo = new Repo();
        repo.setName("test");
        repoList.add(repo);
        when(hottestRepoService.getHottestRepoCreatedLastWeek(Mockito.anyInt())).thenReturn(repoList);
        ResponseEntity<?> response = hottestRepoController.getHottestRepo(hottestRepoRequest);
        assertNotNull(response);
        List<Repo> responseRepoList =  (List<Repo>) response.getBody();
        assertEquals("test", responseRepoList.get(0).getName());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testGetHottestRepoNullRequest() {

        ResponseEntity<?> response = hottestRepoController.getHottestRepo(null);
        assertNotNull(response);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Please provide a number of repo to receive", errorResponse.getMessage());
    }

    @Test
    public void testGetHottestRepoInvalidNumberOfRepo() {

        HottestRepoRequest hottestRepoRequest = new HottestRepoRequest();
        hottestRepoRequest.setNumberOfRepo(200);
        ResponseEntity<?> response = hottestRepoController.getHottestRepo(hottestRepoRequest);
        assertNotNull(response);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Number of repo must be between 1 and 100 (inclusive).", errorResponse.getMessage());
    }

    @Test
    public void testGetHottestRepoServiceError() {

        HottestRepoRequest hottestRepoRequest = new HottestRepoRequest();
        hottestRepoRequest.setNumberOfRepo(3);
        List<Repo> repoList = new ArrayList<>();
        Repo repo = new Repo();
        repo.setName("test");
        repoList.add(repo);
        when(hottestRepoService.getHottestRepoCreatedLastWeek(Mockito.anyInt())).thenThrow(new CustomException("Error", 400));
        ResponseEntity<?> response = hottestRepoController.getHottestRepo(hottestRepoRequest);
        assertNotNull(response);
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getStatusCode().value());
    }


}
