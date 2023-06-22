package com.telstra.codechallenge.controller;

import com.telstra.codechallenge.exceptions.CustomException;
import com.telstra.codechallenge.helloworld.HelloWorld;
import com.telstra.codechallenge.models.ErrorResponse;
import com.telstra.codechallenge.models.HottestRepoRequest;
import com.telstra.codechallenge.models.Repo;
import com.telstra.codechallenge.service.HottestRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HottestRepoController {

    @Autowired
    private HottestRepoService hottestRepoService;
    @GetMapping(path = "/hottestRepo")
    public ResponseEntity<?> getHottestRepo(@RequestBody(required = false) HottestRepoRequest hottestRepoRequest) {

        List<Repo> response = null;
        ResponseEntity<ErrorResponse> verifyResponse = verifyHottestRepoRequest(hottestRepoRequest);

        if (verifyResponse != null) {
            return verifyResponse;
        }


        try {
            response = hottestRepoService.getHottestRepoCreatedLastWeek(hottestRepoRequest.getNumberOfRepo());
        } catch (CustomException customException) {
            ErrorResponse errorResponse = new ErrorResponse(customException.getErrorCode(), customException.getMessage());
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
        }



        return new ResponseEntity<List<Repo>>(response, HttpStatus.OK);
    }

    private ResponseEntity<ErrorResponse> verifyHottestRepoRequest(HottestRepoRequest hottestRepoRequest) {
        if (hottestRepoRequest == null || hottestRepoRequest.getNumberOfRepo() == null) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Please provide a number of repo to receive");
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        } else if (hottestRepoRequest.getNumberOfRepo() < 1 || hottestRepoRequest.getNumberOfRepo() > 100) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Number of repo must be between 1 and 100 (inclusive).");
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
