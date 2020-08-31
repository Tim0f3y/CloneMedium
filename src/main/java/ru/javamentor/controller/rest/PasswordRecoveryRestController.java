package ru.javamentor.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.javamentor.model.PasswordRecoveryToken;
import ru.javamentor.model.User;
import ru.javamentor.service.passwordRecoveryToken.PasswordRecoveryTokenService;
import ru.javamentor.service.user.UserService;

/**
 * Rest контроллер для восстановления пароля
 *
 * @version 1.0
 * @author Java Mentor
 */

@RestController
@RequestMapping("/api")
public class PasswordRecoveryRestController {

    private final PasswordRecoveryTokenService passwordRecoveryTokenService;
    private final UserService userService;

    @Autowired
    public PasswordRecoveryRestController(PasswordRecoveryTokenService passwordRecoveryTokenService, UserService userService) {
        this.passwordRecoveryTokenService = passwordRecoveryTokenService;
        this.userService = userService;
    }

    /**
     * Метод для восстановления пароля юзера по email.
     */
    @PostMapping("/free-user/recoveryPassword/getCode")
    public ResponseEntity<PasswordRecoveryToken> sendRecoveryToken(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PasswordRecoveryToken passwordRecoveryToken = new PasswordRecoveryToken(user);
        passwordRecoveryTokenService.addPasswordRecoveryToken(passwordRecoveryToken);
        return new ResponseEntity<>(passwordRecoveryToken, HttpStatus.OK);
    }

}
