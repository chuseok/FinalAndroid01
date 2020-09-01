package org.dragon.mapper;

import java.util.Date;

import org.dragon.domain.UserVO;


public interface MainMapper {

	public void create(UserVO userVO);
	public Date getDate(String userId);
	public void updateDate(String userId);
}
