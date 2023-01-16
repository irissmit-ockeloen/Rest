package fesma.nl.Payroll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeControllerTest {
    @MockBean
    EmployeeRepository mockRepository;

    @Autowired
    EmployeeController subject;

    static Employee employee1 = new Employee("James Baggins", "housekeeper");
    static Employee employee2 = new Employee("Eva Baggins", "engineer");

    @Test
    void all() {
        List<Employee> expected = List.of(employee1, employee2);
        when(mockRepository.findAll()).thenReturn(expected);

        List<Employee> actual = subject.all();

        assertEquals(expected, actual);
    }
}