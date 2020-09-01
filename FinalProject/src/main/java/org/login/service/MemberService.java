package org.login.service;

import java.util.List;

import org.login.domain.AuthVO;
import org.login.domain.MemberVO;

public interface MemberService {

	public void register(MemberVO mem);
	public void register(AuthVO vo);
	
	public void withdrawal(MemberVO mem);
	public void withdrawal(AuthVO vo);

	public MemberVO get(String userId);

	public List<MemberVO> getList();
	
	public void modify(MemberVO mem);
	public void modify2(MemberVO mem);
	public void modify3(MemberVO mem);
	public void modify4(MemberVO mem);
	
	public int idCheck(MemberVO mem);
	
	public int nameCheck(MemberVO mem);
	
	
	public int PwdCheck(MemberVO mem);
}
