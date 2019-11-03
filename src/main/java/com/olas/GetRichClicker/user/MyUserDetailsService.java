package com.olas.GetRichClicker.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService 
{
	@Autowired
    private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		boolean accountEnabled = true;
		boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
		
		UserModel userModel = userRepository.getUserByUsername(username);
		if (userModel == null) 
		{
			throw new UsernameNotFoundException("No user found with username: '"+ username+"'.");
		}
		List<String> roles = userRepository.getUserRolesByUsername(username);
		if(roles.size() == 0)
		{
			accountNonLocked = false;
		}
		
        return  new org.springframework.security.core.userdetails.User
                (
                		userModel.getUsername(), 
                		userModel.getPassword(),
                		accountEnabled, 
                		accountNonExpired, 
                		credentialsNonExpired, 
                		accountNonLocked, 
                		getAuthorities(roles));
	}
	
	private static List<GrantedAuthority> getAuthorities (List<String> roles)
	{
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) 
        {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
