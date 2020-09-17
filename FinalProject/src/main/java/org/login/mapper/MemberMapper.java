package org.login.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.login.domain.AuthVO;
import org.login.domain.MemberVO;

public interface MemberMapper {
//@Select("select * from mem")
public List<MemberVO> getList();

public int insert(MemberVO mem);
public int insertAuth(AuthVO vo);

public void withdrawal(MemberVO mem);
public void removeauth(AuthVO vo);

public MemberVO read(String userId);

public void modify(MemberVO mem);
public void modify2(MemberVO mem);
public void modify3(MemberVO mem);
public void modify4(MemberVO mem);

public int idCheck(MemberVO mem);

public int nameCheck(MemberVO mem);


public int PwdCheck(MemberVO mem);
}
