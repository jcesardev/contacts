package com.albo.directory.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

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

    
    @Before
    public void setupTest() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    
    @Test
    public void canRetrieveWhenExists() throws Exception {
        User usr = new User();        
        List<User> users = new ArrayList<>();
        users.add(usr);
        
        // given
        given(userRepository.findAll()).willReturn(users);
        
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/usr").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonUser.write(users).getJson());
    }

}
