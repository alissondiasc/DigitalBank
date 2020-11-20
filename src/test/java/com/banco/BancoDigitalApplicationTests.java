package com.banco;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * ActuatorTests
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BancoDigitalApplication.class)
@AutoConfigureMockMvc
class BancoDigitalApplicationTests {

    @Test
    void contextLoads() {
    }

}
