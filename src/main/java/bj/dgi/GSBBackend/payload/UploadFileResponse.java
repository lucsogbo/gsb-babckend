package bj.dgi.GSBBackend.payload;


import bj.dgi.GSBBackend.entities.Fichier;

public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    //private Fichier fichier;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
       // this.fichier=fichier;
    }

//    public Fichier getFichier() {
//        return fichier;
//    }
//
//    public void setFichier(Fichier fichier) {
//        this.fichier = fichier;
//    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
