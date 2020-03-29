package com.demo.magneto.entity;


import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class RememberMe {

	@Id
	private String series;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private Date lastUsed;
}
