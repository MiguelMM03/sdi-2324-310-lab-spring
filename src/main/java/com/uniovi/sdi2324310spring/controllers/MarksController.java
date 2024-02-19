package com.uniovi.sdi2324310spring.controllers;

import com.uniovi.sdi2324310spring.entities.Mark;
import com.uniovi.sdi2324310spring.entities.User;
import com.uniovi.sdi2324310spring.services.MarksService;
import com.uniovi.sdi2324310spring.services.UsersService;
import com.uniovi.sdi2324310spring.validators.MarksValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MarksController {
    // Inyectamos el servicio por inyección basada en constructor
    @Autowired
    private final MarksService marksService;
    @Autowired
    private final UsersService usersService;
    @Autowired
    private final MarksValidator marksValidator;
    private final HttpSession httpSession;
    public MarksController(MarksService marksService, UsersService usersService, MarksValidator marksValidator, HttpSession httpSession) {
        this.marksService = marksService;
        this.usersService = usersService;
        this.marksValidator = marksValidator;
        this.httpSession = httpSession;
    }
    @RequestMapping("/mark/list")
    public String getList(Model model, Pageable pageable, @RequestParam(value="",required = false) String searchText){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks;
        if(searchText!=null && !searchText.isEmpty()){
            marks=marksService.searchMarksByDescriptionAndNameForUser(pageable,searchText,user);
        }else{
            marks=marksService.getMarksForUser(pageable,user);
        }
        model.addAttribute("markList", marks.getContent());
        model.addAttribute("page", marks);
        return "mark/list";
    }
    @RequestMapping("/mark/list/update")
    public String updateList(Model model,Pageable pageable, Principal principal) {
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks=marksService.getMarksForUser(pageable,user);
        model.addAttribute("markList", marks.getContent());
        return "mark/list :: marksTable";
    }
    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@Validated Mark mark, BindingResult result, Model model) {
        marksValidator.validate(mark,result);
        marksService.addMark(mark);
        if(result.hasErrors()){
            model.addAttribute("usersList", usersService.getUsers());
            model.addAttribute("mark", mark);
            return "/mark/add";
        }
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/add")
    public String getMark(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        model.addAttribute("mark", new Mark() );
        return "mark/add";
    }
    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id){
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }
    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        Mark originalMark = marksService.getMark(id);
        // modificar solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/"+id;
    }

    @RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
    public String setResendTrue(@PathVariable Long id) {
        marksService.setMarkResend(true, id);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
    public String setResendFalse(@PathVariable Long id) {
        marksService.setMarkResend(false, id);
        return "redirect:/mark/list";
    }
}
