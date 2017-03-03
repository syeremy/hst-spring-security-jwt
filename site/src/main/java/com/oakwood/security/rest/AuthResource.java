package com.oakwood.security.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oakwood.security.JwtAuthenticationRequest;
import com.oakwood.security.JwtTokenUtil;
import com.oakwood.security.JwtUser;
import com.oakwood.security.service.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * [description]
 *
 * @author syeremy
 * @version 1.0
 * @date 3/2/17
 **/

@RestController
public class AuthResource  {

    @Value("${jwt.header}")
    private String tokenHeader = "Authorization";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @GET
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getProperties(@Context HttpServletRequest servletRequest,
                                  @Context HttpServletResponse servletResponse, @Context UriInfo uriInfo) throws JsonProcessingException {

        return null;
    }



    @POST
    @Path("auth")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> createAuthenticationToken(@Context HttpServletRequest servletRequest,
                                                       @Context HttpServletResponse servletResponse,
                                                       @Context UriInfo uriInfo,
                                                       @RequestBody JwtAuthenticationRequest authenticationRequest,
                                                        Device device) throws AuthenticationException {
        dependencyInit(servletRequest);

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));

    }

    @GET
    @Path("refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest servletRequest) {

        dependencyInit(servletRequest);


        String token = servletRequest.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    //TODO: SEE WHY autowire is not working!!!!
    private void dependencyInit( HttpServletRequest servletRequest)
    {
        //TODO -- Find out why Spring is not injecting the dependencies!!!
        if(authenticationManager == null) {
            //authenticationManager = new ProviderManager(Collections.singletonList(new HippoAuthenticationProvider()));
            authenticationManager = WebApplicationContextUtils.getRequiredWebApplicationContext(servletRequest.getServletContext()).getBean(AuthenticationManager.class);
        }

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
