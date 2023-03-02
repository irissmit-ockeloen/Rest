package fesma.nl.Competence;

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
class CompetenceControllerTest {
    @MockBean
    CompetenceRepository mockRepository;

    @Autowired
    CompetenceController subject;

    String ID = "1L";

    Competence RECORD_1 = new Competence("Java","developer");
    Competence RECORD_2 = new Competence("C++","developer");

    @Test
    void getAllCompetenciesShouldReturnAllCompetencies() {
        List<Competence> expected = List.of(RECORD_1, RECORD_2);
        when(mockRepository.findAll()).thenReturn(expected);

        List<Competence> actual = subject.getCompetence();

        assertEquals(expected, actual);
    }

    @Test
    void postCompetenceShouldSaveNewCompetenceToRepository() {
        subject.postCompetence(RECORD_1);

        verify(mockRepository, times(1)).save(RECORD_1);
    }

    @Test
    void getCompetenceShouldReturnCompetenceWhenCompetenceExist() {
        when(mockRepository.findById(ID)).thenReturn(Optional.of(RECORD_1));

        Object actual = subject.getCompetence(ID);

        assertEquals(RECORD_1, actual);
    }

    @Test
    void getCompetenceShouldThrowWhenCompetenceDoesNotExist() {
        when(mockRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(CompetenceNotFoundException.class, () -> {
            subject.getCompetence(ID);
        });
    }

    @Test
    void putCompetenceShouldSavNewCompetenceWhenCompetenceExist() {
        when(mockRepository.findById(ID)).thenReturn(Optional.of(RECORD_1));

        subject.putCompetence(RECORD_2, ID);

        verify(mockRepository, times(1)).save(RECORD_2);
    }

    @Test
    void putCompetenceThrowWhenCompetenceDoesNotExist() {
        when(mockRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(CompetenceNotFoundException.class, () -> {
            subject.putCompetence(RECORD_2, ID);
        });
    }

    @Test
    void deleteCompetenceShouldDeleteCompetenceFromRepository() {
        subject.deleteCompetence(ID);

        verify(mockRepository, times(1)).deleteById(ID);
    }
}
