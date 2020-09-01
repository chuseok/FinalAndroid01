package org.dragon.service;

import java.util.Date;

import org.dragon.domain.UserVO;
import org.dragon.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MainServiceImpl implements MainService {

	
	@Setter(onMethod_ = @Autowired)
	private MainMapper mapper;
	
	@Override
	public void createUsers(UserVO userVO) {
		log.info("create user : "+userVO);
		mapper.create(userVO);

	}

	@Override
	public Date getDate(String userId) {
		return mapper.getDate(userId);
	}

	@Override
	public void updateDate(String userId) {
		log.info("=======update!!=========");
		mapper.updateDate(userId);
	}

}
