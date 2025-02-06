package com.meohin.meboard;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.meohin.meboard.entity.Post;
import com.meohin.meboard.entity.User;
import com.meohin.meboard.repository.PostRepository;
import com.meohin.meboard.repository.UserRepository;

@SpringBootTest
class MeboardApplicationTests {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void useTestData() {
		// 사용자 저장
		User author = new User();
		author.setUsername("meohin");
		author.setNickname("Meohin");
		author = userRepository.save(author); // 먼저 저장

		Post post = new Post();
		post.setAuthor(author);
		post.setTitle("Test Post");
		post.setContent("This is a test post.");
		post.setCategory("test");
		post.setTag("test");
		post.setCreatedAt(LocalDateTime.now());
		post.setViewCount(1);
		post.setLikeCount(1);
		postRepository.save(post);

		// 두 번째 사용자 저장
		User author2 = new User();
		author2.setUsername("meohin");
		author2.setNickname("Meohin2");
		author2 = userRepository.save(author2);

		Post post2 = new Post();
		post2.setAuthor(author2);
		post2.setTitle("Test Post2");
		post2.setContent("This is a test post 2.");
		post2.setCategory("test");
		post2.setTag("test");
		post2.setCreatedAt(LocalDateTime.now());
		post2.setViewCount(2);
		post2.setLikeCount(2);
		postRepository.save(post2);

		// 세 번째 사용자 저장
		User author3 = new User();
		author3.setUsername("meohin");
		author3.setNickname("Meohin3");
		author3 = userRepository.save(author3);

		Post post3 = new Post();
		post3.setAuthor(author3);
		post3.setTitle("Test Post 3");
		post3.setContent("This is a test post 3.");
		post3.setCategory("test");
		post3.setTag("test");
		post3.setCreatedAt(LocalDateTime.now());
		post3.setViewCount(3);
		post3.setLikeCount(3);
		postRepository.save(post3);
	}
}
