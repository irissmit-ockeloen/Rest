package fesma.nl.Profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProfileApplicationTest {

    @Autowired
    ProfileApplication subject;

    @Test
    void smokeTest() {
        assertNotNull(subject);
    }

}
