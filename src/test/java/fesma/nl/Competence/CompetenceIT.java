package fesma.nl.Competence;

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

@SpringBootTest(classes = CompetenceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompetenceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper MAPPER = new ObjectMapper();
    Supplier<String> HOST = () -> String.format("http://localhost:%s/competence", port);
    HttpHeaders HEADERS = new HttpHeaders();

    String NON_EXISTING = "non-existing";

    Competence RECORD_1 = new Competence("JavaScript", "Developer");
    Competence RECORD_2 = new Competence( "C++", "Developer");

    @Test
    public void testGetAllCompetenceShouldReturn200() {
        ResponseEntity<String> response = restTemplate.getForEntity(HOST.get(),  String.class);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testPostCompetenceShouldReturn200() {
       ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(HOST.get(), RECORD_1, String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }
    @Test
    public void testPostCompetenceShouldChangeData() {
        Competence actual = restTemplate
                .postForObject(HOST.get(), RECORD_1, Competence.class);

        assertNotNull(actual.getId());
        assertEquals(RECORD_1.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());

        actual = restTemplate
                .getForObject(HOST.get() + "/" + actual.getId(), Competence.class);
        assertEquals(RECORD_1.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());
    }

    @Test
    public void testGetCompetenceShouldReturn200() {
        String id = postCompetence(RECORD_1);

        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/" + id, String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testGetNonExistingCompetenceShouldReturn404() {
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(HOST.get() + "/" + NON_EXISTING, String.class);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutCompetenceShouldReturn200() throws JsonProcessingException {
        String id = postCompetence(RECORD_1);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/" + id,
                PUT, getHttpEntity(RECORD_1), String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutNonExistingCompetenceShouldReturn404() throws JsonProcessingException {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                HOST.get() + "/" + NON_EXISTING,
                PUT, getHttpEntity(RECORD_1), String.class);

        assertEquals(404, responseEntity.getStatusCode().value());
    }

    @Test
    public void testPutCompetenceShouldChangeData() throws JsonProcessingException, InterruptedException {
        String EXISTING = postCompetence (RECORD_1);

        Competence actual = restTemplate.exchange(
                HOST.get() + "/" + EXISTING,
                HttpMethod.PUT, getHttpEntity(RECORD_2),Competence.class).getBody();

        assertEquals(EXISTING, actual.getId());
        assertEquals(RECORD_2.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());

        Thread.sleep(200);
        actual = restTemplate
                .getForObject(HOST.get() + "/"  + EXISTING, Competence.class);
        assertEquals(RECORD_2.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());
    }

    @Test
    public void testDeleteCompetenceShouldReturn200() {
        String id = postCompetence(RECORD_1);

        ResponseEntity<String> responseEntity = restTemplate
                .exchange(HOST.get() + "/" + id, DELETE, getHttpEntity(), String.class);

        assertEquals(200, responseEntity.getStatusCode().value());
    }

    private String postCompetence(Competence competence) {
        Competence actual = restTemplate
                .postForObject(HOST.get(), competence, Competence.class);
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

