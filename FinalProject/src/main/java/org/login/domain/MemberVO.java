package org.login.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	private String userId;
	private String userPwd;
	private String userName;
	private String birthday;
	private String phoneNo;
	private String email;
	private Date lastConn;
	private boolean enable;
	private int coin;
	private List<AuthVO> authList;
}
