package com.tonytekinsigths.contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactsList {
    List<Contact> contacts;

    public ContactsList(){
        contacts = new ArrayList<>();
    }

    public List<Contact> getContacts(){
         return contacts;
    }
}
