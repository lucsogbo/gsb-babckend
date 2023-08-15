package bj.dgi.GSBBackend.controller;


import bj.dgi.GSBBackend.entities.Role;
import bj.dgi.GSBBackend.entities.User;
import bj.dgi.GSBBackend.enums.UserStatus;
import bj.dgi.GSBBackend.exception.AppException;
import bj.dgi.GSBBackend.payload.*;
import bj.dgi.GSBBackend.repositories.RoleRepository;
import bj.dgi.GSBBackend.repositories.UserRepository;

import bj.dgi.GSBBackend.security.CurrentUser;
import bj.dgi.GSBBackend.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
@Api(value = "Users", description = "Users controllers details.", tags = {"Users"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController extends bj.dgi.GSBBackend.controller.BaseController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

//    @GetMapping("/me")
//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') ")
//    @ApiOperation(value = "This ressource is used to get connected user details. ADMIN or USER account is necessary to master this operation..")
//    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
//        //UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
//        return userRepository.findById(currentUser.getId()).get();
//    }

    public User getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findById(currentUser.getId()).get();
    }

//    @GetMapping("/me")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    @ApiOperation(value = "This ressource is used to get connected user details. ADMIN or USER account is necessary to master this operation..")
//    public ResponseEntity<?> getCurrentAgentUser(@CurrentUser UserPrincipal currentUser) {
//        //UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
//        try{
//            Optional<User>  userOp =  userRepository.findById(currentUser.getId());
//            Me me = new  Me() ;
//            userOp.ifPresent(me::setUser);
//
////            me.setAgent(user.getAgent());
//            return ResponseEntity.ok(me);
//        }catch (Exception e){
//            throw new AppException(e.getMessage()) ;
//        }
//
//    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') ")
    @ApiOperation(value = "This ressource is used to retrieve user details based on his id. ADMIN or USER account is necessary .")
    public User getCurrentUser(@PathVariable Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @GetMapping("/checkUsernameAvailability")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "This ressource is used to check username avalability. ADMIN account is necessary to master this operation.")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkEmailAvailability")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "This ressource is used to check email avalability. ADMIN account is necessary to master this operation.")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }


//    @PostMapping("/update-user")
    @ApiOperation(value = "This ressource is used to update user account. ADMIN account is necessary to master this operation.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user) {

        User result = null;
        if (userRepository.existsById(user.getId())) {

            User r = userRepository.findById(user.getId()).get();
            if (user.getPassword() != "") {
                //String pwd = passwordEncoder.encode(user.getPassword());
                user.setPassword(r.getPassword());
                result = userRepository.save(user);
            } else {
                return new ResponseEntity(new ApiResponse(false, "Le mot de passe ne peut pas être vide."),
                        HttpStatus.BAD_REQUEST);
            }
        }
        return ResponseEntity.ok(result);
    }


    @ApiOperation(value = "This ressource is used to reset user password. ADMIN account is necessary to master this operation.")
    @PutMapping("/resetByAdmin-password/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    public ResponseEntity<?> resetByAdminPassword(@PathVariable("userId") Long userId) {
        try{
            Optional<User> optU = userRepository.findById(userId);
            if(optU.isPresent()){
                User user =optU.get() ;
                user.setPassword(passwordEncoder.encode(user.getUsername() + "#2022"));
                user.setHasDefaultPassword(true);
                userRepository.save(user);
                ResetPassword rp = new ResetPassword(user.getUsername() + "#2022",user.getUsername() ) ;
                return new ResponseEntity<>(new ApiResponse(true, "Nouveau login ",HttpStatus.OK.value(),
                        rp), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponse(false, "User not found ",HttpStatus.BAD_REQUEST.value(),
                    null), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @GetMapping("/roles")
    public ResponseEntity<ApiResponse> getAllRole() {
        return ResponseEntity.ok(new ApiResponse(true, "List des roles",HttpStatus.OK.value(),roleRepository.findAll()));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Ressources qui recupere tous les Utilisateurs .")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        try{
            return ResponseEntity.ok
                    (new ApiResponse(true, "List des users ", HttpStatus.OK.value(), userRepository.findAll()));
        }catch(Exception e ){
            return new ResponseEntity<>
                    (new ApiResponse(false, e.getMessage(), HttpStatus.OK.value(),null),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/byUserNameOrEmail/{nameOrmail}")
    @ApiOperation(value = "Ressources qui recherche un User par son email ou username  .")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getByUserNameOrEmail(@PathVariable("nameOrmail") String nameOrmail) {
        try{
            Optional<User> user = userRepository.findByUsernameOrEmail(nameOrmail, nameOrmail );
            if (user.isPresent()) {
                return ResponseEntity.ok
                        (new ApiResponse(true, null, HttpStatus.OK.value(),user.get()));
            }
            return new ResponseEntity<>
                    (new ApiResponse(false, "Not found", HttpStatus.OK.value(),null),HttpStatus.NOT_FOUND);
        }catch(Exception e ){
            return new ResponseEntity<>
                    (new ApiResponse(false, e.getMessage(), HttpStatus.OK.value(),null),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{id}/changeAllRoles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Change les roles du user ")
    public ResponseEntity<ApiResponse> revokeAllRoleAndAdd(@PathVariable(value = "id") Long id, @RequestBody List<Role> roleList ) {
       /*
        On revoke tous les droits et on ajoute la nouvelle liste de roles
        */
        try{
            Optional<User> user = userRepository.findById(id);
            Set<Role> roles = new HashSet<>() ;

            for(Role role : roleList){
                Optional<Role> optRole = this.roleRepository.findById(role.getId()) ;
                optRole.ifPresent(roles::add);
            }
                if (user.isPresent() && !roles.isEmpty()) {
                    User user1 = user.get();

                    user1.setRoles(roles);
                    user1  = userRepository.save(user1);
                    return ResponseEntity.ok(new ApiResponse(true, "Role bien assigné", HttpStatus.OK.value(),user1));

                } else {
                    return ResponseEntity.ok(new ApiResponse(false, "Utilisateur non trouvé", HttpStatus.NOT_FOUND));
                }

        }catch (Exception e){
            return new ResponseEntity<>
                    (new ApiResponse(false, e.getMessage(), null,null),HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/disableUser/{id}")
    @ApiOperation(value = "Ressource pour désactiver un compte")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> disableUser(@PathVariable("id") Long id) {
        try{
            Optional<User> userOpt = userRepository.findById(id) ;
            if (userOpt.isPresent()) {
                User user = userOpt.get() ;
                user.setActif(false);
                user.setUserStatus(UserStatus.DISABLED);
                this.userRepository.save(user) ;
                return ResponseEntity.ok
                        (new ApiResponse(true, "compte désactiver", HttpStatus.OK.value(),null));
            }
            return new ResponseEntity<>
                    (new ApiResponse(false, "Not found", HttpStatus.OK.value(),null),HttpStatus.NOT_FOUND);
        }catch(Exception e ){
            return new ResponseEntity<>
                    (new ApiResponse(false, e.getMessage(), HttpStatus.OK.value(),null),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/enableUser/{id}")
    @ApiOperation(value = "Ressource pour activé un compte")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> enableUser(@PathVariable("id") Long id) {
        try{
            Optional<User> userOpt = userRepository.findById(id) ;
            if (userOpt.isPresent()) {
                User user = userOpt.get() ;
                user.setActif(true);
                user.setUserStatus(UserStatus.ENABLED);

                return ResponseEntity.ok
                        (new ApiResponse(true, "compte désactiver", HttpStatus.OK.value(), this.userRepository.save(user) ));
            }
            return new ResponseEntity<>
                    (new ApiResponse(false, "Not found", HttpStatus.OK.value(),null),HttpStatus.NOT_FOUND);
        }catch(Exception e ){
            return new ResponseEntity<>
                    (new ApiResponse(false, e.getMessage(), HttpStatus.OK.value(),null),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @ApiOperation(value = "Ressouce pour mettre à jour un mot de passe")
    @PutMapping("/update-password")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePassword updatePassword,
                                            @CurrentUser UserPrincipal currentUser) {
        try{
            Optional<User> optUser = userRepository.findById(currentUser.getId()) ;
            if(optUser.isPresent()){
                User user = optUser.get() ;
                if(passwordEncoder.matches(updatePassword.getAncienPwd(),user.getPassword())){

                    user.setPassword(passwordEncoder.encode(updatePassword.getNouveauPwd()));
                    userRepository.save(user);
                    return new ResponseEntity<>(new ApiResponse(true, "Mot de passe bien changé ",HttpStatus.OK.value(),
                            null), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(new ApiResponse(false, "Mot de passe incorrect ",
                    HttpStatus.BAD_REQUEST.value(),null), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}