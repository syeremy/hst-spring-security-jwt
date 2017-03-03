package com.oakwood.security.rest;

import com.oakwood.security.JwtTokenUtil;
import com.oakwood.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@RestController
public class UserResource {

    @Value("${jwt.header}")
    private String tokenHeader = "Authorization";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @GET
    @Path("user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JwtUser getAuthenticatedUser(@Context HttpServletRequest servletRequest,
                                        @Context HttpServletResponse servletResponse) {

        dependencyInit(servletRequest);

        String token = servletRequest.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

    //TODO: SEE WHY autowire is not working!!!!
    private void dependencyInit( HttpServletRequest servletRequest)
    {
        //TODO -- Find out why Spring is not injecting the dependencies!!!
        if(userDetailsService == null) {
            //userDetailsService = new JwtUserDetailsServiceImpl();
            userDetailsService = WebApplicationContextUtils.getRequiredWebApplicationContext(servletRequest.getServletContext()).getBean(UserDetailsService.class);
        }

        if(jwtTokenUtil == null) {
            //jwtTokenUtil = new JwtTokenUtil();
            jwtTokenUtil = WebApplicationContextUtils.getRequiredWebApplicationContext(servletRequest.getServletContext()).getBean(JwtTokenUtil.class);
        }
        //TODO --/
    }
    protected void init()
    {

    }

}
