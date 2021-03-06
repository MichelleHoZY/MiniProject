package vttp2022.paf.Project.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.paf.Project.model.DatabaseResult;
import vttp2022.paf.Project.model.SearchResult;
import vttp2022.paf.Project.service.MainService;

@Controller
@RequestMapping(path={"/", "index.html"})
public class MainController {

    @Autowired
    private MainService mSvc;

    @RequestMapping("/createUser")
    public String createUser() {
        return "createUser";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleError404(HttpServletRequest request, Exception e)   {
            ModelAndView mvc = new ModelAndView("error");
            mvc.setStatus(HttpStatus.NOT_FOUND);
            mvc.addObject("error", "No results retrieved from APIs.");  
            return mvc;
    }

    @PostMapping("/show/result")
    public ModelAndView resultPage(@RequestBody MultiValueMap<String, String> form) throws IOException {
        ModelAndView mvc = new ModelAndView();
        String input = form.getFirst("input");

        if ((input == null) || (input.trim().length() == 0)) {
            mvc.setViewName("index");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.addObject("error", "No results were found.");

            return mvc;
        }

        List<DatabaseResult> databaseResultList = new LinkedList<>();
        databaseResultList = mSvc.searchDatabaseForInput(input);

        if ((databaseResultList.isEmpty()) || (databaseResultList == null)) {
            mvc.setViewName("index");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.addObject("error", "No results were found.");

            return mvc;
        }

        mvc.addObject("result", databaseResultList);
        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("result");
        mvc.addObject("input", input);

        return mvc;
    }

    @GetMapping("/show/{imdbId}")
    public ModelAndView showSelectedResult(@PathVariable("imdbId") String imdbId) throws IOException {
        ModelAndView mvc = new ModelAndView();

        Optional<SearchResult> optSearchResult = mSvc.ratingsApiRequest(imdbId);

        if (optSearchResult.isEmpty()) {
            mvc.setViewName("error");
            mvc.setStatus(HttpStatus.NOT_FOUND);
            mvc.addObject("error", "The show or series is not released yet.");

            return mvc;
        }

        SearchResult result = optSearchResult.get();

        mvc.setViewName("show");
        mvc.addObject("result", result);
        mvc.setStatus(HttpStatus.OK);

        return mvc;
    }

    @PostMapping("/login")
    public ModelAndView redirectPage(@RequestBody MultiValueMap<String, String> form, HttpSession sess) {
        ModelAndView mvc = new ModelAndView();

        String username = form.getFirst("username");
        String password = form.getFirst("password");

        if (mSvc.verifyLogin(username, password) == 2) {
            sess.setAttribute("username", username); // logged in
            mvc = new ModelAndView("redirect:/protected/loginSuccessful");
        } else if (mSvc.verifyLogin(username, password) == 1) { // wrong password
            mvc.setStatus(HttpStatus.FORBIDDEN);
            mvc.setViewName("login");
            mvc.addObject("error", "Incorrect password");
        } else if (mSvc.verifyLogin(username, password) == 0) { // no user exists
            mvc.setStatus(HttpStatus.NOT_FOUND); 
            mvc.setViewName("login");
            mvc.addObject("error", "User does not exist");
        }

        return mvc;
    }

    @PostMapping("/createUser")
    public ModelAndView createNewUser(@RequestBody MultiValueMap<String, String> form) {
        ModelAndView mvc = new ModelAndView();

        String username = form.getFirst("username");
        String password = form.getFirst("password");

        if (mSvc.createNewUser(username, password) == 3) {
            mvc.addObject("username", username);
            mvc.setViewName("login");
            mvc.setStatus(HttpStatus.CREATED);
        } else if (mSvc.createNewUser(username, password) == 1) {
            mvc.setViewName("error");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.addObject("error", "Username is taken");
        } else {
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
            mvc.addObject("error", "Please enter a username");
        }

        return mvc;
    }

    @GetMapping("/logout")
    public ModelAndView getLogout(HttpSession sess) {
        sess.invalidate();

        ModelAndView mvc = new ModelAndView();
        mvc.setViewName("index");
        mvc.setStatus(HttpStatus.OK);

        return mvc;
    }
    
}
