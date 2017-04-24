package com.altomni.apn.configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomerRoutingDataSource extends AbstractRoutingDataSource {
	private static final String authorityPrefix = "ROLE_";

	@Override
	protected Object determineCurrentLookupKey() {
		Set<String> authorities = getAuthoritiesOfCurrentUser();

		if (authorities.contains(authorityPrefix + CustomerType.t2.toString())) {
			return CustomerType.t2;
		} else if (authorities.contains(authorityPrefix + CustomerType.t3.toString())) {
			return CustomerType.t3;
		} else {
			return CustomerType.t1;
		}
	}

	private Set<String> getAuthoritiesOfCurrentUser() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return Collections.emptySet();
		}
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		return AuthorityUtils.authorityListToSet(authorities);
	}

}
