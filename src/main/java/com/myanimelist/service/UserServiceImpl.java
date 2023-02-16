package com.myanimelist.service;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myanimelist.authentication.UserPrincipal;
import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.repository.RoleRepository;
import com.myanimelist.repository.UserRepository;
import com.myanimelist.validation.entity.ValidUser;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final MailSenderServiceImpl mailSenderService;

	private final BCryptPasswordEncoder passwordEncoder;
	
	private final Environment env;

	@Lazy
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
						   MailSenderServiceImpl mailSenderService, BCryptPasswordEncoder passwordEncoder,
						   Environment env) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.mailSenderService = mailSenderService;
		this.passwordEncoder = passwordEncoder;
		this.env = env;
	}

	@Override
	public User find(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(ValidUser validUser) {
		User user = new User();

		user.setUsername(validUser.getUsername());
		user.setPassword(passwordEncoder.encode(validUser.getPassword()));
		user.setEmail(validUser.getEmail());
		user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
		user.setActivationCode(UUID.randomUUID().toString());

		try {
			userRepository.save(user);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username " + user.getUsername() + " already exists!");
		}

		String message = """
				Hello, %s!
				Welcome to MyAnimeList. Please, follow link to verify your account:
				%s/register/activate/%s,
				""".formatted(user.getUsername(), env.getProperty("host.domain"), user.getActivationCode());

		mailSenderService.send(user.getEmail(), "Activation code", message);
	}

	@Override
	public boolean activateUser(String code) {
		User user = userRepository.findByActivationCode(code);

		if (user != null) {
			user.setActivationCode(null);
			return true;
		}

		return false;
	}

	@Override
	public void uploadProfilePicture(byte[] bytes) {
		User user = userRepository.findByUsername(getAuthUsername());
		user.setImage(bytes);
	}

	@Override
	public byte[] getProfilePicture() {
		return userRepository.findByUsername(getAuthUsername()).getImage();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserPrincipal(user);
	}
	
	private String getAuthUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
