package org.example.board.service.impl;

import org.example.board.service.BoardDAO;
import org.example.board.service.BoardVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("boardDaoMyBatis")
public class BoardDAOMyBatis implements BoardDAO {

    private static final Logger logger = LoggerFactory.getLogger(BoardDAOMyBatis.class);

    @Resource(name="boardMapper")
    private BoardMapper boardMapper;

    public BoardDAOMyBatis() {
        // System.out.println("===> BoardDAOMyBatis 생성");
        logger.info("===> BoardDAOMyBatis 생성");
    }

    public BoardVO selectBoard(BoardVO boardVO) throws Exception {
        // System.out.println("===> MyBatis로 selectBoard() 기능 처리");
        logger.info("===> MyBatis로 selectBoard() 기능 처리");
        return (BoardVO) boardMapper.selectBoard(boardVO);
    }

    public int selectCountBoard(BoardVO boardVO) throws Exception {
        // System.out.println("===> MyBatis로 selectCountBoard() 기능 처리");
        logger.info("===> MyBatis로 selectCountBoard() 기능 처리");
        return boardMapper.selectCountBoard(boardVO);
    }

    public List<BoardVO> selectListBoard(BoardVO boardVO, int startRow, int limitRow) throws Exception {
        // System.out.println("===> MyBatis로 selectListBoard() 기능 처리");
        logger.info("===> MyBatis로 selectListBoard() 기능 처리");
        return boardMapper.selectListBoard(boardVO, startRow, limitRow);
    }

    public int insertBoard(BoardVO boardVO) throws Exception {
        // System.out.println("===> MyBatis로 insertBoard() 기능 처리");
        logger.info("===> MyBatis로 insertBoard() 기능 처리");
        return boardMapper.insertBoard(boardVO);
    }

    public int updateBoard(BoardVO boardVO) throws Exception {
        // System.out.println("===> MyBatis로 updateBoard() 기능 처리");
        logger.info("===> MyBatis로 updateBoard() 기능 처리");
        return boardMapper.updateBoard(boardVO);
    }

    public int deleteBoard(BoardVO boardVO) throws Exception {
        // System.out.println("===> MyBatis로 deleteBoard() 기능 처리");
        logger.info("===> MyBatis로 deleteBoard() 기능 처리");
        return boardMapper.deleteBoard(boardVO);
    }
}