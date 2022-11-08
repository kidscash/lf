package kr.co.lovefans.devel.service;

import kr.co.lovefans.devel.domain.CreatorInfoDto;
import kr.co.lovefans.devel.repository.SubscrRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
@Service
public class SubscrService {

    private final SubscrRepository subscrRepository;

    public SubscrService(SubscrRepository subscrRepository) {
        this.subscrRepository = subscrRepository;

    }

    public List<CreatorInfoDto> findAll() {
        return subscrRepository.findAll();
    }
}
