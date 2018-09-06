package com.pickme.anomality.controller;


import com.pickme.anomality.dao.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Component
public class ResponseController implements ErrorController {



    @Autowired ResponseRepository repository;


    @GetMapping("/")
    public String index(final Model model) {

      /*  model.addAttribute("repositories" , repository.findAll());



        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repository.findAll();
            }
        }, 0, 10000);

       for (Response res : repository.findAll()){
            System.out.println(res.getTrasection_id()+"\n");

            System.out.println(res.getCreated_time()+"\n");

            System.out.printf(res.getStatus());

        }*/
        return "index";
    }

    @Override
    public String getErrorPath() {
        return "ERROR";
    }
}
