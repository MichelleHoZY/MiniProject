package vttp2022.paf.Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testingNonexistentTitleShouldFail() {

        // writing a test for the case where an input is non-existent
        
        RequestBuilder req = MockMvcRequestBuilders.post("/show/result")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .param("input", "trying to find a title that doesn't exist 89");

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }

        try {
            assertEquals("No result was found.", result.getModelAndView().getModel().get("error"));
        } catch (Exception ex) {
            fail("Non-existent title should return no results.", ex);
            return;
        }
    }

    @Test
    public void testingSpaceInputShouldFail() {

        // writing a test for the case where the input is not valid (space, # or !)
        
        RequestBuilder req = MockMvcRequestBuilders.post("/show/result")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .param("input", " ");

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }

        try {
            assertEquals("No result was found.", result.getModelAndView().getModel().get("error"));
        } catch (Exception ex) {
            fail("Invalid input should return no results.", ex);
            return;
        }
    }

    @Test
    public void testingValidInputShouldReturn200() {

        // writing a test for the case where a series is valid
        
        RequestBuilder req = MockMvcRequestBuilders.post("/show/result")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .param("input", "Band of Brothers");

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(200, resp.getStatus());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void testingValidTitleShouldReturn200() {

        // writing a test for the case where a movie is valid
        
        RequestBuilder req = MockMvcRequestBuilders.get("/show/tt0097165")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            String payload = resp.getContentAsString();
            assertNotNull(payload);
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void testingStillInProductionTitleShouldReturn404() {

        // writing a test for the case where a series or movie is not yet released
        
        RequestBuilder req = MockMvcRequestBuilders.get("/show/tt2640044")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }
        
        try {
            assertEquals("The show or series is not released yet.", result.getModelAndView().getModel().get("error"));
        } catch (Exception ex) {
            fail("Series should fail as it is still in production.", ex);
            return;
        }
    }

    //THIS
    @Test
    public void testingMultipleStreamingSitesShouldReturn200() {

        // writing a test for the case of a valid movie
        
        RequestBuilder req = MockMvcRequestBuilders.get("/show/tt0137523")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(200, resp.getStatus());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void testingMultipleSeasonsShouldReturn200() {

        // writing a test for the case of a valid series with seasons and episodes
        
        RequestBuilder req = MockMvcRequestBuilders.get("/show/tt0386676")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(200, resp.getStatus());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void testingApiReturnsNoResults() {

        // writing a test for the case where the database returns information but the APIs do not
        
        RequestBuilder req = MockMvcRequestBuilders.get("/show/tt0466615")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        System.out.println(">>>>> Request: " + req.toString());

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            fail("Cannot perform mvc invocation", ex);
            return;
        }

        try {
            assertEquals(404, result.getResponse().getStatus());
        } catch (Exception ex) {
            fail("Wrong status code from what is expected.", ex);
            return;
        }
    }
    
}
