package com.CompD.A.Compage.Controller;

import com.CompD.A.Compage.DTO.SosPostDTO;
import com.CompD.A.Compage.Service.SosPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Sos")
public class SosController {
    @Autowired
    private SosPostService sosPostService;

    @GetMapping
    public String list(Model model){
        List<SosPostDTO> sosPostDTOList = sosPostService.getPostList();
        model.addAttribute("postList",sosPostDTOList);

        return"Sos/list.html";
    }

    @GetMapping("/post")
    public String post(){
        return"Sos/post";
    }

    @PostMapping("/post")
    public String write(SosPostDTO sosPostDTO){
        sosPostService.savePost(sosPostDTO);
        return "Sos/list.html";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id")Long id,Model model){
        SosPostDTO sosPostDTO = sosPostService.getPost(id);
        model.addAttribute("post", sosPostDTO);

        return "Sos/post/detail.html";
    }
}
