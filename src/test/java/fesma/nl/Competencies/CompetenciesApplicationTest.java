package fesma.nl.Competencies;
        ;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CompetenciesApplicationTest {

    @Autowired
    CompetenciesApplication subject;

    @Test
    void Test() {
        assertNotNull(subject);
    }
}