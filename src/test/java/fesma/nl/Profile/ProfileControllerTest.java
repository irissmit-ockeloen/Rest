package fesma.nl.Profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProfileControllerTest {
    @MockBean
    ProfileRepository mockRepository;

    @Autowired
    ProfileController subject;

    Profile RECORD_1 = new Profile("Teacher ", "English", "England");
    Profile RECORD_2 = new Profile("Java developer", "Dutch", "America");

    @Test
    void getProfilesShouldReturnAllProfiles() {
        List<Profile> expected = List.of(RECORD_1, RECORD_2);
        when(mockRepository.findAll()).thenReturn(expected);

        List<Profile> actual = subject.getProfiles();

        assertEquals(expected, actual);
    }

    @Test
    void postProfileShouldSaveNewProfileToRepository() {
        subject.postProfile(RECORD_1);

        verify(mockRepository, times(1)).save(RECORD_1);
    }

    @Test
    void getProfileShouldReturnProfileWhenProfileExist() {
        when(mockRepository.findById(1L)).thenReturn(Optional.of(RECORD_1));

        Object actual = subject.getProfile(1L);

        assertEquals(RECORD_1, actual);
    }

    @Test
    void getProfileShouldThrowWhenProfileDoesNotExist() {
        when(mockRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProfileNotFoundException.class, () -> {
            subject.getProfile(1L);
        });
    }

    @Test
    void putProfileShouldSaveNewProfileWhenProfileExist() {
        when(mockRepository.findById(1L)).thenReturn(Optional.of(RECORD_1));

        subject.putProfile(RECORD_2, 1L);

        verify(mockRepository, times(1)).save(RECORD_2);
    }

    @Test
    void putProfileShouldThrowWhenProfileDoesNotExist() {
        when(mockRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProfileNotFoundException.class, () -> {
                subject.putProfile(RECORD_2, 1L);
        });
    }

    @Test
    void deleteProfileShouldDeleteProfileFromRepository() {
        subject.deleteProfile(1L);

        verify(mockRepository, times(1)).deleteById(1L);
    }
}



