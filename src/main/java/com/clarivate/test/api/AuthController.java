package com.clarivate.test.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clarivate.test.db.PlayerBean;
import com.clarivate.test.security.JwtTokenProvider;
import com.clarivate.test.service.PlayerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AuthController {
    
	@Autowired
    AuthenticationManager authenticationManager;
    
	@Autowired
    PlayerService playerService;
	
	@Autowired
    JwtTokenProvider jwtTokenProvider;

	@RequestMapping("/login")
    public ResponseEntity signin(@RequestBody PlayerBean data) {
        String username = data.getUsername();
        
        PlayerBean auth = playerService.login(username, data.getPassword());
        if(auth != null) {
        	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, auth.getPassword()));
        	String token = jwtTokenProvider.createToken(username, this.playerService.findByUsername(username).getAuthorities());
            Map<Object, Object> model = new HashMap<>();
            model.put("token", token);
            return new ResponseEntity<>(model, HttpStatus.ACCEPTED);
        } else {
        	throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}