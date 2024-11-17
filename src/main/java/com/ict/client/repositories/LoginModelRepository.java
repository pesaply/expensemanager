package com.ict.client.repositories;

import com.ict.client.models.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LoginModelRepository extends JpaRepository<LoginModel, String> {
    /**
     * This Repository handles all the updates to the Login Model
     */
    @Query("SELECT l FROM LoginModel l WHERE l.email=?1")
    Optional<LoginModel> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM LoginModel l WHERE l.email=?1")
    void deleteByEmail(String email);
}
