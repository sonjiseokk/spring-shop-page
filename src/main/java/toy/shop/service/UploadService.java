package toy.shop.service;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import toy.shop.entity.web.AttachImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class UploadService {
    @Value("${image.path}")
    private String uploadFolder;
    public List<AttachImage> upload(MultipartFile[] uploadFile){
        if (imageCheck(uploadFile)) return null;
        // 날짜별로 분류
        String datePath = getDatePath();

        /* 폴더 생성 */
        File uploadPath = new File(uploadFolder, datePath);

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        /* 이미저 정보 담는 객체 */
        List<AttachImage> res = new ArrayList<>();

        for (MultipartFile multipartFile : uploadFile) {
            AttachImage attachImage = new AttachImage();
            attachImage.setUploadPath(datePath);

            /* 파일 이름 */
            String uploadFileName = multipartFile.getOriginalFilename();
            attachImage.setFileName(uploadFileName);
            /* uuid 적용 파일 이름 */
            String uuid = UUID.randomUUID().toString();
            attachImage.setUUID(uuid);
            uploadFileName = uuid + "_" + uploadFileName;

            /* 파일 위치, 파일 이름을 합친 File 객체 */
            File saveFile = new File(uploadPath, uploadFileName);

            /* 파일 저장 */
            try {
                multipartFile.transferTo(saveFile);

                /* 방법 2 */ // 라이브러리 사용
                File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
                BufferedImage bo_image = ImageIO.read(saveFile);

                //비율
                double ratio = 3;
                //넓이 높이
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                Thumbnails.of(saveFile)
                        .size(width, height)
                        .toFile(thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
            res.add(attachImage);
        }
        return res;
    }

    private static boolean imageCheck(final MultipartFile[] uploadFile) {
        /* 이미지 파일 체크 */
        for(MultipartFile multipartFile: uploadFile) {

            File checkfile = new File(multipartFile.getOriginalFilename());
            String type = null;

            try {
                type = Files.probeContentType(checkfile.toPath());
                log.info("MIME TYPE : " + type);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!type.startsWith("image")) {
                return true;
            }

        }// for
        return false;
    }

    private static String getDatePath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }
}

                /* 방법 1
                File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

                BufferedImage bo_image = ImageIO.read(saveFile);
                // 비율
                double ratio = 3;
                // 너비 높이
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

                Graphics2D graphic = bt_image.createGraphics();
                graphic.drawImage(bo_image, 0, 0,300,500, null);
                ImageIO.write(bt_image, "jpg", thumbnailFile);
                */