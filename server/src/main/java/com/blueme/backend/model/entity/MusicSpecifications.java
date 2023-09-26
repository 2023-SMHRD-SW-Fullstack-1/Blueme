package com.blueme.backend.model.entity;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

/**
 * 여러 태그에 해당하는 Musics 를 추출하기 위한 클래스입니다.
 * 
 * @author 김혁
 * @version 1.0
 * @since 2023-09-25
 */
public class MusicSpecifications {
  /**
   * 제공된 태그들에 해당하는 Musics 를 추출하기 위한 메서드입니다.
   * 
   * @param tags 제공된 태그들(String...) ex) hasTag("rock","pop")
   * @return JPA Where절 Query를 생성합니다.
   */
  public static Specification<Musics> hasTags(String... tags) {
    return (root, query, criteriaBuilder) -> {
      Predicate[] predicates = new Predicate[tags.length];
      for (int i = 0; i < tags.length; i++) {
        predicates[i] = criteriaBuilder.like(root.get("tag"), "%" + tags[i] + "%");
      }
      return criteriaBuilder.and(predicates);
    };
  }
}
