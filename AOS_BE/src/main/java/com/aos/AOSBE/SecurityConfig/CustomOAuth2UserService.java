package com.aos.AOSBE.SecurityConfig;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.management.relation.Role;

import com.aos.AOSBE.Entity.Roles;
import com.aos.AOSBE.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Repository.AccountsRepository;
import com.aos.AOSBE.Repository.AuthoritiesRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	@Autowired
	private AccountsRepository userRepository;
	@Autowired
	private AuthoritiesRepository authorityRepository;
	@Autowired
	private RolesRepository rolesRepository;
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		// Lấy email từ Google
		String email = oAuth2User.getAttribute("email");
		System.out.println(email);
		String picture = oAuth2User.getAttribute("picture");
		String fullname = oAuth2User.getAttribute("name");
		// Kiểm tra xem user có trong database không
		Optional<Accounts> user = userRepository.findByEmail(email);

		if (!user.isPresent()) {
			System.out.println("user null");
			Accounts userRegister = new Accounts();
			userRegister.setEmail(email);
			userRegister.setFullname(fullname);
			userRegister.setAvatar(picture);
			userRegister.setPassword(new BCryptPasswordEncoder().encode("123"));
			userRegister = userRepository.save(userRegister);
			Authorities auth = new Authorities();
			auth.setAccounts(userRegister);
			auth.setRoles(rolesRepository.findByName("USER").get());
			authorityRepository.save(auth);
		} else {
			System.out.println("user ko null");
			user.get().setFullname(fullname);
			user.get().setAvatar(picture);
			userRepository.save(user.get());
		}
		// Lấy role từ database
		List<Authorities> authorities = authorityRepository.findAllByEmail(email);
		Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
		for (Authorities authority : authorities) {
			grantedAuthoritySet.add(new SimpleGrantedAuthority(authority.getRoles().getName()));
		}
		System.out.println(authorities.get(0).getRoles().getName());
		// Tạo UsernamePasswordAuthenticationToken để đồng bộ với đăng nhập
		// Username/Password
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, // Username
				null, // Không có password vì dùng OAuth2
				grantedAuthoritySet // Quyền từ database
		);
		// Set authentication vào SecurityContext để sử dụng chung
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// Trả về OAuth2User (Spring yêu cầu trả về OAuth2User)
		return new DefaultOAuth2User(grantedAuthoritySet, oAuth2User.getAttributes(), "email");
	}
}
