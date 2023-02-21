package fesma.nl.Profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

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
        // ACT: GET all profiles
        ResponseEntity<String> response = restTemplate.getForEntity(HOST.get(), String.class);

        // CHECK: the return code
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testPostProfileShouldReturn200() {
        // ACT: POST a profile
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(HOST.get(), RECORD_1, String.class);

        // CHECK: the return code
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPostProfileShouldChangeData() {
        // ACT: Post a profile
        Profile actual = restTemplate
                .postForObject(HOST.get(), RECORD_1, Profile.class);

        // CHECK: if the returned Profile is equl to the posted profile and the EXISTING is set
        assertNotNull(actual.getId());
        assertEquals(RECORD_1.getTitle(), actual.getTitle());
        assertEquals(RECORD_1.getFunction(), actual.getFunction());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());

        // CHECK: is the database is updated using the get method
        actual = restTemplate
                .getForObject(HOST.get() + "/" + actual.getId(), Profile.class);
        assertEquals(RECORD_1.getTitle(), actual.getTitle());
        assertEquals(RECORD_1.getFunction(), actual.getFunction());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());
    }

    @Test
    public void testGetProfileShouldReturn200() {
        // PREPARE: Add a profile to get a EXISTING that we can get
        String EXISTING = postProfile(RECORD_1);
        
        // ACT: Get the profile
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/" + EXISTING, String.class);
        
        // CHECK: the return code
        assertEquals(200, responseEntity.getStatusCode().value());
    }


    @Test
    public void testGetNonExistingProfileShouldReturn404() {
        // ACT: Get the profile for a NON_EXISTING profile
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/" + NON_EXISTING, String.class);

        // CHECK: check the return code
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutProfileShouldReturn200() throws JsonProcessingException {
        // PREPARE: Add a profile to PUT
        String EXISTING = postProfile(RECORD_1);

        // ACT: Put the profile
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/" + EXISTING,
                PUT, getHttpEntity(RECORD_1), String.class);

        // CHECK: the return code
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutNonExistingProfileShouldReturn404() throws JsonProcessingException {
        // ACT: Put a NON-EXISTING profile
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/" + NON_EXISTING,
                PUT, getHttpEntity(RECORD_1), String.class);

        // CHECK: check the return code
        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutProfileShouldChangeData() throws JsonProcessingException, InterruptedException {
        // PREPARE: Add a profile to put
        String EXISTING = postProfile(RECORD_1);

        // ACT: Add an EXISTING
        Profile actual = restTemplate.exchange(
                HOST.get() + "/" + EXISTING,
                HttpMethod.PUT, getHttpEntity(RECORD_2), Profile.class).getBody();

        // CHECK: the returned Profile is equal to the input profile
        assertEquals(EXISTING, actual.getId());
        assertEquals(RECORD_2.getTitle(), actual.getTitle());
        assertEquals(RECORD_2.getFunction(), actual.getFunction());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());

        // CHECK: the database using get method
        Thread.sleep(200); // Make sure the db get time to process
        actual = restTemplate
                .getForObject(HOST.get() + "/" + EXISTING, Profile.class);
        assertEquals(RECORD_2.getTitle(), actual.getTitle());
        assertEquals(RECORD_2.getFunction(), actual.getFunction());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());
    }

    @Test
    public void testDeleteProfileShouldReturn200() {
        // PREPARE: Add a profile to get an id that we can delete
        String EXISTING = postProfile(RECORD_1);

        // ACT: Delete the profile
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(HOST.get() + "/" + EXISTING, DELETE, getHttpEntity(), String.class);

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

