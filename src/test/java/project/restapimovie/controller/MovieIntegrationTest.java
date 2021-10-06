package project.restapimovie.controller;

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
public class MovieIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test /** get all Movie**/
    public void shouldFetchAllMovies() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(461)))
                .andReturn();
    }

    @Test /** search movie by valid ID **/
    public void shouldFindMovieByValidID() throws Exception {
        String MovieID = "2";
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI + MovieID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
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

    @Test /** search movie by invalid ID **/
    public void shouldFindMovieInvalidMovieId() throws Exception {
        String MovieID = "0";
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI + MovieID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Movie with ID: '"+ MovieID +"' not found."))
                .andReturn();
    }

    @Test /** search movie by invalid type ID (string) **/
    public void shouldFindMovieInvalidTypeID() throws Exception {
        String MovieID = "abc";
        this.mockMvc.perform(MockMvcRequestBuilders.get(MovieController.URI + MovieID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }

    @Test /** added movie by valid data **/
    public void shouldSaveMovieValid() throws Exception {
        String movieData = "{\"year\":\"2021\",\"genre\":\"Random\",\"rating\":\"8.1\",\"stars\":\"Gong yoo\",\"votes\":\"1234\",\"runtime\":\"60 minute\",\"gross\":\"$75.47M\",\"oneLine\":\"Lorem ipsum\",\"movie\":\"Squid Game Integration Test Add\"}";
        String movieID = "463";
        this.mockMvc.perform(MockMvcRequestBuilders.post(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(movieID))
                .andExpect(jsonPath("$.movie").exists())
                .andExpect(jsonPath("$.movie").value("Squid Game Integration Test Add"))
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

    @Test /** added movie by empty data **/
    public void shouldVerifySaveMovieEmpty() throws Exception {
        String movieData = "";
        this.mockMvc.perform(MockMvcRequestBuilders.post(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }

    @Test /** added movie by invalid key data **/
    public void shouldVerifySaveMovieInvalidData() throws Exception {
        String movieData = "{\"yearsss\":\"2021\",\"genressss\":\"Random\",\"rating\":\"8.1\",\"stars\":\"Gong yoo\",\"votes\":\"1234\",\"runtime\":\"60 minute\",\"gross\":\"$75.47M\",\"oneLine\":\"Lorem ipsum\",\"movie\":\"Squid Game Integration Test Add\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.post(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }

    @Test /** update movie by valid data **/
    public void shouldUpdateMember() throws Exception {
        String movieData = "{\"id\":\"460\",\"year\":\"2021\",\"genre\":\"Random\",\"rating\":\"8.1\",\"stars\":\"Gong yoo\",\"votes\":\"1234\",\"runtime\":\"60 minute\",\"gross\":\"$75.47M\",\"oneLine\":\"Lorem ipsum\",\"movie\":\"Squid Game Integration Test Update\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(460))
                .andExpect(jsonPath("$.movie").exists())
                .andExpect(jsonPath("$.movie").value("Squid Game Integration Test Update"))
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

    @Test /** update movie by invalid ID **/
    public void shouldUpdateMovieInvalidID()  throws Exception {
        String movieData = "{\"id\":\"600\",\"year\":\"2021\",\"genre\":\"Random\",\"rating\":\"8.1\",\"stars\":\"Gong yoo\",\"votes\":\"1234\",\"runtime\":\"60 minute\",\"gross\":\"$75.47M\",\"oneLine\":\"Lorem ipsum\",\"movie\":\"Squid Game Integration Test Update\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                .value("Movie with ID: '"+ 600 +"' not found."))
                .andReturn();
    }

    @Test /** update movie by empty data **/
    public void shouldUpdateMovieEmptyData()  throws Exception {
        String movieData = "";
        this.mockMvc.perform(MockMvcRequestBuilders.put(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }

    @Test /** update movie by invalid key data **/
    public void shouldUpdateMovieInvalidData()  throws Exception {
        String movieData = "{\"id\":\"460\",\"years\":\"2021\",\"genres\":\"Random\",\"rating\":\"8.1\",\"stars\":\"Gong yoo\",\"votes\":\"1234\",\"runtime\":\"60 minute\",\"gross\":\"$75.47M\",\"oneLine\":\"Lorem ipsum\",\"movie\":\"Squid Game Integration Test Update\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(MovieController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieData)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }

    @Test /** delete movie by valid data **/
    public void shouldRemoveMovie() throws Exception {
        String MovieID = "461";
        this.mockMvc.perform(MockMvcRequestBuilders.delete(MovieController.URI + MovieID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                .value("Movie with ID: '"+ MovieID +"' deleted."))
                .andReturn();
    }

    @Test /** delete movie by invalid ID **/
    public void shouldVerifyInvalidMovieRemove() throws Exception {
        String MovieID = "0";
        this.mockMvc.perform(MockMvcRequestBuilders.delete(MovieController.URI + MovieID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                .value("Movie with ID: '"+ MovieID +"' deleted."))
                .andReturn();
    }

    @Test /** delete movie by invalid type ID**/
    public void shouldVerifyInvalidTypeIDMovieRemove() throws Exception {
        String MovieID = "abc";
        this.mockMvc.perform(MockMvcRequestBuilders.delete(MovieController.URI + MovieID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Your request has issued a malformed or illegal request."))
                .andReturn();
    }

}

