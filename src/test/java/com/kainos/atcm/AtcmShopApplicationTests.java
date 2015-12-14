package com.kainos.atcm;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AtcmShopApplication.class)
@WebAppConfiguration
public class AtcmShopApplicationTests {

    @Test
    @Ignore
    public void contextLoads() {
        assertTrue(true);
    }

}
