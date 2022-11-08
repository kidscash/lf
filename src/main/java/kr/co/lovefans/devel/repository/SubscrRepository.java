package kr.co.lovefans.devel.repository;

import kr.co.lovefans.devel.domain.CreatorInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscrRepository extends JpaRepository<CreatorInfoDto, Integer> {
}
