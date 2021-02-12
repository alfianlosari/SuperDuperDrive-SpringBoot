package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping()
    public String credentialUpdated(Authentication authentication, @ModelAttribute CredentialForm credentialForm, Model model) {
        String resultError = null;

        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user == null) {
            resultError = "User doesn't exists";
        }

        if (resultError == null) {
            Integer userid = user.getUserid();
            Integer credentialid = credentialForm.getCredentialId();
            String credentialURL = credentialForm.getUrl();
            String credentialUsername = credentialForm.getUsername();
            String credentialPassword = credentialForm.getPassword();
            if (credentialid != null) {
                credentialid = credentialService.update(new Credential(credentialid, credentialURL, credentialUsername, null, credentialPassword, userid));
            } else {
                credentialid = credentialService.create(new Credential(null, credentialURL, credentialUsername, null, credentialPassword, userid));
            }

            if (credentialid < 0) {
                resultError = "Failed to create note";
            }
        }

        if (resultError == null) {
            model.addAttribute("resultSuccess", true);
        } else {
            model.addAttribute("resultError", resultError);
        }

        return "result";
    }

    @GetMapping("/delete/{credentialid}")
    public String deleteCredential(@PathVariable Integer credentialid, Model model) {
        String resultError = null;
        int result = credentialService.delete(credentialid);

        if (result < 0) {
            resultError = "Failed to delete credential";
        }

        if (resultError == null) {
            model.addAttribute("resultSuccess", true);
        } else {
            model.addAttribute("resultError", resultError);
        }

        return "result";
    }
}
