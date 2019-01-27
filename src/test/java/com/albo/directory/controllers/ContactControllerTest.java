package com.albo.directory.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.albo.directory.models.Contact;
import com.albo.directory.models.User;
import com.albo.directory.repos.ContactRepository;
import com.albo.directory.repos.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {
    
    private MockMvc mvc;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock 
    private ContactRepository contactRepository;
    
    @InjectMocks
    private ContactController contactController;
    
    private JacksonTester<List<Contact>> jsonContacts;
    
    private JacksonTester<Contact> jsonContact;
   
    @Before
    public void setupTest() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(contactController).build();
    }
    
    @Test
    public void whenSaveContact_ThenOk() throws Exception {
        Contact contact = new Contact(2L, "New Contact", 525568686868L);
        User usr = new User(1L, "User test", new ArrayList<Contact>());
        Optional<User> userFromDb = Optional.of(usr);        
        given(userRepository.findById(1L)).willReturn(userFromDb);    
        given(contactRepository.findByPhoneNumber(525568686868L)).willReturn(contact);
        given(userRepository.saveAndFlush(usr)).willReturn(usr);
        String bodyRequest = jsonContact.write(contact).getJson();        
        mvc.perform(
                post("/usr/1/receipt")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
                .content(bodyRequest))                
                .andExpect(status().isOk());
    }    
    
    @Test
    public void whenCanRetrieveUserAndCanRetrieveContact_ThenOk() throws Exception {
        Contact contact = new Contact(2L, "New Contact", 525568686868L);
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        User usr = new User(1L, "User test", contacts);
        Optional<User> userFromDb = Optional.of(usr);        
        given(userRepository.findById(1L)).willReturn(userFromDb);
        
        MockHttpServletResponse response = mvc.perform(
                get("/usr/1/receipt").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonContacts.write(contacts).getJson());
    }
    
    @Test
    public void whenCanRetrieveUserAndCanRetrieveContactById_ThenOk() throws Exception {
        Contact contact = new Contact(2L, "New Contact", 525568686868L);
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        User usr = new User(1L, "User test", contacts);
        Optional<User> userFromDb = Optional.of(usr);        
        given(userRepository.findById(1L)).willReturn(userFromDb);
        
        MockHttpServletResponse response = mvc.perform(
                get("/usr/1/receipt/2").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonContact.write(contact).getJson());
    }
}
