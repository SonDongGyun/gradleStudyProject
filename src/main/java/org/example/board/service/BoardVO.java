package org.example.board.service;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BoardVO {

    private int seq;
    private String writeId;
    private String boardTitle;
    private String boardContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date boardDate;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getWriteId() {
        return writeId;
    }

    public void setWriteId(String writeId) {
        this.writeId = writeId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardContent() {
        return boardContent;
    }

    public void setBoardContent(String boardContent) {
        this.boardContent = boardContent;
    }

    public Date getBoardDate() {
        return boardDate;
    }

    public void setBoardDate(Date boardDate) {
        this.boardDate = boardDate;
    }

    @Override
    public String toString() {
        return "BoardVO {"
                + "seq=\'" + seq + "\'"
                + ", writeId=\''" + writeId + "\'"
                + ", boardTitle=\''" + boardTitle + "\'"
                + ", boardContent=\''" + boardContent + "\'"
                + ", boardDate=\'" + boardDate + "\'"
                + " }";
    }
}