// Entity 클래스, 기본 Entity Repository는 함께 위치해야함, 도메인 페이지에서 관리
package com.web02.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 인터페이스 생성후 JpaRepository<Entity 클래스, PK타입> 으로 CRUD 메소드 자동생성
public interface PostsRepository extends JpaRepository<Posts,Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
    List<Posts> findByTitleContaining(String keyword);

    @Modifying
    @Query("update Posts p set p.views = p.views + 1 where p.id = :id")
    int updateViews(@Param("id") Long id);


}
