package com.example.auctionarena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.auctionarena.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice> {

    @Query(value = "SELECT * from notice " +
    "WHERE nno = (SELECT prev_notice FROM ( " +
	"SELECT nno, lag(nno, 1, 0) OVER (ORDER BY nno) AS prev_notice FROM notice) " +
	"WHERE nno = :nno)", nativeQuery = true
    )
    Notice findPrevNotice(Long nno);
    
    @Query(value = "SELECT * from notice " +
    "WHERE nno = (SELECT next_notice FROM ( " +
	"SELECT nno, lead(nno, 1, 0) OVER (ORDER BY nno) AS next_notice FROM notice) " +
	"WHERE nno = :nno)", nativeQuery = true
    )
    Notice findNextNotice(Long nno);
}
