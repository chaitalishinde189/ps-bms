package com.ps.thm;

//import org.cassandraunit.spring.CassandraDataSet;
//import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
//import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
//import org.cassandraunit.spring.EmbeddedCassandra;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ps.thm.dto.Show;
import com.ps.thm.enums.Format;
import com.ps.thm.enums.Language;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
/*(classes = TestConfiguration.class)
@TestExecutionListeners(listeners = {
        CassandraUnitDependencyInjectionTestExecutionListener.class,
        CassandraUnitTestExecutionListener.class,
        ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class
})
@EmbeddedCassandra(timeout = 60000)
@CassandraDataSet(value = {"shows.cql"}, keyspace = "test_ps_bms")*/
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TheatreManagementApplicationTests {

    @Autowired
    private MockMvc mvc;

    private static Gson gson;

    private static final Show show = new Show();

    @BeforeAll
    public static void setup() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter().nullSafe())
                .create();

        show.setCity("Pune");
        show.setMovieId(UUID.fromString("a4fbc72c-fea6-11ec-b939-0242ac120003"));
        show.setDate(LocalDate.parse("2022-07-20"));
        show.setTheatreId(UUID.fromString("a4fbc8e4-fea6-11ec-b939-0242ac120003"));
        show.setTime(LocalTime.parse("14:00:00"));
        show.setLanguage(Language.ENGLISH);
        show.setFormat(Format.IMAX_2D);
    }

    @Test
    @Order(1)
    public void createShowWithValidDetails() throws Exception {
        mvc.perform(post("/shows").content(asJsonString(show)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void createShowWithDuplicateDetails() throws Exception {
        Show duplicateShow = new Show();
        duplicateShow.setCity(show.getCity());
        duplicateShow.setMovieId(show.getMovieId());
        duplicateShow.setDate(show.getDate());
        duplicateShow.setTheatreId(show.getTheatreId());
        duplicateShow.setTime(show.getTime());
        duplicateShow.setLanguage(Language.HINDI);
        duplicateShow.setFormat(Format.IMAX_3D);

        mvc.perform(post("/shows").content(asJsonString(duplicateShow)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void getAllShowsUsingFilters() throws Exception {
        mvc.perform(get("/shows?city=Pune&movieId=a4fbc72c-fea6-11ec-b939-0242ac120003&date=2022-07-20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());
    }

    @Test
    @Order(4)
    public void updateShowWithValidDetails() throws Exception {
        Show updatedShow = new Show();
        updatedShow.setCity(show.getCity());
        updatedShow.setMovieId(show.getMovieId());
        updatedShow.setDate(show.getDate());
        updatedShow.setTheatreId(show.getTheatreId());
        updatedShow.setTime(show.getTime());
        updatedShow.setLanguage(Language.HINDI);
        updatedShow.setFormat(Format.IMAX_3D);

        UUID showId = getShowId(show.getCity(), show.getMovieId(), show.getDate(), show.getTheatreId(), show.getTime());
        mvc.perform(put("/shows/" + showId).content(asJsonString(updatedShow)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.language").value("HINDI"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.format").value("IMAX_3D"));
    }

    @Test
    @Order(5)
    public void updateShowWithInvalidDetails() throws Exception {
        Show updatedShow = new Show();
        updatedShow.setCity("Nashik");
        updatedShow.setMovieId(show.getMovieId());
        updatedShow.setDate(show.getDate());
        updatedShow.setTheatreId(show.getTheatreId());
        updatedShow.setTime(show.getTime());
        updatedShow.setLanguage(Language.HINDI);
        updatedShow.setFormat(Format.IMAX_3D);

        UUID showId = getShowId(show.getCity(), show.getMovieId(), show.getDate(), show.getTheatreId(), show.getTime());
        mvc.perform(put("/shows/" + showId).content(asJsonString(updatedShow)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void deleteShow() throws Exception {
        UUID showId = getShowId(show.getCity(), show.getMovieId(), show.getDate(), show.getTheatreId(), show.getTime());
        mvc.perform(delete("/shows/" + showId.toString()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(7)
    public void deleteShowWithInvalidId() throws Exception {
        mvc.perform(delete("/shows/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private UUID getShowId(String city, UUID movieId, LocalDate date, UUID theatreId, LocalTime time) throws Exception {
        MvcResult result = mvc.perform(get("/shows?city=Pune&movieId=a4fbc72c-fea6-11ec-b939-0242ac120003&date=2022-07-20"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        List<Show> shows = gson.fromJson(responseJson, new TypeToken<ArrayList<Show>>() {
        }.getType());
        return shows.get(0).getId();
    }

    private String asJsonString(final Object obj) {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDate read(final JsonReader jsonReader) throws IOException {
            return LocalDate.parse(jsonReader.nextString());
        }
    }

    private static final class LocalTimeAdapter extends TypeAdapter<LocalTime> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalTime localTime) throws IOException {
            jsonWriter.value(localTime.toString());
        }

        @Override
        public LocalTime read(final JsonReader jsonReader) throws IOException {
            return LocalTime.parse(jsonReader.nextString());
        }
    }

}
