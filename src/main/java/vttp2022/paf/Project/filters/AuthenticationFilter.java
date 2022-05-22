package vttp2022.paf.Project.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpServletResponse httpResp = (HttpServletResponse)response;

        HttpSession sess = httpReq.getSession();
        String username = (String)sess.getAttribute("username");

        if ((null == username) || (username.trim().length() <= 0)) {
            httpResp.sendRedirect("/index.html");
            return;
        }

        System.out.println(">>>>> Url: " + httpReq.getRequestURI().toString());
        System.out.println(">>>>> Username: " + username);

        chain.doFilter(request, response);
    }
    
}