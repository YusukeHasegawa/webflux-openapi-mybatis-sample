package io.github.yusukehasegawa.openapi.web.api;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.github.yusukehasegawa.openapi.mapper.UserAMapper;
import io.github.yusukehasegawa.openapi.model.UserA;
import io.github.yusukehasegawa.openapi.service.UserService;
import io.github.yusukehasegawa.openapi.web.model.InlineResponse200;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UsersApiController implements UsersApi {

	private final UserAMapper userAMapper;

	private final UserService userService;

	@Override
	public ResponseEntity<InlineResponse200> user(@PathVariable("id") final Long id) {
		return Optional.ofNullable(getUser(id)).map(mapper -> {
			final InlineResponse200 res = new InlineResponse200();
			res.setId(mapper.getId());
			res.setName(mapper.getName());
			return ResponseEntity.ok(res);
		}).orElse(ResponseEntity.notFound().build());
	}

	private UserA getUser(final Long id) {
		// return userAMapper.selectByPrimaryKey(id);
		return userService.slow(id).block();
	}
}
