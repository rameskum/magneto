package com.demo.magneto.repositories;

import com.demo.magneto.entity.RememberMe;
import org.springframework.data.repository.CrudRepository;

public interface RememberMeRepositoryCrud extends CrudRepository<RememberMe, String>, RememberMeRepositoryCustom {

	void deleteByUsername(String userName);
}
