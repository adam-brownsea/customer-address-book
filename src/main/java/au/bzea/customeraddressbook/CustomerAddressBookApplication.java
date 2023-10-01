package au.bzea.customeraddressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan("au.bzea.customeraddressbook.model")
@SpringBootApplication
public class CustomerAddressBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAddressBookApplication.class, args);
	}

}
