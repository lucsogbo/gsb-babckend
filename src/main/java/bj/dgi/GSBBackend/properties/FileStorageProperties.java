package bj.dgi.GSBBackend.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
public class FileStorageProperties {

    private String uploadDir;

    /*
    Créer pour stocker les photos de profiles
     */
    private String uploadDirImages;


//    public String getUploadDir() {
//        return uploadDir;
//    }
//
//    public void setUploadDir(String uploadDir) {
//        this.uploadDir = uploadDir;
//    }

}
