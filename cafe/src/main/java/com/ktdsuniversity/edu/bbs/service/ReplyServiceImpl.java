package com.ktdsuniversity.edu.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.bbs.dao.ReplyDAO;
import com.ktdsuniversity.edu.bbs.vo.ReplyVO;
import com.ktdsuniversity.edu.exceptions.AjaxPageNotFoundException;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDAO;
	
	@Override
	public List<ReplyVO> getAllReplies(int boardId) {
		return replyDAO.getAllReplies(boardId);
	}

	@Transactional
	@Override
	public boolean createNewReply(ReplyVO replyVO) {
		int insertCount = replyDAO.createNewReply(replyVO);
		return insertCount > 0;
	}

	@Transactional
	@Override
	public boolean deleteOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDAO.getOneReply(replyId);
		if(!email.equals(replyVO.getEmail())) {
			throw new AjaxPageNotFoundException("잘못된 접근입니다.");
		}
		return replyDAO.deleteOneReply(replyId) > 0;
	}

	@Transactional
	@Override
	public boolean modifyOneReply(ReplyVO replyVO) {
		ReplyVO originReplyVO = replyDAO.getOneReply(replyVO.getReplyId());
		if(replyVO.getEmail().equals(originReplyVO.getEmail())) {
			throw new AjaxPageNotFoundException("잘못된 접근입니다.");
		}
		return replyDAO.modifyOneReply(replyVO) > 0;
	}

	@Transactional
	@Override
	public boolean recommendOneReply(int replyId, String email) {
		ReplyVO replyVO = replyDAO.getOneReply(replyId);
		if (email.equals(replyVO.getEmail())) {
			throw new AjaxPageNotFoundException("잘못된 접근입니다.");
		}
		return replyDAO.recommendOneReply(replyId) > 0;
	}

}
