package com.myanimelist.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

import javax.validation.Payload;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FieldMatchValidatorTest {
	
	@InjectMocks
	private FieldMatchValidator fieldMatchValidator;

	@ParameterizedTest(name = "{index} => ''{0}'' == ''{1}'' - true")
	@MethodSource(value = "fieldsMatch")
	void doFieldsMatch(String first, String second) {
		fieldMatchValidator.initialize(new FieldMatchImpl("firstField", "secondField"));
		
		assertThat(fieldMatchValidator.isValid(new FieldMatchImpl(first, second), null)).isTrue();
	}

	static Stream<Arguments> fieldsMatch() {
		return Stream.of(
			Arguments.of("pass", "pass"),
			Arguments.of("", ""),
			Arguments.of(" ", " "),
			Arguments.of(" 11", " 11"),
			Arguments.of(" 11 ", " 11 "),
			Arguments.of(null, null) // null is true because filds ain't necessary
		);
	}
	
	@ParameterizedTest(name = "{index} => ''{0}'' = ''{1}'' - false")
	@MethodSource(value = "fieldsNotMatch")
	void doFieldsNotMatch(String first, String second) {
		fieldMatchValidator.initialize(new FieldMatchImpl("firstField", "secondField"));
		
		assertThrows(NullPointerException.class, () -> fieldMatchValidator.isValid(new FieldMatchImpl(first, second), null));
	}

	static Stream<Arguments> fieldsNotMatch() {
		return Stream.of(
			Arguments.of("1", "2"),
			Arguments.of("", " "),
			Arguments.of("   ", "login"),
			Arguments.of("password", "пароль"),
			Arguments.of(null, "dummy"),
			Arguments.of("dummy", null),
			Arguments.of("i", "і") // english - ukrainian
		);
	}

	record FieldMatchImpl(String firstField, String secondField) implements FieldMatch {

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
