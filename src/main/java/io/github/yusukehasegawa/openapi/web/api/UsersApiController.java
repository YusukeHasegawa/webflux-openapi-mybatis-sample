package io.github.yusukehasegawa.openapi.web.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.github.yusukehasegawa.openapi.mapper.UserAMapper;
import io.github.yusukehasegawa.openapi.model.UserA;
import io.github.yusukehasegawa.openapi.model.UserAExample;
import io.github.yusukehasegawa.openapi.service.UserService;
import io.github.yusukehasegawa.openapi.web.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UsersApiController implements UsersApi {

	private final UserAMapper userAMapper;

	private final UserService userService;

	@Override
	public ResponseEntity<User> user(@PathVariable("id") final Long id) {
		return Optional.ofNullable(getUser(id)).map(mapper -> {
			final User res = new User();
			res.setId(mapper.getId());
			res.setName(mapper.getName());
			return ResponseEntity.ok(res);
		}).orElse(ResponseEntity.notFound().build());
	}

	private UserA getUser(final Long id) {
		// return userAMapper.selectByPrimaryKey(id);
		return userService.slow(id).block();
	}

	@Override
	public ResponseEntity<List<User>> users() {
		return ResponseEntity.ok(
				userAMapper.selectByExample(new UserAExample()).stream().map(mapper -> {
					final User res = new User();
					res.setId(mapper.getId());
					res.setName(mapper.getName());
					return res;
				}).collect(Collectors.toList()));
	}

}
