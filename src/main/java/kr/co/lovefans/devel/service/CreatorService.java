package kr.co.lovefans.devel.service;

import kr.co.lovefans.devel.domain.CreatorInfoDto;
import kr.co.lovefans.devel.domain.MemberInfoDto;
import kr.co.lovefans.devel.repository.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class CreatorService {

    @Autowired private final CreatorRepository creatorRepository;


    public CreatorService(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    public int join(CreatorInfoDto creatorInfo) {
        validateDuplicatePageName(creatorInfo);
        creatorRepository.save(creatorInfo);
        return creatorInfo.getCiMiSeq();
    }

    private void validateDuplicatePageName(CreatorInfoDto creatorInfo) {
        creatorRepository.findByPageNm(creatorInfo.getCiPageNm())
                .ifPresent(c -> {
                    throw new IllegalStateException("이미 존재하는 페이지입니다.");
                });
    }

    public List<CreatorInfoDto> findCreatorInfo() {
        return creatorRepository.findAll();
    }

    public Optional<CreatorInfoDto> findOne(int ciMiSeq) {
        return creatorRepository.findBySeq(ciMiSeq);
    }

    public Optional<MemberInfoDto> findOneMemInfo(int miSeq) {
        return creatorRepository.findMemInfoBySeq(miSeq);
    }
}
