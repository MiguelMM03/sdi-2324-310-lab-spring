package com.uniovi.sdi2324310spring.controllers;

import com.uniovi.sdi2324310spring.entities.Professor;
import com.uniovi.sdi2324310spring.services.ProfessorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorsController {

    @Autowired
    private ProfessorsService professorsService;

    @RequestMapping("/professor/list")
    public String getList(Model model) {
        return professorsService.getProfessorsList().toString();
    }
    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professorsService.addProfessor(professor);
        return professor.toString();
    }
    @RequestMapping("/professor/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return professorsService.getProfessor(id).toString();
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id){
        professorsService.deleteProfessor(id);
        return "Professor deleted";
    }
    @RequestMapping(value = "/professor/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("professor", professorsService.getProfessor(id));
        return "professor/edit";
    }
    @RequestMapping(value="/professor/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Professor professor, @PathVariable Long id){
        professor.setId(id);
        professorsService.editProfessor(professor);
        return "Professor edited";
    }
}
