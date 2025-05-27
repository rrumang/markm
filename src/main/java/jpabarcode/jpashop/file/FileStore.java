package jpabarcode.jpashop.file;


import jpabarcode.jpashop.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    //다건 파일 업로드
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    //단건 파일 업로드
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);   //서버에 저장하는 파일명
        multipartFile.transferTo(new File(getFullPath(storeFileName))); //전체경로 출력
        return new UploadFile(originalFilename, storeFileName);
    }

    //서버에 저장하는 파일명
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);  //확장자 추출
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자 추출(.png)
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    //전체경로 출력
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    //파일명으로 파일 삭제
    public void deleteFile(String filename) {
        String fullPath = getFullPath(filename);
        File file = new File(fullPath);
        if (file.exists()) file.delete();
    }

}