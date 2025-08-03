package jpabarcode.jpashop.service;

import jpabarcode.jpashop.controller.BoardForm;
import jpabarcode.jpashop.domain.Board;
import jpabarcode.jpashop.domain.UploadFile;
import jpabarcode.jpashop.file.FileStore;
import jpabarcode.jpashop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    //게시글 등록
    @Transactional
    public void save(BoardForm form) throws IOException {
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setType(form.getType());
        board.setFileName(form.getFileName());
        if(form.getId() != null) board.setId(form.getId());

        if (form.getAttachFile() != null && !form.getAttachFile().isEmpty()) {
            //파일저장
            MultipartFile attachFile = form.getAttachFile();
            UploadFile uploadFile = fileStore.storeFile(attachFile);
            //파일명 추가
            board.setFileName(uploadFile.getStoreFileName());
        }

        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(BoardForm form) throws IOException {
        Board findBoard = boardRepository.findOne(form.getId());
        findBoard.setTitle(form.getTitle());
        findBoard.setContent(form.getContent());

        if (!form.getAttachFile().isEmpty()) {
            //파일저장
            MultipartFile attachFile = form.getAttachFile();
            UploadFile uploadFile = fileStore.storeFile(attachFile);
            //기존파일 삭제
            fileStore.deleteFile(findBoard.getFileName());
            //파일명 추가
            findBoard.setFileName(uploadFile.getStoreFileName());
        }
    }

    @Transactional
    public String deleteBoard(BoardForm form) throws IOException {
        Board findBoard = boardRepository.findOne(form.getId());
        if (findBoard.getFileName() != null && !findBoard.getFileName().isEmpty()) {
            fileStore.deleteFile(findBoard.getFileName());
        }
        boardRepository.deleteOne(form.getId());
        return findBoard.getType();
    }

    //게시글 전체 조회
    public List<Board> findByType(String type) {
        return boardRepository.findByType(type);
    }

    //게시글 전체 조회
    public List<Board> findByType2(String type, String title, int startIndex, int pageSize) {
        return boardRepository.findByType2(type, title, startIndex, pageSize);
    }

    //게시글 전체 조회
    public long findByTypeCnt(String type, String title) {
        return boardRepository.findByTypeCnt(type, title);
    }

    //게시글 상세 조회
    public Board findOne(Long id) {
        return boardRepository.findOne(id);
    }

    //게시글 N개 조회
    public List<Board> findByTypeLimit(String type, int limit) {
        return boardRepository.findByTypeLimit(type, limit);
    }
}
