package project.restapimovie.controller;

import project.restapimovie.model.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldFetchAllMembers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(460)))
                .andReturn();
    }

    @Test
    public void shouldFindMemberById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI + "2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.movie").exists())
                .andExpect(jsonPath("$.movie").value("Jurassic Park"))
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.year").value("-1993"))
                .andExpect(jsonPath("$.genre").exists())
                .andExpect(jsonPath("$.genre").value("\r\nAction, Adventure, Sci-Fi            "))
                .andExpect(jsonPath("$.rating").exists())
                .andExpect(jsonPath("$.rating").value("8.1"))
                .andExpect(jsonPath("$.votes").exists())
                .andExpect(jsonPath("$.votes").value("897,444"))
                .andExpect(jsonPath("$.stars").exists())
                .andExpect(jsonPath("$.stars").value("\r\n    Director:\r\nSteven Spielberg\r\n| \r\n    Stars:\r\nSam Neill, \r\nLaura Dern, \r\nJeff Goldblum, \r\nRichard Attenborough\r\n"))
                .andExpect(jsonPath("$.runtime").exists())
                .andExpect(jsonPath("$.runtime").value("127"))
                .andExpect(jsonPath("$.oneLine").exists())
                .andExpect(jsonPath("$.oneLine").value("\r\nA pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose."))
                .andExpect(jsonPath("$.gross").exists())
                .andExpect(jsonPath("$.gross").value("$402.45M"))
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andReturn();
    }

    @Test
    public void shouldVerifyInvalidMemberId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI + "0")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Movie with ID: '0' not found."))
                .andReturn();
    }

    @Test
    public void shouldVerifyInvalidMemberArgument() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI + "abc")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }

    @Test
    public void shouldSaveMember() throws Exception {
        Movie record = new Movie("Squid Game", "2021", "Random", "8.1", "Lorem ipsum", "Gong yoo", "1234", "60 minute", "$75.47M");

        this.mockMvc.perform(MockMvcRequestBuilders.post(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(461))
                .andExpect(jsonPath("$.movie").exists())
                .andExpect(jsonPath("$.movie").value("Squid Game"))
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.year").value("2021"))
                .andExpect(jsonPath("$.genre").exists())
                .andExpect(jsonPath("$.genre").value("Random"))
                .andExpect(jsonPath("$.rating").exists())
                .andExpect(jsonPath("$.rating").value("8.1"))
                .andExpect(jsonPath("$.votes").exists())
                .andExpect(jsonPath("$.votes").value("1234"))
                .andExpect(jsonPath("$.stars").exists())
                .andExpect(jsonPath("$.stars").value("Gong yoo"))
                .andExpect(jsonPath("$.runtime").exists())
                .andExpect(jsonPath("$.runtime").value("60 minute"))
                .andExpect(jsonPath("$.oneLine").exists())
                .andExpect(jsonPath("$.oneLine").value("Lorem ipsum"))
                .andExpect(jsonPath("$.gross").exists())
                .andExpect(jsonPath("$.gross").value("$75.47M"))
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andReturn();

    }

    @Test
    public void shouldVerifyInvalidSaveMember() throws Exception {
        Movie record = new Movie();

        this.mockMvc.perform(MockMvcRequestBuilders.post(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Your request has issued a malformed or illegal request."))
                .andReturn();

    }

    @Test
    public void shouldUpdateMember() throws Exception {
        Movie record = new Movie((long) 461, "Squid Gamee", "2021", "Random", "8.1", "Lorem ipsum", "Gong yoo", "1234", "60 minute", "$75.47M");

        this.mockMvc.perform(MockMvcRequestBuilders.put(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(461))
                .andExpect(jsonPath("$.movie").exists())
                .andExpect(jsonPath("$.movie").value("Squid Gamee"))
                .andExpect(jsonPath("$.year").exists())
                .andExpect(jsonPath("$.year").value("2021"))
                .andExpect(jsonPath("$.genre").exists())
                .andExpect(jsonPath("$.genre").value("Random"))
                .andExpect(jsonPath("$.rating").exists())
                .andExpect(jsonPath("$.rating").value("8.1"))
                .andExpect(jsonPath("$.votes").exists())
                .andExpect(jsonPath("$.votes").value("1234"))
                .andExpect(jsonPath("$.stars").exists())
                .andExpect(jsonPath("$.stars").value("Gong yoo"))
                .andExpect(jsonPath("$.runtime").exists())
                .andExpect(jsonPath("$.runtime").value("60 minute"))
                .andExpect(jsonPath("$.oneLine").exists())
                .andExpect(jsonPath("$.oneLine").value("Lorem ipsum"))
                .andExpect(jsonPath("$.gross").exists())
                .andExpect(jsonPath("$.gross").value("$75.47M"))
                .andExpect(jsonPath("$.*", hasSize(10)))
                .andReturn();
    }

    @Test
    public void shouldRemoveMember() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(MovieController.URI + "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Movie with ID: '1' deleted."))
                .andReturn();
    }

    @Test
    public void shouldVerifyInvalidMemberRemove() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(MovieController.URI + "999")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Movie with ID: '999' not found."))
                .andReturn();
    }
}

