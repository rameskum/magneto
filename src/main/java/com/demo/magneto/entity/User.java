package com.demo.magneto.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(indexes = {
		@Index(name = "IDX_USER_NAME", columnList = "userName", unique = true)
})
public class User extends AbstractPersistable<Long> {

	@Column(nullable = false, unique = true)
	@Email(message = "{errors.invalid_email}")
	@NotEmpty
	private String userName;

	@NotEmpty
	@Column(nullable = false)
	private String password;

	@NotEmpty
	@Column(nullable = false)
	private String firstName;

	@NotEmpty
	@Column(nullable = false)
	private String lastName;

	private boolean active;

	private String authorities = "";

	public Stream<String> getAuthoritiesStream() {
		if (authorities.length() > 0) {
			return Arrays.stream(authorities.split(","));
		}
		return Stream.empty();
	}
}
