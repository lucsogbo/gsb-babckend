package bj.dgi.GSBBackend.controller;



import bj.dgi.GSBBackend.entities.PasswordResetToken;
import bj.dgi.GSBBackend.entities.Role;
import bj.dgi.GSBBackend.entities.User;
import bj.dgi.GSBBackend.enums.RoleName;
import bj.dgi.GSBBackend.exception.AppException;
import bj.dgi.GSBBackend.payload.ApiResponse;
import bj.dgi.GSBBackend.payload.request.LoginRequest;
import bj.dgi.GSBBackend.payload.request.SignUpRequest;
import bj.dgi.GSBBackend.payload.request.UpdateUserPasswordRequest;
import bj.dgi.GSBBackend.payload.response.ApiResponseBis;
import bj.dgi.GSBBackend.payload.response.JwtAuthenticationResponse;
import bj.dgi.GSBBackend.repositories.PasswordResetTokenRepository;
import bj.dgi.GSBBackend.repositories.RoleRepository;
import bj.dgi.GSBBackend.repositories.UserRepository;
import bj.dgi.GSBBackend.security.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    final
    AuthenticationManager authenticationManager;

    final
    UserRepository userRepository;

    final
    RoleRepository roleRepository;

    final
    PasswordEncoder passwordEncoder;

    final
    JwtTokenProvider tokenProvider;


//    @Autowired
//    SendMailService sendMailService;

    final
    PasswordResetTokenRepository passwordResetTokenRepository;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    /**
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try{
            String username = loginRequest.getUsernameOrEmail();
            Optional<User> user = userRepository.findByUsernameOrEmail(
                    username,
                    username
            );
            if (user.isPresent()) {
                username = user.get().getUsername();
                if(!user.get().isActif()){
                    return new ResponseEntity<ApiResponse>
                            (new ApiResponse(false,"le compte est désactivé",
                                    HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
                }
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
            if (user.get().isHasDefaultPassword()) {
                jwtAuthenticationResponse.setHasDefaultPassword(true);
                jwtAuthenticationResponse.setChangePasswordToken(createPasswordResetTokenForUser(user.get()));
            }
            return ResponseEntity.ok(new ApiResponse(true, "Authentification réussie",
                    HttpStatus.OK.value(), jwtAuthenticationResponse));

        }catch (Exception e ){
            return new ResponseEntity<ApiResponse>
                    (new ApiResponse(false, e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /**
     * @param signUpRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {


            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return new ResponseEntity<>(new ApiResponseBis(false, bj.dgi.GSBBackend.enums.StatusMessage.USERNAME_EXIST.getReasonPhrase(), bj.dgi.GSBBackend.enums.StatusMessage.USERNAME_EXIST.getValue(), "Username is already taken!"),
                        HttpStatus.BAD_REQUEST);
            }

//            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//                return new ResponseEntity(new ApiResponseBis(false, StatusMessage.EMAIL_EXIST.getReasonPhrase(), StatusMessage.EMAIL_EXIST.getValue(), "Email Address already in use!"),
//                        HttpStatus.BAD_REQUEST);
//            }

            // Creating user's account
            User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),signUpRequest.getEmail(),"");
            Random random = new Random();
            int aleatoire = random.nextInt(90000 - 10000 + 1) + 10000;
            String pass = "2023#"+aleatoire ;

            user.setPassword(passwordEncoder.encode(pass));

            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));

            user.setRoles(Collections.singleton(userRole));
            user.setHasDefaultPassword(true);
//            user.setAgent(signUpRequest.getAgent());
            userRepository.save(user);

            Map<String,String> map = new HashMap<>() ;
            map.put("username",user.getUsername()) ;
            map.put("password",pass) ;
            map.put("email",user.getEmail()) ;

            return ApiResponse.Res200withMsg("User créé avec succes ",map) ;
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage(), HttpStatus.OK.value(), e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(value = "/reinitiate-password/{email}")
    @ResponseBody
    @ApiOperation(value = "Permet de réinitialiser le mot de passe d'un utilisateur à partir de l'email (Mot de passe oublié)!")
    public ResponseEntity<ApiResponse> resetPassword(
            @PathVariable("email") String userEmail) {

        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()) {
            String token = createPasswordResetTokenForUser(user.get());
            try {
//                sendMailService.sendMail(user.get().getEmail(), "RESET PASSWORD", token);
                return ResponseEntity.ok(new ApiResponse(true, "token bien générer et mail envoyé!", HttpStatus.OK.value(), null));

            } catch (Exception e) {
                //e.printStackTrace();
                return ResponseEntity.ok(new ApiResponse(true, "Tout s'est bien passé mais nous ne pouvons garantir de l'envoi du mail contenant le token!", HttpStatus.OK.value(), null));

            }


        } else {
            return ResponseEntity.ok(new ApiResponse(false, "Aucun utilisateur ne correspond à cet email!", HttpStatus.NOT_FOUND.value(), null));


        }


    }


    private String createPasswordResetTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        System.out.println("token = " + token);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetToken.setExpiryDate();
        //     System.out.println("passwordResetToken = " + passwordResetToken);
        passwordResetTokenRepository.save(passwordResetToken);
        return token;
    }

    private User validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passwordResetTokenOptional =
                passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenOptional.isPresent()) {

            Calendar cal = Calendar.getInstance();
            if ((passwordResetTokenOptional.get().getExpiryDate()
                    .getTime() - cal.getTime()
                    .getTime()) <= 0) {
                return null;
            } else {
                return passwordResetTokenOptional.get().getUser();
            }

        } else {
            return null;
        }

    }


    @PostMapping("/reset-password")
    @ApiOperation(value = "Permet de mettre à jour un mot de passe oublier")
    public ResponseEntity<?> updateUserPassword(@Valid @RequestBody UpdateUserPasswordRequest updateUserPasswordRequest) {
        if (updateUserPasswordRequest.getNewPassword().equals(updateUserPasswordRequest.getConfirmPassword())) {
            User user = validatePasswordResetToken(updateUserPasswordRequest.getToken());
            if (user != null) {
                // System.out.println("updateUserPasswordRequest = " + updateUserPasswordRequest);
                user.setPassword(passwordEncoder.encode(updateUserPasswordRequest.getConfirmPassword()));
                user.setHasDefaultPassword(false);
                return ResponseEntity.ok(new ApiResponseBis(true, "Mot de passe bien mise  jour", HttpStatus.OK, userRepository.save(user)));
            } else {
                return ResponseEntity.ok(new ApiResponseBis(false, "Le token est expiré ou n'est pas correct", HttpStatus.NOT_FOUND, null));
            }
        } else {
            return ResponseEntity.ok(new ApiResponseBis(false, "Les deux mots de passe ne  sont pas identiques", HttpStatus.CONFLICT, null));
        }

    }
//    @PostMapping("/singnUpByAgent/{id_agent}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @ApiOperation("Créer user à partir d'un agent")
//    public ResponseEntity<?> registerUserByAgent(@PathVariable(value = "id_agent") Long id_agent){
//        try{
//            Optional<Agent> agentOp = agentRepository.findById(id_agent);
//            if (!agentOp.isPresent()){
//                return ApiResponse.Res400("L'agent est introuvable") ;
//            }
//            Agent agent = agentOp.get();
//            SignUpRequest signUpRequest = new SignUpRequest();
//            signUpRequest.setName(agent.getNom()+" "+agent.getPrenom());
//            signUpRequest.setEmail(agent.getEmailPro());
////            if (agent.getEmailPro()==null){
////                return ApiResponse.Res400("Vous ne pouvez pas créer de compte à cet agent, car son mail professionel n'est pas renseigné.") ;
////            }
////            signUpRequest.setUsername(signUpRequest.getEmail());
//
//            if (agent.getMatricule().isEmpty()){
//                return ApiResponse.Res400("Vous ne pouvez pas créer de compte pour cet agent, car son matricule n'est pas renseigné.") ;
//            }
//            signUpRequest.setUsername(agent.getMatricule());
//            signUpRequest.setAgent(agent);
//            return registerUser(signUpRequest);
//        }catch(Exception e ){
//            return new ResponseEntity<>
//                    (new ApiResponse(false, e.getMessage(), HttpStatus.OK.value(),null),HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
