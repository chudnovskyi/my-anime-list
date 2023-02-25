package com.myanimelist.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.myanimelist.authentication.UserPrincipal;
import com.myanimelist.entity.Role;
import com.myanimelist.entity.User;
import com.myanimelist.exception.UsernameAlreadyExistsException;
import com.myanimelist.repository.RoleRepository;
import com.myanimelist.repository.UserRepository;
import com.myanimelist.validation.entity.ValidUser;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private RoleRepository roleRepository;
	@Mock
	private MailSenderServiceImpl mailSenderService;
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	@Mock
	private Environment environment;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;

	@BeforeEach
	public void setUp() {
		user = getUser();
	}

	@Test
	void findSuccess() {
		doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

		User actualResult = userService.find(user.getUsername());

		assertThat(actualResult).isNotNull();
		assertThat(actualResult).isEqualTo(user);
	}

	@Test
	void findFail() {
		assertAll(
				() -> assertThrows(EntityNotFoundException.class, () -> userService.find(null)),
				() -> assertThrows(EntityNotFoundException.class, () -> userService.find("dummy")),
				() -> assertThrows(EntityNotFoundException.class, () -> userService.find(""))
		);
	}

	@Test
	void saveSuccess() {
		ValidUser validUser = getValidUser();

		when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(getRole()));
		when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword() + "_encoded");

		userService.save(validUser);

		verify(roleRepository).findByName("ROLE_USER");
		verify(passwordEncoder).encode(user.getPassword());
	}

	@Test
	void saveFail() {
		ValidUser validUser = getValidUser();

		when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(getRole()));
		when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Username already exists"));

		var exception = assertThrows(UsernameAlreadyExistsException.class, () -> userService.save(validUser));
		String expectedMessage = String.format("Username %s already exists!", user.getUsername());
		assertThat(exception.getMessage()).isEqualTo(expectedMessage);
	}

	@Test
	void activateUserSuccess() {
		doReturn(Optional.of(user)).when(userRepository).findByActivationCode(user.getActivationCode());

		assertThat(userService.activateUser(user.getActivationCode())).isTrue();
	}

	@Test
	void activateUserFail() {
		doReturn(Optional.empty()).when(userRepository).findByActivationCode("dummy");

		assertThat(userService.activateUser("dummy")).isFalse();
	}

	@Test
	void testUploadProfilePicture() {
		authenticate();

		doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

		byte[] bytes = { 1, 2, 3, 4, 5 };

		userService.uploadProfilePicture(bytes);

		assertThat(user.getImage()).isEqualTo(bytes);

		verify(userRepository).findByUsername(user.getUsername());
	}

	@Test
	void getProfilePictureSuccess() {
		authenticate();

		doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

		assertThat(userService).returns(user.getImage(), t -> t.getProfilePicture());
	}

	@Test
	void getProfilePictureFail() {
		authenticate();

		doReturn(Optional.empty()).when(userRepository).findByUsername(user.getUsername());

		assertThrows(EntityNotFoundException.class, () -> userService.getProfilePicture());
	}

	@Test
	void loadUserByUsernameSuccess() {
		doReturn(Optional.of(user)).when(userRepository).findByUsername(user.getUsername());

		UserDetails result = userService.loadUserByUsername(user.getUsername());

		assertThat(result).isEqualTo(new UserPrincipal(user));
	}

	@Test
	void loadUserByUsernameFail() {
		doReturn(Optional.empty()).when(userRepository).findByUsername(anyString());

		assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("dummy"));
	}

	private void authenticate() {
		Authentication authentication = mock(Authentication.class);

		when(authentication.getName()).thenReturn(user.getUsername());
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
	}
	
	private ValidUser getValidUser() {
		ValidUser validUser = new ValidUser();
		validUser.setUsername(user.getUsername());
		validUser.setPassword(user.getPassword());
		validUser.setEmail(user.getEmail());
		return validUser;
	}

	public User getUser() {
		User user = new User();
		user.setId(1);
		user.setUsername("dummy");
		user.setEmail("user@gmail.com");
		user.setPassword("$2a$12$kUawd9Xz7u5lTd.9mrEvQ.Wyg3ft/Z1lyNaw..XZkjZbjcpyXrglC");
		user.setActivationCode("activate");
		user.setReviews(Collections.emptyList());
		user.setRoles(Arrays.asList(getRole()));
		return user;
	}

	private Role getRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("ROLE_USER");
		return role;
	}
}
