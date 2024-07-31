package org.example.board.service;

import java.util.List;

public interface BoardDAO {

    BoardVO selectBoard(BoardVO boardVO) throws Exception;

    int selectCountBoard(BoardVO boardVO) throws Exception;

    List<BoardVO> selectListBoard(BoardVO boardVO, int startRow, int limitRow) throws Exception;

    int insertBoard(BoardVO boardVO) throws Exception;

    int updateBoard(BoardVO boardVO) throws Exception;

    int deleteBoard(BoardVO boardVO) throws Exception;
}