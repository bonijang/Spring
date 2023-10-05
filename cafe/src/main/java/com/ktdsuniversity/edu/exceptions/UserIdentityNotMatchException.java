package com.ktdsuniversity.edu.exceptions;

import com.ktdsuniversity.edu.member.vo.MemberVO;

/**
 * 로그인할 때, 잘못된 이메일과 비밀번호를 전달했을 때 발생시킬 예외.
 * 
 * 화면으로 로그인 정보를 전달해야 함.
 */
public class UserIdentityNotMatchException extends RuntimeException {

	private static final long serialVersionUID = -3234883133196074207L;

	/**
	 * 로그인 정보
	 */
	private MemberVO memberVO;
	
	public UserIdentityNotMatchException(MemberVO memberVO, String message) {
		super(message);
		this.memberVO = memberVO;
	}
	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	
}
