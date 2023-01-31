package Profile;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProfileApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper MAPPER = new ObjectMapper();
    Supplier<String> HOST = () -> String.format("http://localhost:%s/profiles", port);
    HttpHeaders HEADERS = new HttpHeaders();

    Profile RECORD_1 = new Profile("Teacher ", "English", "England");
    Profile RECORD_2 = new Profile("Java developer", "Dutch", "America");

    @Test
    public void testGetAllProfilesShouldReturn200() {
        ResponseEntity<String> response = restTemplate.getForEntity(HOST.get(), String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DirtiesContext
    public void testPostProfileShouldReturn200() {
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(HOST.get(), RECORD_1, String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    @DirtiesContext
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
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/1", String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testGetNonExistingProfileShouldReturn404() {
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/99", String.class);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    @DirtiesContext
    public void testPutProfileShouldReturn200() throws JsonProcessingException {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/1",
                HttpMethod.PUT, getHttpEntity(RECORD_1), String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutNonExistingProfileShouldReturn404() throws JsonProcessingException {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/99",
                HttpMethod.PUT, getHttpEntity(RECORD_1), String.class);

        assertEquals(404, responseEntity.getStatusCode().value());
    }
    @Test
    @DirtiesContext
    public void testPutProfileShouldChangeData() throws JsonProcessingException {
        Profile actual = restTemplate.exchange(
                HOST.get() + "/1",
                HttpMethod.PUT, getHttpEntity(RECORD_2), Profile.class).getBody();

        assertEquals(1, actual.getId());
        assertEquals(RECORD_2.getTitle(), actual.getTitle());
        assertEquals(RECORD_2.getFunction(), actual.getFunction());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());

        actual = restTemplate
                .getForObject(HOST.get() + "/1", Profile.class);
        assertEquals(RECORD_2.getTitle(), actual.getTitle());
        assertEquals(RECORD_2.getFunction(), actual.getFunction());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());
    }

    @Test
    @DirtiesContext
    public void testDeleteProfileShouldReturn200() {

        ResponseEntity<String> responseEntity = restTemplate
                .exchange(HOST.get() + "/1", HttpMethod.DELETE, getHttpEntity(), String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
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
