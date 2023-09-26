package toy.shop.entity.web;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Data
@Embeddable
public class AttachImage {
    private String UUID;
    private String fileName;
    private String uploadPath;

    public AttachImage(final String UUID, final String fileName, final String uploadPath) {
        this.UUID = UUID;
        this.fileName = fileName;
        this.uploadPath = uploadPath;
    }
}