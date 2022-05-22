package vttp2022.paf.Project.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.paf.Project.model.DatabaseResult;
import vttp2022.paf.Project.model.SearchResult;
import vttp2022.paf.Project.model.Sidebar;
import vttp2022.paf.Project.model.UserRatings;
import vttp2022.paf.Project.service.MainService;

@Controller
@RequestMapping(path="/protected")
public class ProtectedController {

    @Autowired
    private MainService mSvc;

    @RequestMapping("/ratings")
    public ModelAndView ratingsPage(HttpSession sess) {
        String username = (String)sess.getAttribute("username");
        List<UserRatings> list = mSvc.allRatings(username);

        ModelAndView mvc = new ModelAndView();
        mvc.addObject("list", list);
        mvc.setViewName("ratings");
        mvc.setStatus(HttpStatus.OK);

        return mvc;
    }

    @GetMapping("/loginSuccessful")
    public ModelAndView loadProtectedPage(HttpSession sess) {

        String username = (String)sess.getAttribute("username");

        List<UserRatings> list = new LinkedList<>();
        list = mSvc.recentRatings(username);
        Sidebar sidebar = mSvc.sidebarInfo(username);

        System.out.println(">>>>> Sidebar: " + sidebar);
        
        ModelAndView mvc = new ModelAndView();           

        mvc.setViewName("loginSuccessful");
        mvc.setStatus(HttpStatus.OK);
        mvc.addObject("username", username);
        mvc.addObject("ratingList", list);
        mvc.addObject("sidebar", sidebar);

        return mvc;
    } 

    @PostMapping("/search")
    public ModelAndView resultPage(@RequestBody MultiValueMap<String, String> form, HttpSession sess) throws IOException {
        ModelAndView mvc = new ModelAndView();
        String input = form.getFirst("input");
        String username = (String) sess.getAttribute("username");

        if ((input == null) || (input.trim().length() == 0)) {
            Sidebar sidebar = mSvc.sidebarInfo(username);
            List<UserRatings> list = mSvc.recentRatings(username);
            mvc.setViewName("loginSuccessful");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.addObject("error", "No results were found.");
            mvc.addObject("sidebar", sidebar);
            mvc.addObject("username", username);
            mvc.addObject("ratingList", list);

            return mvc;
        }

        List<DatabaseResult> databaseResultList = new LinkedList<>();
        databaseResultList = mSvc.searchDatabaseForInput(input);

        if ((databaseResultList.isEmpty()) || (databaseResultList == null)) {
            Sidebar sidebar = mSvc.sidebarInfo(username);
            List<UserRatings> list = mSvc.recentRatings(username);
            mvc.setViewName("loginSuccessful");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.addObject("error", "No results were found.");
            mvc.addObject("sidebar", sidebar);
            mvc.addObject("username", username);
            mvc.addObject("ratingList", list);

            return mvc;
        }

        mvc.addObject("result", databaseResultList);
        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("loginResult");
        mvc.addObject("input", input);

        return mvc;
    }

    @GetMapping("/{imdbId}")
    public ModelAndView showSelectedResult(@PathVariable("imdbId") String imdbId, HttpSession sess) throws IOException {
        ModelAndView mvc = new ModelAndView();
        sess.setAttribute("imdbId", imdbId);

        Optional<SearchResult> optSearchResult = mSvc.ratingsApiRequest(imdbId);

        if (optSearchResult.isEmpty()) {
            mvc.setViewName("error");
            mvc.setStatus(HttpStatus.NOT_FOUND);
            mvc.addObject("error", "The show or series is not released yet.");

            return mvc;
        }

        String username = (String)sess.getAttribute("username");
        Integer userRating = mSvc.checkUserRating(username, imdbId);

        SearchResult result = optSearchResult.get();

        mvc.setViewName("loginShow2");
        mvc.addObject("imdbId", imdbId);
        mvc.addObject("result", result);
        sess.setAttribute("result", result);
        mvc.addObject("userRating", userRating);
        mvc.setStatus(HttpStatus.OK);

        return mvc;
    }

    @PostMapping("/show")
    public ModelAndView addedRating(@RequestBody MultiValueMap<String, String> form, HttpSession sess) {
        ModelAndView mvc = new ModelAndView();

        String userRatingStr = form.getFirst("input");
        Integer userRating = Integer.parseInt(userRatingStr);

        String username = (String)sess.getAttribute("username");
        String imdbId = (String)sess.getAttribute("imdbId");
        SearchResult result = (SearchResult)sess.getAttribute("result");
        
        if (!mSvc.updateUserRating(username, imdbId, userRating)) {
            mvc.setViewName("error");
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.addObject("error", "Rating was not added.");

            return mvc;
        }

        mvc.setViewName("loginShow2");
        mvc.setStatus(HttpStatus.OK);
        mvc.addObject("userRating", userRating);
        mvc.addObject("result", result);

        return mvc;
    }

}
