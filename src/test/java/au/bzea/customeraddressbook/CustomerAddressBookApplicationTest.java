package au.bzea.customeraddressbook;

import au.bzea.customeraddressbook.controller.CustomerAddressBookController;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerAddressBookApplicationTest {

    @Autowired
    private CustomerAddressBookController controller;

    @Test
	void contextLoads() {
        assertNotNull(controller);
	}

}
