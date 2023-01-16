package fesma.nl.Payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(classes = PayrollApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class PayrollApplicationIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAllEmployees() {
        List<Employee> actual = restTemplate.getForObject("http://localhost:" + port + "/employees", List.class);
        assertEquals(2, actual.size());
    }

    @Test
    @DirtiesContext
    public void testAddEmployee() {
        Employee employee = new Employee("Lokes Gupta", "supplier");
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/employees", employee, String.class);
        assertEquals(200, responseEntity.getStatusCode().value());
    }
}
