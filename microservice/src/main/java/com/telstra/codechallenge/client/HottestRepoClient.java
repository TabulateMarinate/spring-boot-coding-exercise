package com.telstra.codechallenge.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telstra.codechallenge.exceptions.CustomException;
import com.telstra.codechallenge.models.HottestRepo;
import com.telstra.codechallenge.models.Repo;
import com.telstra.codechallenge.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class HottestRepoClient {

    @Value("${git.client.accessToken}")
    private String gitToken;

    @Autowired
    ObjectMapper objectMapper;

    public ResponseEntity<HottestRepo> getHottestRepoCreatedLastWeek(int numberOfRepo) throws CustomException{

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString(Constants.HOTTEST_URL)
                .queryParam("q", "Q")
                .queryParam("sort", "stars")
                .queryParam("order", "desc")
                .queryParam("per_page", numberOfRepo)
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, Constants.MEDIA_TYPE_GIT_JSON);
        headers.set(Constants.AUTHORIZATION, "Bearer " + gitToken);
        headers.set(Constants.X_GITHUB_API_VERSION, "2022-11-28");
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<HottestRepo> response = restTemplate.exchange(uri, HttpMethod.GET, entity, HottestRepo.class);

            return response;
        } catch (HttpStatusCodeException httpStatusCodeException) {
            throw new CustomException("Error while calling a downstream API", httpStatusCodeException.getStatusCode().value());
        } catch (Exception e) {
            throw new CustomException("Error while calling a downstream API", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
}
