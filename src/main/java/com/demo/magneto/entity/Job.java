package com.demo.magneto.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
//@Entity
@Table(indexes = {
		@Index(name = "IDX_NAME", columnList = "name", unique = true)
})
public class Job extends AbstractPersistable<Long> {

	@Column(nullable = false)
	private String name;
	private String description;

	@Column(nullable = false)
	private JobType type;

	@Column(nullable = false)
	private String script;

	private String handlers;

	private String cronExpr;

	private String uniqueIdentifier;

	private String resolutionSteps;

	private boolean auto;
	private boolean enabled;
}