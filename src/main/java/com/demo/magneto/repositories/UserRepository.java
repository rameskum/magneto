package com.demo.magneto.repositories;

import com.demo.magneto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(final String userName);

	boolean existsByUserName(final String userName);

	void deleteByUserName(final String userName);

	long countByActive(boolean active);
}
