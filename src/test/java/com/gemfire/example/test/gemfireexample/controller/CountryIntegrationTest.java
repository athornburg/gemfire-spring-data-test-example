package com.gemfire.example.test.gemfireexample.controller;

import com.gemfire.example.test.gemfireexample.GemfireExampleApplication;
import com.gemfire.example.test.gemfireexample.repo.CountryRepository;
import org.apache.geode.cache.GemFireCache;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.tests.integration.ForkingClientServerIntegrationTestsSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GemfireExampleApplication.class, CountryIntegrationTest.GeodeClientTestConfiguration.class})
public class CountryIntegrationTest extends ForkingClientServerIntegrationTestsSupport {

    @BeforeClass
    public static void startGemFireServer() throws IOException {
        ForkingClientServerIntegrationTestsSupport.startGemFireServer(GeodeClientTestConfiguration.class);
    }

    @Autowired
    private CountryRepository countryRepository;

    private MockMvc mockMvc;

    @Test
    public void country_savesAndRetrievesCountriesFromGemfire() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CountryController(countryRepository)).build();

        String countryJson = "{\n" +
                "  \"name\": \"USA\",\n" +
                "  \"capital\": \"Washington\"\n" +
                "}";

        this.mockMvc.perform(post("/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(countryJson))
                .andExpect(status().isOk());


        String content = this.mockMvc.perform(get("/country/USA").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        assertThat(content).isEqualTo("{\"name\":\"USA\",\"capital\":\"Washington\"}");
    }

    @Test
    public void country_savesAndRetrievesAllCountries_builtIn() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CountryController(countryRepository)).build();

        String countryJson = "{\n" +
                "  \"name\": \"USA\",\n" +
                "  \"capital\": \"Washington\"\n" +
                "}";

        this.mockMvc.perform(post("/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(countryJson))
                .andExpect(status().isOk());


        String content = this.mockMvc.perform(get("/country").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        assertThat(content).isEqualTo("[{\"name\":\"USA\",\"capital\":\"Washington\"}]");
    }

    @Test
    public void country_savesAndRetrievesAllCountries_byQuery() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CountryController(countryRepository)).build();

        String countryJson = "{\n" +
                "  \"name\": \"USA\",\n" +
                "  \"capital\": \"Washington\"\n" +
                "}";

        this.mockMvc.perform(post("/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(countryJson))
                .andExpect(status().isOk());


        String content = this.mockMvc.perform(get("/country/byQuery").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        assertThat(content).isEqualTo("[{\"name\":\"USA\",\"capital\":\"Washington\"}]");
    }

    @CacheServerApplication
    @EnableEntityDefinedRegions
    static class GeodeServerTestConfiguration {

        public static void main(String[] args) {

            AnnotationConfigApplicationContext applicationContext =
                    new AnnotationConfigApplicationContext(GeodeServerTestConfiguration.class);

            applicationContext.registerShutdownHook();
        }
    }

    @ClientCacheApplication
    @EnableEntityDefinedRegions
    static class GeodeClientTestConfiguration {
        @Bean("country")
        ClientRegionFactoryBean exampleRegion(GemFireCache gemfireCache) {

            ClientRegionFactoryBean exampleRegion = new ClientRegionFactoryBean();

            exampleRegion.setCache(gemfireCache);

            return exampleRegion;
        }
    }
}