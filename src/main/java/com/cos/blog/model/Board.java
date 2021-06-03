package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment, 시퀀스
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터일때 사용
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이됨.
	
	private int count; // 조회수
	
	// fetch = FetchType.EAGER는 무조건 들고 와야할때
	@ManyToOne(fetch = FetchType.EAGER) // Board = Many, user = One (한명의 유저는 여러개의 게시물을 쓸 수 있다는 뜻) 만약,One to One이라면 한명의 유저는 하나의 게시글 밖에 쓸 수 없다.
	@JoinColumn(name="userId") // 자동으로 FK를 만들어 준다
	private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다. (충돌날수 있다는뜻)
	
	// reply은 하나의 게시물에 여러개가 존재할 수 있으니 list로 받아야 한다.
	// joinColumn은 필요없다. board게시물에 리플을 여러개 받는데 그만큼 아이디를 받으면 원자성이 깨지기 때문
	// fetch = FetchType.LAZY는 기본전략 (안써도 됨)												cascade remove를 하면 게시글을 지울때 관련된 리플을 다 지운다는 뜻 
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy 연관관계의 주인이 아니다(FK이 아님) DB에 컬럼을 만들지 않는다. 그저 이 reply이 어떤번호의 board게시물인지 확인하는 용도일뿐 
	@JsonIgnoreProperties({"board"}) // reply안의 board를 무시한다.(무한참조 방지함)
	@OrderBy("id desc")
	private List<Reply> replys;
	
	
	
	@CreationTimestamp
	private Timestamp createDate;
}
