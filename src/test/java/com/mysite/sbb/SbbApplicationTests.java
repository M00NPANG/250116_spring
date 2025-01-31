package com.mysite.sbb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("질문 2개를 등록한다.")
	void t1() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	@DisplayName("질문이 2개가 등록되어 있는지 확인한다.")
	void t2() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());
	  }

	  @Test
	  @DisplayName("첫 번째 등록된 게시물의 제목이 sbb가 무엇인가요?이어야 한다.")
	  void t3() {
		List<Question> all = this.questionRepository.findAll();
	     Question q = all.get(0);

		 assertEquals("sbb가 무엇인가요?", q.getSubject());
	    }

		@Test
		@DisplayName("id가 1인 질문의 제목은 sbb가 무엇인가요? 이어야 한다.")
		void t4() {
			Optional<Question> oq = this.questionRepository.findById(1);

			if(oq.isPresent()) {
				Question q = oq.get();
				assertEquals("sbb가 무엇인가요?", q.getSubject());
			}
		  }

		  @Test
		  @DisplayName("sbb가 무엇인가요?라는 제목을 검색하면 id가 1인 질문이 들어온다.")
		  void t5() {
		      // given
		      Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		      // then
			  assertEquals(1, q.getId());
		    }

			@Test
			@DisplayName("sbb가 무엇인가요?, sbb에 대해서 알고 싶습니다.에 대해 조회하면 id가 1인 질문을 조회한다." )
			void t6() {
			    // given
				Question q = this.questionRepository.findBySubjectAndContent(
						"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다."
				);
			    // then
				assertEquals(1, q.getId());
			  }

			  @Test
			  @DisplayName("제목 중 sbb가 포함된 질문을 찾음")
			  void t7() {
			      // given
			      List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
				  Question q = qList.get(0);
				  assertEquals("sbb가 무엇인가요?", q.getSubject());
			    }

				@Test
				@DisplayName("id가 1인 질문이 존재하면 제목을 수정된 제목으로 수정한다.")
				void t8() {
				    // given
				    Optional<Question> oq = this.questionRepository.findById(1);

				    // when
					assertTrue(oq.isPresent());

				    // then
					Question q = oq.get();
					q.setSubject("수정된 제목");
					this.questionRepository.save(q);
				  }
}
