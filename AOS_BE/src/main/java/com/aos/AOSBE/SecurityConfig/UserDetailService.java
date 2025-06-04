package com.aos.AOSBE.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Service.AccountsService;


@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private AccountsService accountsService; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Accounts user = accountsService.accountsFindByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy Username: " + username);
        }
        return new CustomUserDetails(user);
    }
}
