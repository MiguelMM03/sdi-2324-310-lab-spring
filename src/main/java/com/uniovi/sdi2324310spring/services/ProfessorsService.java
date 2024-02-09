package com.uniovi.sdi2324310spring.services;

import com.uniovi.sdi2324310spring.entities.Professor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorsService {
    private List<Professor> professorsList=new ArrayList<>();
    @PostConstruct
    public void init(){
        professorsList.add(new Professor(1L, "123445678A", "Jaime", "García", "Titular"));
        professorsList.add(new Professor(2L, "87654321A", "Lucas", "Vazquez", "Ayudante"));
    }

    public List<Professor> getProfessorsList() {
        return professorsList;
    }

    public Professor getProfessor(Long id){
        for(Professor p: professorsList){
            if(p.getId().equals(id)){
                return p;
            }
        }
        return null;
    }
    public void addProfessor(Professor professor){
        if(professor.getId()==null){
            professor.setId(professorsList.get(professorsList.size()-1).getId()+1);
        }
        professorsList.add(professor);
    }
    public void deleteProfessor(Long id){
        professorsList.removeIf(professor -> professor.getId().equals(id));
    }
}
