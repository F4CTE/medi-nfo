package com.b3al.med.medi_nfo.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);

    Page<User> findAllById(Long id, Pageable pageable);

    boolean existsByUsernameIgnoreCase(String username);

}
