package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.ReplySaveRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 오라클에서는 시퀀스, mysql에서는 auto_increment
	private int id; // 오라클에서는 시퀀스, mysql에서는 auto_increment의 역할을 담당, 비워놔도 자동으로 들어감
	
	@Column(nullable = false, length = 200)
	private String content;
	
	// 이걸 누가? 어느테이블에있는것인가?
	@ManyToOne // 여러개의 답변(reply)은 하나의게시글(board)에 존재
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne // 여러개의 답변은 하나의 유저가 쓸 수 있다.
	@JoinColumn(name="userId")
	private User user;

	@CreationTimestamp
	private Timestamp createDate;

	// 출력확인용
	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", user=" + user + ", createDate="
				+ createDate + "]";
	}
}
