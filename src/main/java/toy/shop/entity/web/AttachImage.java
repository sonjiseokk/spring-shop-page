package toy.shop.entity.web;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AttachImage {
    private Long id;
    private String UUID;
    private String fileName;
    private String uploadPath;


    @Builder
    public AttachImage(Long id, String fileName, String uploadPath) {
        this.id = id;
        this.fileName = fileName;
        this.uploadPath = uploadPath;
    }
}