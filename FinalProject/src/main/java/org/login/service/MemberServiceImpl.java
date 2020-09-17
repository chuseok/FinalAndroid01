package org.login.service;

import java.util.List;

import org.login.domain.AuthVO;
import org.login.domain.MemberVO;
import org.login.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
	private MemberMapper mapper;
	
	@Override
	public int register(MemberVO mem) {  

		log.info("register....."+ mem);
		return mapper.insert(mem); 
	}
	
	
	@Override
	public int register(AuthVO vo) {
		log.info("Auth......."+ vo);
		return mapper.insertAuth(vo);
	}
	
	@Override
	public void withdrawal(MemberVO mem) {
		log.info("delete....."+ mem);
		mapper.withdrawal(mem);
	}
	@Override
	public void withdrawal(AuthVO vo) {
		log.info("delete Auth....."+ vo) ;
		mapper.removeauth(vo);
	}
	
	@Override
	public MemberVO get(String userId) {
		log.info("get........." + userId);

		return mapper.read(userId);
	}

	 @Override 
	 public List<MemberVO> getList() { 
		 log.info("getList............");
	 
	 return mapper.getList(); 
	 }
	 
	 @Override
		public void modify(MemberVO mem) {
			log.info("update Name....."+ mem);
			mapper.modify(mem);
		}
	 @Override
		public void modify2(MemberVO mem) {
			log.info("update Pwd....."+ mem);
			mapper.modify2(mem);
		}
	 @Override
		public void modify3(MemberVO mem) {
			log.info("update phone....."+ mem);
			mapper.modify3(mem);
		}
	 @Override
		public void modify4(MemberVO mem) {
			log.info("update E....."+ mem);
			mapper.modify4(mem);
		}

	 @Override
	 public int idCheck(MemberVO mem){
	 	int result = mapper.idCheck(mem);
	 	return result;
	 }
	 
	 @Override
	 public int nameCheck(MemberVO mem){
	 	int result = mapper.nameCheck(mem);
	 	return result;
	 }
	 @Override
	 public int PwdCheck(MemberVO mem){
	 	int result = mapper.PwdCheck(mem);
	 	return result;
	 }

}
