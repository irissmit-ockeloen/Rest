package Profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfileControllerTest {
    @MockBean
    ProfileRepository mockRepository;

    @Autowired
    ProfileController subject;

    Profile RECORD_1 = new Profile("String ", "Deniz van", "Turkey");
    Profile RECORD_2 = new Profile("Java developer", "dutch", "America");

    @Test
    void getProfilesShouldReturnAllProfiles() {
        List<Profile> expected = List.of(RECORD_1, RECORD_2);
        when(mockRepository.findAll()).thenReturn(expected);

        List<Profile> actual = subject.getProfiles();

        assertEquals(expected, actual);
    }
}



