package com.aos.AOSBE.SecurityConfig;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Repository.AuthoritiesRepository;
import com.aos.AOSBE.Service.AccountsService;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private AccountsService accountsService;
	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username);
		Optional<Accounts> user = accountsService.accountsFindByEmail(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("Không tìm thấy Username: " + username);
		}
		List<Authorities> authorities = authoritiesRepository.findAllByEmail(username);
		Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
		for (Authorities authority : authorities) {
			grantedAuthoritySet.add(new SimpleGrantedAuthority(authority.getRoles().getName()));

		}
		return new CustomUserDetails(user.get(), grantedAuthoritySet);
	}
}
