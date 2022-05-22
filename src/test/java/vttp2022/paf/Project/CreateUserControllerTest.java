package vttp2022.paf.Project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterEach;
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

import vttp2022.paf.Project.service.MainService;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateUserControllerTest {

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
                          .setAttribute("username", "user");
    
        mockSess = new MockHttpSession(webApplicationContext.getServletContext());

        mockSess.setAttribute("username","user");
    }

    @Test
    public void addingNewUserShouldFail() {

        // writing a test to create an invalid user
        
        RequestBuilder req = MockMvcRequestBuilders.post("/createUser")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .param("username", "")
            .param("password", "password");

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
    public void addingNewUserWithSameNameShouldFail() {

        // writing a test to create a user with a username already taken

        RequestBuilder req = MockMvcRequestBuilders.post("/createUser")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .param("username", "Veronica")
            .param("password", "Veronica");

            MvcResult result = null;
            try {
                result = mvc.perform(req).andReturn();
            } catch (Exception ex) {
                fail("Cannot perform mvc invocation", ex);
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
    public void loginWrongUsernameShouldFail() {

        // writing a test to login with wrong username

        RequestBuilder req = MockMvcRequestBuilders.post("/login")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .param("username", " ")
            .param("password", "password");

            MvcResult result = null;
            try {
                result = mvc.perform(req).andReturn();
            } catch (Exception ex) {
                fail("Cannot perform mvc invocation", ex);
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
    public void logoutShouldPass() {

        // writing a test to test logout

        RequestBuilder req = MockMvcRequestBuilders.get("/logout")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .accept(MediaType.TEXT_HTML_VALUE)
            .session(mockSess);

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
    
}
