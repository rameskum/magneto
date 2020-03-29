package com.demo.magneto.repositories.impl;

import com.demo.magneto.entity.RememberMe;
import com.demo.magneto.repositories.RememberMeRepositoryCrud;
import com.demo.magneto.repositories.RememberMeRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Transactional
@Component
public class RememberMeRepositoryCustomImpl implements RememberMeRepositoryCustom {

	@Autowired
	@Lazy
	private RememberMeRepositoryCrud rememberMeRepository;

	@Override
	public void createNewToken(PersistentRememberMeToken toke) {
		RememberMe rememberMe = RememberMe.builder()
				.username(toke.getUsername())
				.series(toke.getSeries())
				.token(toke.getTokenValue())
				.lastUsed(toke.getDate())
				.build();
		this.rememberMeRepository.save(rememberMe);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsedDate) {
		Optional<RememberMe> login = this.rememberMeRepository.findById(series);
		if (login.isPresent()) {
			RememberMe rememberMe = login.get();
			rememberMe.setToken(tokenValue);
			rememberMe.setLastUsed(lastUsedDate);
		}
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		Optional<RememberMe> id = this.rememberMeRepository.findById(seriesId);
		if (id.isPresent()) {
			RememberMe logins = id.get();
			return new PersistentRememberMeToken(logins.getUsername(),
					logins.getSeries(), logins.getToken(), logins.getLastUsed());
		}
		return null;
	}

	@Override
	public void removeUserTokens(String userName) {
		this.rememberMeRepository.deleteByUsername(userName);
	}
}
