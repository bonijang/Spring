package com.ktdsuniversity.edu.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.bbs.dao.ReplyDAO;
import com.ktdsuniversity.edu.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.exceptions.PageNotFoundException;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDAO;
	
	@Override
	public List<ReplyVO> getAllReplies(int boardId) {
		return replyDAO.getAllReplies(boardId);
	}

	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		int insertCount = replyDAO.createNewReply(replyVO);
		return insertCount > 0;
	}

	@Override
	public boolean deleteOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDAO.getOneReply(replyId);
		if(!email.equals(replyVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다.");
		}
		return replyDAO.deleteOneReply(replyId) > 0;
	}

	@Override
	public boolean modifyOneReply(ReplyVO replyVO) {
		ReplyVO originReplyVO = replyDAO.getOneReply(replyVO.getReplyId());
		if(replyVO.getEmail().equals(originReplyVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다.");
		}
		return replyDAO.modifyOneReply(replyVO) > 0;
	}

	@Override
	public boolean recommendOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDAO.getOneReply(replyId);
		if (email.equals(replyVO.getEmail())) {
			throw new PageNotFoundException("잘못된 접근입니다.");
		}
		return replyDAO.recommendOneReply(replyId) > 0;
	}

}
