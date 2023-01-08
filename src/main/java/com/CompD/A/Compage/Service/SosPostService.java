package com.CompD.A.Compage.Service;


import com.CompD.A.Compage.DTO.SosPostDTO;
import com.CompD.A.Compage.Entity.SosPost;
import com.CompD.A.Compage.Repository.SosPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SosPostService {
    @Autowired
    private SosPostRepository sosPostRepository;

    @Transactional
    public Long savePost(SosPostDTO sosPostDTO){
        SosPost sosPost = sosPostRepository.save(sosPostDTO.toEntity());
        return sosPost.getId();
    }

    @Transactional
    public List<SosPostDTO> getPostList(){
        List<SosPost> postList = sosPostRepository.findAll();
        List<SosPostDTO> postDtoList = new ArrayList<>();

        for(SosPost sosPost : postList){
            SosPostDTO sosPostDTO = SosPostDTO.builder()
                    .id(sosPost.getId())
                    .author(sosPost.getAuthor())
                    .title(sosPost.getTitle())
                    .content(sosPost.getContent())
                    .createdDate(sosPost.getCreatedDate())
                    .build();
            postDtoList.add(sosPostDTO);
        }
        return postDtoList;
    }

    @Transactional
    public SosPostDTO getPost(Long id){
        SosPost sosPost = sosPostRepository.findById(id).get();

        SosPostDTO sosPostDTO = SosPostDTO.builder()
                .id(sosPost.getId())
                .author(sosPost.getAuthor())
                .title(sosPost.getTitle())
                .content(sosPost.getContent())
                .createdDate(sosPost.getCreatedDate())
                .build();

        return sosPostDTO;
    }

}
