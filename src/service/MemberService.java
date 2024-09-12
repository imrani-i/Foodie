package service;

import java.sql.SQLException;
import java.util.List;

import dao.MemberDAO;
import dto.MemberDTO;
import dto.PostDTO;

public class MemberService {

	MemberDAO memberDAO = new MemberDAO();
	// 1. 회원가입

	public int memberInsert(MemberDTO member) throws SQLException {
		return memberDAO.memberInsert(member);
	}

	// 2.아이디 체크
	public boolean memberCheck(int num, String idPw) throws SQLException {

		if (num==1 && (memberDAO.memberCheck(num, idPw) > 0)) {
			return false;
		} else if(num ==2 && (memberDAO.memberCheck(num, idPw) <= 0)) {

			return false;
		}
		else if (num==0 && (memberDAO.memberCheck(num, idPw)<=0)) {
			return false;
		}
		else {
			return true;
		}

	}
	
	// 3.작성글 조회
	
	public List<PostDTO> selectByWriter(String writer) {
		return memberDAO.selectByWriter(writer);
	}
	
	// 4. 작성글 삭제
	public int deletePost(int deleteNo) {
		return memberDAO.deletePost(deleteNo);
	}
}
