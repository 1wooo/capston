package com.example.demo.liveStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private int fileIndex;

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

    public void fileIndexSwitch() {
        if (fileIndex == 1) {
            fileIndex = 2;
        } else if (fileIndex == 2) {
            fileIndex = 1;
        }
    }

    public byte[] getObject() throws IOException {
        S3Object o = amazonS3.getObject(new GetObjectRequest(bucket+"/capston-cam", "cam"
                + String.valueOf(fileIndex) + ".mp4"));
        S3ObjectInputStream objectInputStream = ((S3Object) o).getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        OutputStream output = new FileOutputStream("src/main/resources/static/testCam/output"+ fileIndex + ".mp4");
        output.write(bytes);
        output.flush();
        output.close();

        return bytes;
    }

    public String getThumbnailPath(String path) {
        String tmp = "/capston-cam/cam" + String.valueOf(fileIndex) + ".mp4";
        return amazonS3.getUrl(bucket, tmp).toString();
    }
}
