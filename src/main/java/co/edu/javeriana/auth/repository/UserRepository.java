package co.edu.javeriana.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.javeriana.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}