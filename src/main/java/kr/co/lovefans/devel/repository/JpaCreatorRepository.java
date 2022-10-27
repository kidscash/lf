package kr.co.lovefans.devel.repository;

import kr.co.lovefans.devel.domain.CreatorInfoDto;
import kr.co.lovefans.devel.domain.MemberInfoDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaCreatorRepository implements CreatorRepository {

    private final EntityManager em;

    public JpaCreatorRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public CreatorInfoDto save(CreatorInfoDto creatorInfo) {
        em.persist(creatorInfo);

        return creatorInfo;
    }

    @Override
    public Optional<CreatorInfoDto> findBySeq(int ciMiSeq) {
        CreatorInfoDto creatorInfo = em.find(CreatorInfoDto.class, ciMiSeq);

        return Optional.ofNullable(creatorInfo);
    }

    @Override
    public Optional<MemberInfoDto> findMemInfoBySeq(int miSeq) {
        MemberInfoDto memberInfo = em.find(MemberInfoDto.class, miSeq);

        return Optional.ofNullable(memberInfo);
    }

    @Override
    public Optional<CreatorInfoDto> findByPageNm(String ciPageNm) {
        List<CreatorInfoDto> result = em.createQuery("select c from CreatorInfoDto c where c.ciPageNm = :ciPageNm", CreatorInfoDto.class)
                .setParameter("ciPageNm", ciPageNm)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<CreatorInfoDto> findAll() {
        return em.createQuery("select c from CreatorInfoDto c", CreatorInfoDto.class)
                .getResultList();
    }
}
