package com.ktdsuniversity.edu.bbs.dao;

import java.util.List;

import com.ktdsuniversity.edu.bbs.vo.ReplyVO;

public interface ReplyDAO {
	
	public List<ReplyVO> getAllReplies(int boardId);

	public ReplyVO getOneReply(int replyId);
	
	public int createNewReply(ReplyVO replyVO);
	
	public int deleteOneReply(int replyId);
	
	public int modifyOneReply(ReplyVO replyVO);
	
	public int recommendOneReply(int replyId);
}
