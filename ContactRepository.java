package com.addressbook.backend.repository;

import com.addressbook.backend.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {
    // Custom query methods
    List<Contact> findByFirstName(String firstName);
    List<Contact> findByEmail(String email);
}
