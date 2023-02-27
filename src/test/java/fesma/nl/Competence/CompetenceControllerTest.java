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

    Competence RECORD_1 = new Competence("Python", "Developer");
    Competence RECORD_2 = new Competence("C++", "Developer");
    Competence RECORD_3 = new Competence("JavaScript", "Developer");
    Competence RECORD_4 = new Competence("Ruby", "Developer");

    @Test
    void getAllCompetenciesShouldReturnAllCompetencies() {
        List<Competence> expected = List.of(RECORD_1, RECORD_2, RECORD_3, RECORD_4);
        when(mockRepository.findAll()).thenReturn(expected);

        List<Competence> actual = subject.getAllCompetencies();

        assertEquals(expected, actual);
    }

    @Test
    void postCompetenciesShouldSaveNewCompetenciesToRepository() {
        subject.postCompetencies(RECORD_1);

        verify(mockRepository, times(1)).save(RECORD_1);
    }

    @Test
    void getCompetenciesShouldReturnCompetenciesWhenCompetenciesExist() {
        when(mockRepository.findById(ID)).thenReturn(Optional.of(RECORD_1));

        Object actual = subject.getCompetencies(ID);

        assertEquals(RECORD_1, actual);
    }

    @Test
    void getCompetenciesShouldThrowWhenCompetenciesDoesNotExist() {
        when(mockRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(CompetenceNotFoundException.class, () -> subject.getCompetencies(ID));
    }

    @Test
    void putCompetenciesShouldSavNewCompetenciesWheCompetenciesExist() throws CompetenceNotFoundException {
        when(mockRepository.findById(ID)).thenReturn(Optional.of(RECORD_1));

        subject.putCompetencies(RECORD_2, ID);

        verify(mockRepository, times(1)).save(RECORD_2);
    }

    @Test
    void putCompetenciesThrowWhenCompetenciesNotExist() {
        when(mockRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(CompetenceNotFoundException.class, () -> subject.putCompetencies(RECORD_2, ID));
    }

    @Test
    void deleteCompetenciesShouldDeleteCompetenciesFromRepository() {
        subject.deleteCompetencies(ID);

        verify(mockRepository, times(1)).deleteById(ID);
    }
}
