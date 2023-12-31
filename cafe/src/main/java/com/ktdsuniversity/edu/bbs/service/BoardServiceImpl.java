package com.ktdsuniversity.edu.bbs.service;

import java.io.File;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.bbs.dao.BoardDAO;
import com.ktdsuniversity.edu.bbs.vo.BoardListVO;
import com.ktdsuniversity.edu.bbs.vo.BoardVO;
import com.ktdsuniversity.edu.bbs.vo.SearchBoardVO;
import com.ktdsuniversity.edu.beans.FileHandler;
import com.ktdsuniversity.edu.beans.FileHandler.StoredFile;
import com.ktdsuniversity.edu.exceptions.PageNotFoundException;

import io.github.seccoding.web.mimetype.ExtFilter;
import io.github.seccoding.web.mimetype.MimeType;
import io.github.seccoding.web.mimetype.abst.ExtensionFilter;
import io.github.seccoding.web.mimetype.factory.ExtensionFilterFactory;

@Service
public class BoardServiceImpl implements BoardService {

	private Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public BoardListVO getAllBoard(SearchBoardVO searchBoardVO) {
		
		// 게시글 갯수와 게시글 목록 이렇게 2가지를 반환하기 위해 List 만듦
		BoardListVO boardListVO = new BoardListVO();
		
		boardListVO.setBoardCnt(boardDAO.getBoardAllCount(searchBoardVO));
		
		if(searchBoardVO == null) {
			boardListVO.setBoardList(boardDAO.getAllBoard());			
		}
		else {
			boardListVO.setBoardList(boardDAO.searchAllBoard(searchBoardVO));
		}
		return boardListVO;
	}

	@Transactional // 이 Annotation이 붙어있으면
	               // 예외 상황이 발생하면 자동으로 Rollback을 처리해준다.
	               // 예외 상황이 발생하지 않으면 자동으로 Commit을 처리해준다.
	@Override
	public boolean createNewBoard(BoardVO boardVO, MultipartFile file) {
		
		StoredFile storedFile = fileHandler.storeFile(file);
		
		
		// 업로드에 성공했다면
		if (storedFile != null) {
			logger.debug(storedFile.getFileName());
			logger.debug(storedFile.getFileSize()+"");
			logger.debug(storedFile.getRealFileName());
			logger.debug(storedFile.getRealFilePath());
			
			// 사용자가 입력한 정보에
			// 파일 정보를 할당한다.
		    boardVO.setFileName(storedFile.getRealFileName());
		    boardVO.setOriginFileName(storedFile.getFileName());

		    // 이미지 파일만 업로드 가능
		    ExtensionFilter filter = ExtensionFilterFactory.getFilter(ExtFilter.APACHE_TIKA);
		    boolean isImageFile = filter.doFilter(storedFile.getRealFilePath(), 
		    							MimeType.JPEG,
		    							MimeType.JPG,
		    							MimeType.GIF,
		    							MimeType.PNG);
		    if (!isImageFile) {
		    	// 이미지 파일이 아니라면
		    	// 서버에 업로드한 파일을 삭제하고
		    	File uploadFile = new File(storedFile.getRealFilePath());
		    	uploadFile.delete();
		    	// boardVO에 정의한 파일 정보도 삭제한다.
		    	boardVO.setOriginFileName(null);
		    	boardVO.setFileName(null);
		    }
		}
		
		// DB에 게시글 등록한다.
		// createCount에는 DB에 등록한 게시글의 개수를 반환.
		int createCount = boardDAO.createNewBoard(boardVO);
		// DB에 등록한 개수가 0보다 크다면 성공. 아니라면 실패
		return createCount > 0;
	}

	@Transactional
	@Override
	public BoardVO getOneBoard(int id, boolean isIncrease) {
		if(isIncrease) {
			// 파라미터로 전달받은 게시글의 조회 수 증가
			// updateCount에는 DB에 업데이트한 게시글의 수를 반환
			int updateCount = boardDAO.increaseViewCount(id);
			if (updateCount == 0) {
				// updateCount가 0이라는 것은
				// 파라미터로 전달받은 id값이 DB에 존재하지 않는다는 의미이다.
				// 이 경우, "잘못된 접근입니다." 라고 사용자에게 예외 메세지를 보내준다.
				throw new PageNotFoundException("잘못된 접근입니다.");
			}
			
			// 트랜잭션 테스트를 위한 예외 발생
			// NumberFormatException발생!
			// 조회수가 증가했다가 롤벡 되어야 한다.(조회수가 증가되지 않는다)
//			Integer.parseInt("AAA");
		}
		// 예외가 발생하지 않았다면, 게시글 정보를 조회해 반환한다.
		BoardVO boardVO = boardDAO.getOneBoardVO(id);
		if(boardVO == null) {
			// 파라미터로 전달받은 id값이 DB에 존재하지 않을 경우
			// "잘못된 접근입니다." 라고 사용자에게 예외메세지를 보낸다.
			throw new PageNotFoundException("잘못된 접근입니다.");
		}
		return boardVO;
	}

	@Transactional
	@Override
	public boolean updateOneBoard(BoardVO boardVO, MultipartFile file) {
		// 파일을 업로드 했는지 확인
		if (file != null && !file.isEmpty()) {
			// 변경되기 전의 게시글 정보 가져오기
			BoardVO originBoardVO = boardDAO.getOneBoardVO(boardVO.getId());
			if (originBoardVO != null && originBoardVO.getFileName() != null) {
				// 변경되기 전의 게시글이 파일이 업로드된 게시글일 경우
				File originFile =
						fileHandler.getStoredFile(originBoardVO.getFileName());
				// 파일이 있는지 확인하고 삭제한다.
				if (originFile.exists() && originFile.isFile()) {
					originFile.delete();
				}
			}
			// 파일을 업로드하고 결과를 받아온다.
			StoredFile storedFile = fileHandler.storeFile(file);
			boardVO.setFileName(storedFile.getRealFileName());
			boardVO.setOriginFileName(storedFile.getFileName());
		}
		
		// 파라미터로 전달받은 수정된 게시글의 정보로 DB 수정
		// updateCount에는 DB에 업데이트한 게시글의 수를 반환
		int updateCount = boardDAO.updateOneBoard(boardVO);
		return updateCount > 0;
	}

	@Transactional
	@Override
	public boolean deleteOneBoard(int id) {
		// 삭제되기 전의 게시글 정보 가져오기
		BoardVO originBoardVO = boardDAO.getOneBoardVO(id);
		if (originBoardVO != null && originBoardVO.getFileName() != null) {
			// 삭제되기 전의 게시글이 파일이 업로드된 게시글일 경우
			// 파일이 있는지 확인하고, 삭제한다.
			File originFile = 
					fileHandler.getStoredFile(originBoardVO.getFileName());
			if (originFile.exists() && originFile.isFile()) {
				originFile.delete();
			}
		}
		
		// 파라미터로 전달받은 id로 게시글을 삭제.
		// deleteCount에는 DB에서 삭제한 게시글의 수를 반환한다.
		int deleteCount = boardDAO.deleteOneBoard(id);
		return deleteCount > 0;
	}

	

	

}
