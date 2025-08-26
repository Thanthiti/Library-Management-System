package com.example.thanthiti.Library.Management.System.Repository;

import com.example.thanthiti.Library.Management.System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

}
