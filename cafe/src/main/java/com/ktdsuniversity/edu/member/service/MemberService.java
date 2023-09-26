package com.ktdsuniversity.edu.member.service;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public interface MemberService {

	/**
	 * 회원가입을 처리한다.
	 * @param memberVO 사용자가 작성한 사용자 정보
	 * @return 회원가입이 정상적으로 처리되었는지 여부
	 */
	public boolean createNewMember(MemberVO memberVO);
	
	
}
