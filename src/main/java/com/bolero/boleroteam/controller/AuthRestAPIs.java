package com.bolero.boleroteam.controller;

import com.bolero.boleroteam.repository.RoleRepository;
import com.bolero.boleroteam.repository.UserRepository;
import com.bolero.boleroteam.config.jwt.JwtProvider;
import com.bolero.boleroteam.message.request.LoginForm;
import com.bolero.boleroteam.message.request.SignUpForm;
import com.bolero.boleroteam.message.response.JwtResponse;
import com.bolero.boleroteam.model.Role;
import com.bolero.boleroteam.model.RoleName;
import com.bolero.boleroteam.model.User;
import com.bolero.boleroteam.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser( @RequestBody SignUpForm signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        signUpRequest.getName();
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstName(), signUpRequest.getLastName(),
                signUpRequest.getPhoneNumber());
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
//        strRoles.forEach(role -> {
//            switch(role) {
//                case "admin":
//                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
//                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//                    roles.add(adminRole);
//                    break;
//                case "pm":
//                    Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
//                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//                    roles.add(pmRole);
//                    break;
//                default:
//                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
//                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
//                    roles.add(userRole);
//            }
//        });
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }
//    @PostMapping("/password")
//    ResponseEntity<String> pssss(@RequestParam("checkPassword") String checkPassword, @RequestParam("newPassword") String newPassword, Long idUser){
//
//        Optional<User> user = userRepository.findByUsername(principal.getName());
//        if (user.isPresent()){
//            String passwordThenEndCode = passwordEncoder.encode(checkPassword);
//
//            if (passwordThenEndCode.equals()){
//                return new ResponseEntity<>( passwordThenEndCode,HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }
//    }
    @PutMapping("/updateUser")
    public ResponseEntity<Void> updateUser(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Principal principal){
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user1 = user.get();
        userService.changePassword(user1, oldPassword, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
