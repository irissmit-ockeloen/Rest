package fesma.nl.Payroll;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PayrollApplicationTest {

	@Autowired
	PayrollApplication subject;

	@Test
	void smokeTest() {
		assertNotNull(subject);
	}

}
