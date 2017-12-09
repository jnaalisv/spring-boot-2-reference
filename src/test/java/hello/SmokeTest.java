package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = HelloController.class
)
public class SmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void applicationShouldStart() {
        String body = this.restTemplate.getForObject("/hello", String.class);
        assertThat(body).isEqualTo("Hello World!");
    }
}
