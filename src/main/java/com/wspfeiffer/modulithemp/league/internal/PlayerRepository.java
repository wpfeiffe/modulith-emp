package com.wspfeiffer.modulithemp.league.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>
{
    Page<Player> findByFullName(String fullName, Pageable p);
    Page<Player> findByPosition(String position, Pageable p);
}
