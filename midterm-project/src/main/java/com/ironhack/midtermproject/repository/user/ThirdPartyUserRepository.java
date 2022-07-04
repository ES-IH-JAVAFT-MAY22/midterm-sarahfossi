package com.ironhack.midtermproject.repository.user;

import com.ironhack.midtermproject.model.user.ThirdPartyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyUserRepository extends JpaRepository<ThirdPartyUser, Long> {
}

