package com.uniovi.sdi2324310spring.services;

import com.uniovi.sdi2324310spring.entities.Mark;
import com.uniovi.sdi2324310spring.entities.Professor;
import com.uniovi.sdi2324310spring.repositories.ProfessorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorsService {
    @Autowired
    private ProfessorsRepository professorsRepository;

    public List<Professor> getProfessorsList() {
        List<Professor> professors = new ArrayList<>();
        professorsRepository.findAll().forEach(professors::add);
        return professors;
    }

    public Professor getProfessor(Long id){
        return professorsRepository.findById(id).get();
    }
    public void addProfessor(Professor professor){
        professorsRepository.save(professor);
    }
    public void deleteProfessor(Long id){
        professorsRepository.deleteById(id);
    }
    public void editProfessor(Professor professor){
        Optional<Professor> p=professorsRepository.findById(professor.getId());
        if(p.isPresent()){
            professorsRepository.save(professor);
        }
    }
}
