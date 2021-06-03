package com.cos.blog.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	// select만 하는거니까 readOnly = true를 한다
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 상세보기실패 : 아이디를 찾을 수 없습니다");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기 (int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()-> {
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다");
				}); // 영속화 완료
		// 영속성 context에 board가 들어온다. 데이터베이스에 있는 테이블에 있는 board랑 동기화가 되있다.
		// 그럼 이곳에
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당함수로 종료시 (service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹이 되면서 (board에 있는것이 set된거랑 달라지기 때문에) 자동업데이트가 됨.
		// db쪽으로 flush가 됨(commit이 된다는 뜻)
	}
	
	@Transactional
	public void 댓글쓰기 (ReplySaveRequestDto replySaveRequestDto) {
		
		/*
		 * User user =
		 * userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()-> {
		 * return new IllegalArgumentException("댓글 쓰기 실패 : 유저 아이디를 찾을 수 없습니다"); }); //
		 * 영속화 완료
		 * 
		 * Board board =
		 * boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()-> {
		 * return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 아이디를 찾을 수 없습니다"); }); //
		 * 영속화 완료
		 * 
		 * Reply reply = Reply.builder() .user(user) .board(board)
		 * .content(replySaveRequestDto.getContent()) .build();
		 */
		
		// 네이티브 쿼리문을 쓰면 위의 영속화를 하지 않는다.
		replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}

}
