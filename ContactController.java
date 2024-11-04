package com.addressbook.backend.controller;

import com.addressbook.backend.model.Contact;
import com.addressbook.backend.repository.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    // Constructor-based dependency injection (recommended)
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            List<Contact> contacts = contactRepository.findAll();
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error if needed (using a logger)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable String id) {
        try {
            Optional<Contact> contact = contactRepository.findById(id);
            if (contact.isPresent()) {
                return new ResponseEntity<>(contact.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the error if needed (using a logger)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        try {
            Contact newContact = contactRepository.save(contact);
            return new ResponseEntity<>(newContact, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the error if needed (using a logger)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact contactDetails) {
        try {
            Optional<Contact> existingContact = contactRepository.findById(id);
            if (existingContact.isPresent()) {
                Contact contact = existingContact.get();
                contact.setFirstName(contactDetails.getFirstName());
                contact.setLastName(contactDetails.getLastName());
                contact.setEmail(contactDetails.getEmail());
                contact.setDob(contactDetails.getDob());
                contact.setPhone(contactDetails.getPhone());

                Contact updatedContact = contactRepository.save(contact);
                return new ResponseEntity<>(updatedContact, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Log the error if needed (using a logger)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable String id) {
        try {
            contactRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Log the error if needed (using a logger)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


