package com.rakuten.ross.aurora.domain;

import com.rakuten.ross.aurora.core.layer.DomainModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements DomainModel {

	// username is the unique  user identifier which can communicate with all application in aurora's.
	private final String username;

	public static User mock() {
		return new User("aurora-super-admin");
	}
}
