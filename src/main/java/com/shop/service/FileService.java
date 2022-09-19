package com.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/*
 * 파일을 업로드하는 메소드와 삭제하는 메소드
 */
@Slf4j
@Service
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {

        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // UUID로 받은 값과 원래 파일의 이름의 확장자를 조합해서 저장될 파일 이름을 만듭니다.
        String saveFileName = uuid.toString() + extension;

        String fileUploadFullUrl = uploadPath + "/" + saveFileName;
        // FileOutputStream 클래스는 바이트 단위 출력을 내보내는 클래스입니다.
        // 생성자로 파일이 저장될 위치와 파일의 이름을 넘겨 파일에 쓸 파일 출력 스트림을 만듭니다.
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        // fileData를 파일 출력 스트림에 입력합니다.
        fos.write(fileData);
        fos.close();

        return saveFileName;
    }

    public void deleteFile(String filePath) throws Exception {

        // 파일이 저장된 경로를 이용하여 파일 객체를 생성합니다.
        File deleteFile = new File(filePath);

        // 해당 파일이 존재하면 파일을 삭제합니다.
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
