package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.validations.ZooKangarooValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init(){
        kangaroos =new HashMap<>();
    }
    @GetMapping
    public List<Kangaroo> find(){
    return this.kangaroos.values().stream().toList();
    }
    @GetMapping("/{id}")
    public Kangaroo find(@PathVariable("id") Integer id){
        ZooKangarooValidation.isIdValid(id);
        ZooKangarooValidation.checkKangarooExistence(kangaroos,id,true);
        return kangaroos.get(id);
    }
    @PostMapping
    public  Kangaroo save(@RequestBody Kangaroo kangroo){
        ZooKangarooValidation.checkKangarooExistence(kangaroos,kangroo.getId(),false);
        ZooKangarooValidation.checkKangrooWeight(kangroo.getWeight());
        kangaroos.put(kangroo.getId(),kangroo);
        return kangaroos.get(kangroo.getId());
    }
    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable Integer id,@RequestBody Kangaroo kangaroo){
        ZooKangarooValidation.isIdValid(id);
        ZooKangarooValidation.checkKangrooWeight(kangaroo.getWeight());
        kangaroo.setId(id);
        if (kangaroos.containsKey(id)){
            kangaroos.put(id,kangaroo);
            return kangaroos.get(id);
        }
        else {
        return save(kangaroo);
        }
    }
    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable Integer id){
        ZooKangarooValidation.isIdValid(id);
        ZooKangarooValidation.checkKangarooExistence(kangaroos,id,true);
        return kangaroos.remove(id);
    }
}

