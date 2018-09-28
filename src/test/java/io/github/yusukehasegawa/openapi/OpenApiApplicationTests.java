package io.github.yusukehasegawa.openapi;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.yusukehasegawa.openapi.mapper.UserMapper;
import io.github.yusukehasegawa.openapi.model.UserExample;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenApiApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	public void contextLoads() {

		Map.of("A", "foo", "B", "bar", "C", "baz").forEach((suffix, name) -> {
			final DynamicUserExample example = new DynamicUserExample(suffix);
			example.createCriteria().andIdEqualTo(1L);
			assertEquals(userMapper.selectByExample(example).get(0).getName(), name);
		});
	}

	@Data
	@RequiredArgsConstructor
	public static final class DynamicUserExample extends UserExample {
		private final String suffix;
	}
}
