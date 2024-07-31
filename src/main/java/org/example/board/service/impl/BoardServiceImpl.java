package org.example.board.service.impl;

import org.example.board.service.BoardDAO;
import org.example.board.service.BoardService;
import org.example.board.service.BoardVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("boardService")
@Transactional
public class BoardServiceImpl implements BoardService {

    @Resource(name="boardDaoMyBatis")
    private BoardDAO boardDAO;

    public BoardVO selectBoard(BoardVO boardVO) throws Exception {
        return boardDAO.selectBoard(boardVO);
    }

    public int selectCountBoard(BoardVO boardVO) throws Exception {
        return boardDAO.selectCountBoard(boardVO);
    }

    public List<BoardVO> selectListBoard(BoardVO boardVO, int startRow, int limitRow) throws Exception {
        return boardDAO.selectListBoard(boardVO, startRow, limitRow);
    }

    public int insertBoard(BoardVO boardVO) throws Exception {
        return boardDAO.insertBoard(boardVO);
    }

    public int updateBoard(BoardVO boardVO) throws Exception {
        return boardDAO.updateBoard(boardVO);
    }

    public int deleteBoard(BoardVO boardVO) throws Exception {
        return boardDAO.deleteBoard(boardVO);
    }
}