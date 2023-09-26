package com.ktdsuniversity.edu.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.member.dao.MemberDAO;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public boolean createNewMember(MemberVO memberVO) {
		int emailCount = memberDAO.getEmailCount(memberVO.getEmail());
		if (emailCount > 0) {
			throw new IllegalArgumentException("Email이 이미 사용중입니다.");
		}
		int insertCount = memberDAO.createNewMember(memberVO);
		return insertCount > 0;
	}

}
