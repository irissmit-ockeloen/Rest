package Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProfileControllerTests {
    @MockBean
    ProfileRepository mockRepository;

    @Autowired
    ProfileController subject;


}
