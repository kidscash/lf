package kr.co.lovefans.devel.service;


import kr.co.lovefans.devel.domain.CreatorPostDto;
import kr.co.lovefans.devel.dto.PostDto;
import kr.co.lovefans.devel.repository.CreatorPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CreatorPostService {

    @Autowired
    private final CreatorPostRepository creatorPostRepository;

    public CreatorPostService(CreatorPostRepository creatorPostRepository) {
        this.creatorPostRepository = creatorPostRepository;
    }

    public List<CreatorPostDto> findBycpMiSeq(Long cpMiSeq){
        List<CreatorPostDto> list = creatorPostRepository.findBycpMiSeq(cpMiSeq);

        return list;
    }

    public Slice<CreatorPostDto> findSliceBycpMiSeq(Long cpMiSeq,int page){

        PageRequest pageRequest = PageRequest.of(page,3,Sort.Direction.DESC,"cpRegdt");
        Slice<CreatorPostDto> post = creatorPostRepository.findSliceBycpMiSeq(cpMiSeq, pageRequest);
        return post;

    };

    public List<PostDto> findPostByCpMiSeq(Long slvmiseq){
        List<PostDto> post = creatorPostRepository.findPostByCpMiSeq(slvmiseq);
        return post;
    }




}
