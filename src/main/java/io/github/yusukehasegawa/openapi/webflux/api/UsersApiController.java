package io.github.yusukehasegawa.openapi.webflux.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import io.github.yusukehasegawa.openapi.model.UserA;
import io.github.yusukehasegawa.openapi.model.UserAExample;
import io.github.yusukehasegawa.openapi.service.UserService;
import io.github.yusukehasegawa.openapi.webflux.model.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController("webfluxUsersApiController")
@RequestMapping("/webflux")
public class UsersApiController implements UsersApi {

	private final UserService userService;

	@Override
	public ResponseEntity<Mono<User>> user(@PathVariable("id") final Long id,
			final ServerWebExchange exchange) {
		return ResponseEntity.ok(getUser(id).map(mapper -> {
			final User res = new User();
			res.setId(mapper.getId());
			res.setName(mapper.getName());
			return res;
		}).doOnSuccess(it -> {
			if (it == null)
				exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
		}));

	}

	private Mono<UserA> getUser(final Long id) {
		// return userService.selectByPrimaryKey(id);
		return userService.slow(id);
	}

	@Override
	public ResponseEntity<Flux<User>> users(final ServerWebExchange exchange) {
		return ResponseEntity
				.ok(userService.selectByExample(new UserAExample()).map(mapper -> {
					final User res = new User();
					res.setId(mapper.getId());
					res.setName(mapper.getName());
					return res;
				}));
	}

}
