package Profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ProfileApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 public class ProfileControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    Supplier<String> HOST = () -> String.format("http://localhost:%s", port);

    Profile RECORD_1 = new Profile("Teacher ", "English", "England");
    Profile RECORD_2 = new Profile("Java developer", "Dutch", "America");

    @Test
    public void testProfiles() {
        ResponseEntity<String> response = restTemplate.getForEntity(HOST.get() + "/profiles", String.class);

        assertEquals(200, response.getStatusCode().value());

        System.out.println(response.getBody());
    }
    @Test
    public void testAddProfiles() {
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(HOST.get() + "/profiles", RECORD_1, String.class);
        assertEquals(200, responseEntity.getStatusCode().value());
    }
}
