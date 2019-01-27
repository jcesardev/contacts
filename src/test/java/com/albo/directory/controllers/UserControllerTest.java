package com.albo.directory.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.albo.directory.models.User;
import com.albo.directory.repos.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    
    private MockMvc mvc;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserController userController;
    
    private JacksonTester<List<User>> jsonUser;
    
    private JacksonTester<User> jsonUserBody;

    
    @Before
    public void setupTest() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    
    @Test
    public void whenCanRetrieveUsers_ThenOk() throws Exception {
        User usr = new User(1L, "User test", null);
        List<User> users = new ArrayList<>();
        users.add(usr);        
        given(userRepository.findAll()).willReturn(users);        
        MockHttpServletResponse response = mvc.perform(
                get("/usr").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonUser.write(users).getJson());
    }
    
    @Test
    public void whenCanFindUserById_ThenOk() throws Exception {
        User usr = new User(1L, "User test", null);        
        given(userRepository.findById(1L)).willReturn(Optional.of(usr));        
        MockHttpServletResponse response = mvc.perform(
                get("/usr/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonUserBody.write(usr).getJson());
    }
    
    @Test
    public void whenNotFoundUsers_ThenNotFound() throws Exception {                
        List<User> users = new ArrayList<>();                
        given(userRepository.findAll()).willReturn(users);        
        MockHttpServletResponse response = mvc.perform(
                get("/usr").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void whenSaveUser_ThenOk() throws Exception {
        User usr = new User(1L, "User test", null);
        String bodyRequest = jsonUserBody.write(usr).getJson();        
        mvc.perform(
                post("/usr")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
                .content(bodyRequest))                
                .andExpect(status().isOk());
    }

    @Test
    public void whenSendNullBody_ThenBadRequest() throws Exception {
        User usr = new User(null, null, null);
        String bodyRequest = jsonUserBody.write(usr).getJson();        
        mvc.perform(
                post("/usr")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
                .content(bodyRequest))                
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void whenCanUpdateUser_ThenOk() throws Exception {
        User usr = new User(1L, "User test", null);
        Optional<User> userFromDb = Optional.of(usr);
        given(userRepository.findById(1L)).willReturn(userFromDb);
        String bodyRequest = jsonUserBody.write(usr).getJson();        
        mvc.perform(
                put("/usr/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)                
                .content(bodyRequest))                
                .andExpect(status().isOk());
    }
}

