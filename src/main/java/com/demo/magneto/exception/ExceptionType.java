package com.demo.magneto.exception;

public enum ExceptionType {

	ENTITY_NOT_FOUND("not.found"),
	DUPLICATE_ENTITY("duplicate"),
	ILLEGAL_ARGUMENT("argument"),
	ENTITY_EXCEPTION("exception");

	String value;

	ExceptionType(String value) {
		this.value = value;
	}

	String getValue() {
		return this.value;
	}
}
