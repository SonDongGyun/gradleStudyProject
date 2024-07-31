package org.example.board.service;

import java.util.List;

public interface BoardService {

    /* @brief  사용자 정보 등록*/
    BoardVO selectBoard(BoardVO boardVo) throws Exception;

    int selectCountBoard(BoardVO boardVo) throws Exception;

    List<BoardVO> selectListBoard(BoardVO boardVo, int startRow, int limitRow) throws Exception;

    /* @brief  사용자 정보 등록*/
    int insertBoard(BoardVO boardVo) throws Exception;

    /* @brief  사용자 정보 수정 */
    int updateBoard(BoardVO boardVo) throws Exception;

    /* @brief  사용자 삭제 */
    int deleteBoard(BoardVO boardVo) throws Exception;
}