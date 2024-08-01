package org.example.board.controller;

import org.example.board.service.BoardService;
import org.example.board.service.BoardVO;
import org.example.utility.OutputPagination;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;



@Controller
@ApiIgnore  // 스웨거UI에서 숨김 처리
@RequestMapping("/board")
public class BoardController {
    // Logger 생성을 위한 LoggerFactory
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    @Resource(name="boardService")
    private BoardService boardService;

    @RequestMapping(value = "/boardDetail.do", method = RequestMethod.GET)
    public String boardDetail(HttpServletRequest request, Model model) throws Exception {

        BoardVO detailBoardVO = new BoardVO();

        if(request.getParameter("seq").isEmpty() == false) {

            detailBoardVO.setSeq(Integer.parseInt(request.getParameter("seq")));
            BoardVO responseBoardVO = boardService.selectBoard(detailBoardVO);

            model.addAttribute("writeId", responseBoardVO.getWriteId());
            model.addAttribute("boardTitle", responseBoardVO.getBoardTitle());
            model.addAttribute("boardContent", responseBoardVO.getBoardContent());
            model.addAttribute("boardDate", responseBoardVO.getBoardDate());
        }
        return "board/boardDetail";
    }

    @RequestMapping(value = "/boardList.do")
    public String boardList(HttpServletRequest request, Model model) throws Exception {
        // 로그 메시지 기록
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.warn("Warning log message");
        logger.error("Error log message");

        BoardVO boardVO = new BoardVO();

        int totalRow = boardService.selectCountBoard(boardVO);      // 해당 테이블의 전체 갯수
        int choicePage = 0;                                         // 선택 페이지
        int startRow = 0;                                           // MySQL LIMIT 시작점
        int limitRow = 10;                                          // MySQL LIMIT 종료점( 출력될 가로(row)의 개수를 지정 )

        if(request.getParameter("page") != null && request.getParameter("page").length() > 0) {
            choicePage = Integer.parseInt(request.getParameter("page"));
            startRow = (choicePage - 1) * limitRow;
        } else {
            choicePage = 1;
            startRow = 0;
        }

        OutputPagination outputPagination = new OutputPagination();

        model.addAttribute("boardList", boardService.selectListBoard(boardVO, startRow, limitRow));
        model.addAttribute("boardPagination", outputPagination.outputServletPagination(choicePage, limitRow, totalRow, "boardMovePage"));

        return "board/boardList";
    }
    @RequestMapping(value = "/boardWrite.do")
    public String boardWrite() throws Exception {
        return "board/boardWrite";
    }

    @RequestMapping(value = "/boardRevise.do", method = RequestMethod.GET)
    public String boardEdit(HttpServletRequest request, Model model) throws Exception {

        BoardVO editBoardVO = new BoardVO();
        editBoardVO.setSeq(Integer.parseInt(request.getParameter("seq")));

        BoardVO responseBoardVO = boardService.selectBoard(editBoardVO);
        model.addAttribute("seq", request.getParameter("seq"));
        model.addAttribute("writeId", responseBoardVO.getWriteId());
        model.addAttribute("boardTitle", responseBoardVO.getBoardTitle());
        model.addAttribute("boardContent", responseBoardVO.getBoardContent());
        model.addAttribute("boardDate", responseBoardVO.getBoardDate());

        return "board/boardRevise";
    }

    @ResponseBody
    @RequestMapping(value = "/boardWriteInsert.do", method = RequestMethod.POST)
    public void boardWriteInsert(HttpServletRequest request, HttpServletResponse response) throws Exception {

        BoardVO boardVO = new BoardVO();

        if(request.getParameter("writeId").isEmpty() == false) {
            boardVO.setWriteId(request.getParameter("writeId"));
        }

        if(request.getParameter("boardTitle").isEmpty() == false) {
            boardVO.setBoardTitle(request.getParameter("boardTitle"));
        }

        if(request.getParameter("boardDate").isEmpty() == false) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            boardVO.setBoardDate(formatter.parse(request.getParameter("boardDate")));
        }

        if(request.getParameter("boardContent").isEmpty() == false) {
            boardVO.setBoardContent(request.getParameter("boardContent"));
        }

        int resultNumber = boardService.insertBoard(boardVO);

        if(resultNumber > 0) {
            response.sendRedirect("./boardList.do");
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>alert('해당 글을 등록하는데 실패하였습니다.');</script>");
            out.flush();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/boardReviseUpdate.do", method = RequestMethod.POST)
    public void boardEditUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        BoardVO boardVO = new BoardVO();

        if(request.getParameter("seq").isEmpty() == false) {
            boardVO.setSeq(Integer.parseInt(request.getParameter("seq")));
        }

        if(request.getParameter("writeId").isEmpty() == false) {
            boardVO.setWriteId(request.getParameter("writeId"));
        }

        if(request.getParameter("boardTitle").isEmpty() == false) {
            boardVO.setBoardTitle(request.getParameter("boardTitle"));
        }

        if(request.getParameter("boardContent").isEmpty() == false) {
            boardVO.setBoardContent(request.getParameter("boardContent"));
        }

        if(request.getParameter("boardDate").isEmpty() == false) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            boardVO.setBoardDate(formatter.parse(request.getParameter("boardDate")));
        }

        int resultNumber = boardService.updateBoard(boardVO);

        if(resultNumber > 0) {
            response.sendRedirect("./boardDetail.do?seq=" + boardVO.getSeq());
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>alert('해당 글을 수정하는데 실패하였습니다.');</script>");
            out.flush();
        }
    }

    @RequestMapping(value = "/boardDelete.do", method = RequestMethod.GET)
    public void boardDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {

        BoardVO boardVO = new BoardVO();

        if(request.getParameter("seq").isEmpty() == false) {
            boardVO.setSeq(Integer.parseInt(request.getParameter("seq")));
        }

        int resultNumber = boardService.deleteBoard(boardVO);

        if(resultNumber > 0) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('해당 글이 삭제되었습니다.');");
            out.println("window.location.href='./boardList.do';");
            out.println("</script>");
            out.flush();
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>alert('해당 글을 삭제하는데 실패하였습니다.');</script>");
            out.flush();
        }
    }
}