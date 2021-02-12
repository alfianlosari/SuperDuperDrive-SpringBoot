package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping()
    public String noteUpdated(Authentication authentication, @ModelAttribute NoteForm noteForm, Model model) {
        String resultError = null;

        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user == null) {
            resultError = "User doesn't exist";
        }

        if (resultError == null) {
            Integer noteid = noteForm.getNoteid();
            String notetitle = noteForm.getNotetitle();
            String notedescription = noteForm.getNotedescription();
            if (noteid != null) {
                noteid = noteService.update(new Note(noteid, notetitle, notedescription, null));
            } else {
                noteid = noteService.createNote(new Note(null, notetitle, notedescription, user.getUserid()));
            }
            if (noteid < 0) {
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

    @GetMapping("/delete/{noteid}")
    public String deleteNote(@PathVariable Integer noteid, Model model) {
        String resultError = null;
        int result = noteService.delete(noteid);

        if (result < 0) {
            resultError = "Failed to delete note";
        }

        if (resultError == null) {
            model.addAttribute("resultSuccess", true);
        } else {
            model.addAttribute("resultError", resultError);
        }

        return "result";
    }
}
