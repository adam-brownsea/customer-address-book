package au.bzea.customeraddressbook.repository;

import au.bzea.customeraddressbook.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
public interface ContactRespository extends JpaRepository<Contact, Long> {
}
