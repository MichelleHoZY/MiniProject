package vttp2022.paf.Project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.paf.Project.service.MainService;

@Controller
@RequestMapping(path={"/", "index.html"})
public class MainController {

    @Autowired
    private MainService mSvc;

    @GetMapping("/result")
    public ModelAndView resultPage(@RequestParam String input, @RequestParam String type, @RequestParam String country, @RequestParam String service) throws IOException {

        Integer response = mSvc.makingRequest(input, country, type, service);
        System.out.println(">>>>> Controller response: " + response);

        ModelAndView mvc = new ModelAndView();

        mvc.addObject("input", input);
        mvc.addObject("type", type);
        mvc.addObject("country", country);
        mvc.addObject("service", service);
        mvc.setStatus(HttpStatus.OK);
        mvc.setViewName("result");

        return mvc;
    }
    
}
