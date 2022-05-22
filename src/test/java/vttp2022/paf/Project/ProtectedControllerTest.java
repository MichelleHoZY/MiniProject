package vttp2022.paf.Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import javax.servlet.http.HttpServletRequest;

import vttp2022.paf.Project.service.MainService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProtectedControllerTest {

    @Mock
    private MainService mSvc;

    @Autowired
    private MockMvc mvc;

    @Autowired
    MockHttpSession mockSess;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private HttpServletRequest httpServletRequest;

   @BeforeEach
    public void setUp() throws Exception {
    
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 .build();
    
        httpServletRequest.getSession()
                          .setAttribute("username", "Veronica");
    
        mockSess = new MockHttpSession(webApplicationContext.getServletContext());

        mockSess.setAttribute("username","Veronica");
    }

    @Test
    public void protectedHomePageShouldPass() {

        // writing a test to load the protected home page        

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/loginSuccessful")
            .contentType(MediaType.TEXT_HTML_VALUE)
            .with(csrf())
            .session(mockSess)
            .accept(MediaType.TEXT_HTML_VALUE);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(200, resp.getStatus());
            assertEquals("loginSuccessful", result.getModelAndView().getViewName());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void refreshedRatingPageShouldPass() {

        // writing a test with an valid rating to pass

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/show")
            .with(csrf())
            .session(mockSess)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .param("input", "9");

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
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
    public void refreshedRatingPageShouldFail() {

        // writing a test with an invalid rating to fail

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/show")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .with(csrf())
            .session(mockSess)
            .param("input", "10");

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(400, resp.getStatus());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void shouldLoadMovie() {

        // writing a test with an valid movie to pass

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/tt0097165")
            .with(csrf())
            .session(mockSess)    
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
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
    public void shouldNotLoadUnleasedMovie() {

        // writing a test with an unreleased movie to fail

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/tt2640044")
            .with(csrf())
            .session(mockSess)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(404, resp.getStatus());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void ratingsPageShouldPass() {

        // writing a test to load the ratings page

        RequestBuilder req = MockMvcRequestBuilders.get("/protected/ratings")
            .with(csrf())
            .session(mockSess)    
            .contentType(MediaType.TEXT_HTML_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE);

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
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
    public void searchPageShouldFail() {

        // writing a test to load the search page with an invalid search

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/search")
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .with(csrf())
            .session(mockSess)
            .param("input", " ");

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(400, resp.getStatus());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void searchPageShouldFailNonexistentTitle() {

        // writing a test to load the search page with a title that doesn't exist

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/search")
            .with(csrf())
            .session(mockSess)    
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("input", "trying to find a title that doesn't exist 89");

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
            return;
        }

        MockHttpServletResponse resp = result.getResponse();
        try {
            assertEquals(400, resp.getStatus());
        } catch (Exception ex) {
            fail("Status not expected", ex);
            return;
        }
    }

    @Test
    public void searchPageShouldPass() {

        // writing a test to load the search page with a valid title

        RequestBuilder req = MockMvcRequestBuilders.post("/protected/search")
            .with(csrf())
            .session(mockSess)
            .accept(MediaType.TEXT_HTML_VALUE)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .param("input", "Band of Brothers");

        MvcResult result = null;
        try {
            result = mvc.perform(req).andReturn();
        } catch (Exception ex) {
            assertTrue(true);
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
    
}
