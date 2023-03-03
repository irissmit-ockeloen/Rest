package fesma.nl.Competence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.*;

@SpringBootTest(classes = CompetenceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompetenceIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static String NON_EXISTING = "non-existing";
    static Competence RECORD_1 = new Competence("Java", "developer");
    static Competence RECORD_2 = new Competence("C++", "developer");

    @Test
    public void getAllReturn200() throws JsonProcessingException {
        int actual = exchangeForStatusCode(url(), GET);

        assertEquals(200, actual);
    }

    @Test
    public void getExistingReturn200() throws JsonProcessingException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        int actual = exchangeForStatusCode(url(id), GET);

        assertEquals(200, actual);
    }

    @Test
    public void getNonExistingReturn404() throws JsonProcessingException {
        int actual = exchangeForStatusCode(url(NON_EXISTING), GET);

        assertEquals(404, actual);
    }

    @Test
    public void getReturnCorrectObject() throws JsonProcessingException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        Competence actual = exchangeForObject(url(id), GET);

        assertEquals(id, actual.getId());
        assertEquals(RECORD_1.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());
    }

    @Test
    public void postReturn200() throws JsonProcessingException {
        int actual = exchangeForStatusCode(url(), POST, RECORD_1);

        assertEquals(200, actual);
    }

    @Test
    public void postReturnCorrectObject() throws JsonProcessingException {
        Competence actual = exchangeForObject(url(), POST, RECORD_1);

        assertNotNull(actual.getId());
        assertEquals(RECORD_1.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());
    }

    @Test
    public void postAdaptDatabase() throws JsonProcessingException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        Competence actual = exchangeForObject(url(id), GET);
        assertEquals(RECORD_1.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());
    }

    @Test
    public void putExistingReturn200() throws JsonProcessingException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        int actual = exchangeForStatusCode(url(id), PUT, RECORD_1);

        assertEquals(200, actual);
    }

    @Test
    public void putNonExistingReturn404() throws JsonProcessingException {
        int actual = exchangeForStatusCode(url(NON_EXISTING), PUT, RECORD_1);

        assertEquals(404, actual);
    }

    @Test
    public void putReturnCorrectObject() throws JsonProcessingException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        Competence actual = exchangeForObject(url(id), HttpMethod.PUT, RECORD_2);

        assertEquals(id, actual.getId());
        assertEquals(RECORD_2.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());
    }

    @Test
    public void putAdaptDatabase() throws JsonProcessingException, InterruptedException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        exchangeForObject(url(id), HttpMethod.PUT, RECORD_2);

        Thread.sleep(200);
        Competence actual = exchangeForObject(url(id), GET);
        assertEquals(RECORD_2.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_2.getDescription(), actual.getDescription());
    }

    @Test
    public void deleteExistingReturn200() throws JsonProcessingException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        int actual = exchangeForStatusCode(url(id), DELETE);

        assertEquals(200, actual);
    }

    @Test
    public void deleteAdaptDatabase() throws JsonProcessingException {
        String id = exchangeForObject(url(), POST, RECORD_1).getId();

        Competence actual = exchangeForObject(url(id), GET);
        assertEquals(RECORD_1.getCompetence(), actual.getCompetence());
        assertEquals(RECORD_1.getDescription(), actual.getDescription());
    }

    private String url() {
        return String.format("http://localhost:%d/competencies", port);
    }

    private String url(String id) {
        return String.format("%s/%s", url(), id);
    }

    private Competence exchangeForObject(String url, HttpMethod method) throws JsonProcessingException {
        return exchange(url, method, null, Competence.class).getBody();
    }

    private int exchangeForStatusCode(String url, HttpMethod method) throws JsonProcessingException {
        ResponseEntity<String> actual = exchange(url, method, null, String.class);
        return actual.getStatusCode().value();
    }

    private Competence exchangeForObject(String url, HttpMethod method, Object value) throws JsonProcessingException {
        return exchange(url, method, value, Competence.class).getBody();
    }

    private int exchangeForStatusCode(String url, HttpMethod method, Object value) throws JsonProcessingException {
        ResponseEntity<String> actual = exchange(url, method, value, String.class);
        return actual.getStatusCode().value();
    }

    private <T> ResponseEntity<T> exchange(String url, HttpMethod method, Object value, Class<T> responseType) throws JsonProcessingException {
        return restTemplate.exchange(url, method, getHttpEntity(value), responseType);
    }

    private HttpEntity<String> getHttpEntity(Object value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (value != null) {
            String requestBody = mapper.writeValueAsString(value);
            return new HttpEntity<>(requestBody, headers);
        }
        return new HttpEntity<>(headers);
    }
}

