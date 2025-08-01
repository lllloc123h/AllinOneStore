package com.aos.AOSBE.SecurityConfig;

import java.util.*;

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
import com.aos.AOSBE.Repository.RolesRepository;
import org.springframework.web.client.RestTemplate;

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
		String picture= null;
		String registrationId = userRequest.getClientRegistration().getRegistrationId(); // facebook, google
		if ("facebook".equals(registrationId)) {
			// Gọi Graph API để lấy ảnh rõ ràng
			String accessToken = userRequest.getAccessToken().getTokenValue();
			String uri = "https://graph.facebook.com/me?fields=id,name,email,picture.type(large)&access_token=" + accessToken;

			try {
				RestTemplate restTemplate = new RestTemplate();
				Map<String, Object> fbUser = restTemplate.getForObject(uri, Map.class);

				if (fbUser != null && fbUser.containsKey("picture")) {
					Map<String, Object> pictureObj = (Map<String, Object>) fbUser.get("picture");
					Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
					picture = (String) dataObj.get("url");
					System.out.println("✅ Facebook avatar: " + picture);
				} else {
					System.out.println("⚠️ Không tìm thấy picture từ Facebook");
				}
			} catch (Exception e) {
				System.out.println("❌ Lỗi gọi Facebook Graph API: " + e.getMessage());
			}

		} else {
			picture = oAuth2User.getAttribute("picture"); // Google, etc.
		}		String fullname = oAuth2User.getAttribute("name");
		// Kiểm tra xem user có trong database không
		Optional<Accounts> user = userRepository.findByEmail(email);

		if (!user.isPresent()) {
			System.out.println("user null");
			Accounts userRegister = new Accounts();
			userRegister.setEmail(email);
			userRegister.setFullname(fullname);
			userRegister.setAvatarUrl(picture);
			userRegister.setPassword(new BCryptPasswordEncoder().encode("123"));
			userRegister = userRepository.save(userRegister);
			Authorities auth = new Authorities();
			auth.setAccounts(userRegister);
			auth.setRoles(rolesRepository.findByName("USER").get());
			authorityRepository.save(auth);
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
