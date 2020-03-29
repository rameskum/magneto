package com.demo.magneto.exception;

import com.demo.magneto.config.MessageProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Component
public class ExceptionHandler {

	private MessageProperties messagePropertyConfig;

	public ExceptionHandler(MessageProperties messagePropertyConfig) {
		this.messagePropertyConfig = messagePropertyConfig;
	}

	private RuntimeException throwException(ExceptionType exceptionType, String messageTemplate, String... args) {
		if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
			return new EntityNotFoundException(format(messageTemplate, args));
		} else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
			return new DuplicateEntityException(format(messageTemplate, args));
		}
		return new RuntimeException(format(messageTemplate, args));
	}

	private String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
		return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
	}

	private String format(String template, String... args) {
		Optional<String> templateContent = Optional.ofNullable(messagePropertyConfig.getConfigValue(template));
		return templateContent.map(s -> MessageFormat.format(s, args)).orElseGet(() -> String.format(template, args));
	}

	public RuntimeException throwException(String messageTemplate, String... args) {
		return new RuntimeException(format(messageTemplate, args));
	}

	public RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
		String messageTemplate = getMessageTemplate(entityType, exceptionType);
		return throwException(exceptionType, messageTemplate, args);
	}

	public RuntimeException throwExceptionWithId(EntityType entityType, ExceptionType exceptionType, String id, String... args) {
		String messageTemplate = getMessageTemplate(entityType, exceptionType).concat(".").concat(id);
		return throwException(exceptionType, messageTemplate, args);
	}

	public static class EntityNotFoundException extends RuntimeException {
		public EntityNotFoundException(String message) {
			super(message);
		}
	}

	public static class DuplicateEntityException extends RuntimeException {
		public DuplicateEntityException(String message) {
			super(message);
		}
	}
}
