package fesma.nl.Profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

@SpringBootTest(classes = ProfileApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper MAPPER = new ObjectMapper();
    Supplier<String> HOST = () -> String.format("http://localhost:%s/profiles", port);
    HttpHeaders HEADERS = new HttpHeaders();

    String NON_EXISTING = "non-existing";

    Profile RECORD_1 = new Profile("Teacher ", "English", "England");
    Profile RECORD_2 = new Profile("Java developer", "Dutch", "America");

    @Test
    public void testGetAllProfilesShouldReturn200() {
        ResponseEntity<String> response = restTemplate.getForEntity(HOST.get(), String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testPostProfileShouldReturn200() {
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(HOST.get(), RECORD_1, String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPostProfileShouldChangeData() {
        Profile actual = restTemplate
                .postForObject(HOST.get(), RECORD_1, Profile.class);

        assertNotNull(actual.getId());
        assertEquals(RECORD_1.getTitle(), actual.getTitle());
        assertEquals(RECORD_1.getFunction(), actual.getFunction());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());

        actual = restTemplate
                .getForObject(HOST.get() + "/" + actual.getId(), Profile.class);
        assertEquals(RECORD_1.getTitle(), actual.getTitle());
        assertEquals(RECORD_1.getFunction(), actual.getFunction());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());
    }

    @Test
    public void testGetProfileShouldReturn200() {
        String id = postProfile(RECORD_1);

        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/" + id, String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }


    @Test
    public void testGetNonExistingProfileShouldReturn404() {
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/" + NON_EXISTING, String.class);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutProfileShouldReturn200() throws JsonProcessingException {
        String id = postProfile(RECORD_1);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/" + id,
                PUT, getHttpEntity(RECORD_1), String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutNonExistingProfileShouldReturn404() throws JsonProcessingException {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/" + NON_EXISTING,
                PUT, getHttpEntity(RECORD_1), String.class);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutProfileShouldChangeData() throws JsonProcessingException {
        String id = postProfile(RECORD_1);

        Profile actual = restTemplate.exchange(
                HOST.get() + "/" + id,
                HttpMethod.PUT, getHttpEntity(RECORD_2), Profile.class).getBody();

        assertNotNull(actual.getId());
        assertEquals(RECORD_2.getTitle(), actual.getTitle());
        assertEquals(RECORD_2.getFunction(), actual.getFunction());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());

        actual = restTemplate
                .getForObject(HOST.get() + "/" + actual.getId(), Profile.class);
        assertEquals(RECORD_2.getTitle(), actual.getTitle());
        assertEquals(RECORD_2.getFunction(), actual.getFunction());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());
    }

    @Test
    public void testDeleteProfileShouldReturn200() {
        // PREPARE: Add a profile to get a id that we can delete
        String id = postProfile(RECORD_1);

        // ACT: Delete the profile
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(HOST.get() + "/" + id, DELETE, getHttpEntity(), String.class);

        // CHECK: check the return code
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    private String postProfile(Profile profile) {
        Profile actual = restTemplate
                .postForObject(HOST.get(), profile, Profile.class);
        return actual.getId();
    }

    private HttpEntity<String> getHttpEntity(Object value) throws JsonProcessingException {
        HEADERS.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = MAPPER.writeValueAsString(value);
        return new HttpEntity<>(requestBody, HEADERS);
    }

    private HttpEntity<String> getHttpEntity() {
        HEADERS.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(HEADERS);
    }
}

