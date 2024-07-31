package org.example.board.service.impl;

import org.example.board.service.BoardVO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    BoardVO selectBoard(BoardVO boardVO) throws Exception;

    int selectCountBoard(BoardVO boardVO) throws Exception;

    List<BoardVO> selectListBoard(BoardVO boardVO, @Param("startRow") int startRow, @Param("limitRow") int limitRow) throws Exception;

    int insertBoard(BoardVO boardVO) throws Exception;

    int updateBoard(BoardVO boardVO) throws Exception;

    int deleteBoard(BoardVO boardVO) throws Exception;
}