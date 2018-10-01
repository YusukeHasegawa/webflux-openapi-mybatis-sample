package io.github.yusukehasegawa.openapi.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import io.github.yusukehasegawa.openapi.mapper.UserAMapper;
import io.github.yusukehasegawa.openapi.model.UserA;
import io.github.yusukehasegawa.openapi.model.UserAExample;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
@RequiredArgsConstructor()
public class UserService {

	private final UserAMapper userAMapper;

	private final Scheduler jdbcScheduler;

	public Mono<Long> countByExample(final UserAExample example) {
		return Mono.defer(() -> Mono.justOrEmpty(userAMapper.countByExample(example)))
				.subscribeOn(jdbcScheduler);
	}

	public Flux<UserA> selectByExample(final UserAExample example) {
		return Flux.defer(() -> Flux.fromIterable(userAMapper.selectByExample(example)))
				.subscribeOn(jdbcScheduler);
	}

	public Mono<UserA> selectByPrimaryKey(final Long id) {
		return Mono.defer(() -> Mono.justOrEmpty(userAMapper.selectByPrimaryKey(id)))
				.subscribeOn(jdbcScheduler);
	}

	public Mono<UserA> slow(final Long id) {
		return selectByPrimaryKey(id).delayElement(Duration.ofSeconds(5));
	}

}
