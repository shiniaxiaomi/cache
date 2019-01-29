package com.lyj;

import com.lyj.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	StringRedisTemplate stringRedisTemplate;//k-v都是字符串的

	@Autowired
	RedisTemplate redisTemplate;//k-v都是对象的

	//测试 字符串-字符串
	@Test
	public void testStringRedisTemplate(){
		stringRedisTemplate.opsForValue().append("msg","hello");
		stringRedisTemplate.opsForValue().append("msg","world");
		String msg = stringRedisTemplate.opsForValue().get("msg");
		System.out.println(msg);

		stringRedisTemplate.opsForList().leftPush("myList","1");
		stringRedisTemplate.opsForList().leftPush("myList","2");
	}

	//测试 对象-对象
	@Test
	public void testRedisTemplate(){
		redisTemplate.opsForValue().set("user",new User());
		Object user = redisTemplate.opsForValue().get("user");
		System.out.println(user);
	}

	//测试 对象-对象
	@Test
	public void testRedisTemplate01(){
		List list=new ArrayList();
		list.add("1");
		list.add("2");
		redisTemplate.opsForValue().set("list",list);
	}


}

