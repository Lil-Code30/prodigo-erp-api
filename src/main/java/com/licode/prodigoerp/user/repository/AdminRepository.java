package com.licode.prodigoerp.user.repository;

import com.licode.prodigoerp.user.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

    List<User> findAllByIsSuperAdmin(Boolean isSuperAdmin);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :adminId")
    void changeAdminStatus(@NotBlank Long adminId,  @NotBlank String status);
}
