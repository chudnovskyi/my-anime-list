package com.myanimelist.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import javax.validation.Payload;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmailValidatorTest {

	@InjectMocks
	private EmailValidator emailValidator;

	@ParameterizedTest(name = "{index} => email={0}, domain={1}, isValid={2}")
	@MethodSource(value = {
			"noDomainEmails",
			"singleDomainEmails",
			"multipleDomainEmails"
	})
	void isEmailValid(String email, String[] domains, Boolean isValid) {
		emailValidator.initialize(new EmailImpl(domains));

		assertThat(emailValidator.isValid(email, null)).isEqualTo(isValid);
	}

	static Stream<Arguments> noDomainEmails() {
		String[] domains = new String[] { "" };
		return Stream.of(
			Arguments.of("user@example.com", domains, true),
			Arguments.of("user.name@example.com", domains, true),
			Arguments.of("user123@example.com", domains, true),
			Arguments.of("u_s_e_r@example.co.jp", domains, true),
			Arguments.of("user@example.com.au", domains, true),
			Arguments.of("user@subdomain.example.com", domains, true),
			Arguments.of("user@127.0.0.1", domains, true),
			Arguments.of("user@[127.0.0.1]", domains, true),
			Arguments.of("user@", domains, false),
			Arguments.of("user@localhost", domains, false),
			Arguments.of("user@example", domains, false),
			Arguments.of("user@.example.com", domains, false),
			Arguments.of("user@-example.com", domains, false),
			Arguments.of("user@example..com", domains, false)
		);
	}

	static Stream<Arguments> singleDomainEmails() {
		String[] domains = new String[] { "gmail.com" };
		return Stream.of(
			Arguments.of("user@gmail.com", domains, true),
			Arguments.of("user.name@gmail.com", domains, true),
			Arguments.of("user123@gmail.com", domains, true),
			Arguments.of("u_s_e_r@gmail.co.jp", domains, false),
			Arguments.of("user@gmail.com.au", domains, false),
			Arguments.of("user@subdomain.gmail.com", domains, true),
			Arguments.of("user@com", domains, false),
			Arguments.of("user@mail.com", domains, false),
			Arguments.of("@gmail.com", domains, false),
			Arguments.of("gmail.comgmail.comgmail.com@gmail.com.gmail.com", domains, true),
			Arguments.of("user@gmail.comm", domains, false)
		);
	}

	static Stream<Arguments> multipleDomainEmails() {
		String[] domains = new String[] { "gmail.com", "karazin.ua" };
		return Stream.of(
			Arguments.of("chudnovskyi_0@sub.karazin.ua", domains, true),
			Arguments.of("chudnovskyi_0@sub.gmail.com", domains, true),
			Arguments.of("chudnovskyi_0@sub.yahoo.com", domains, false),
			Arguments.of("useerafds.fdssfd_sdfsfdr@exaasfd.karazin.ua", domains, true),
			Arguments.of("", domains, false),
			Arguments.of("@", domains, false),
			Arguments.of("@example.co.jp", domains, false),
			Arguments.of("sub.karazin.ua@sub.karazin.ua", domains, true),
			Arguments.of("user@user", domains, false),
			Arguments.of("user@karazin", domains, false),
			Arguments.of("user@razin.ua", domains, false)
		);
	}
	
	@Test
	void passIfValueIsNull() {
		assertThat(emailValidator.isValid(null, null)).isTrue();
	}

	record EmailImpl(String[] domains) implements Email {

		@Override
		public String message() {
			return null;
		}

		@Override
		public Class<?>[] groups() {
			return null;
		}

		@Override
		public Class<? extends Payload>[] payload() {
			return null;
		}

		@Override
		public Class<? extends Annotation> annotationType() {
			return null;
		}
	}
}
