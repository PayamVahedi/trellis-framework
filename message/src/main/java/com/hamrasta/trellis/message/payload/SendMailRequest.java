package com.hamrasta.trellis.message.payload;

import com.hamrasta.trellis.core.payload.RequestMessage;

import java.util.Arrays;
import java.util.List;

public class SendMailRequest extends RequestMessage {
    private String email;

    private String subject;

    private String body;

    private String imageName;

    private byte[] image;

    private String pdfName;

    private byte[] pdf;

    private List<EmbeddedData> embeddedData;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public List<EmbeddedData> getEmbeddedData() {
        return embeddedData;
    }

    public void setEmbeddedData(List<EmbeddedData> embeddedData) {
        this.embeddedData = embeddedData;
    }

    public SendMailRequest() {
    }

    public SendMailRequest(String email, String subject, String body) {
        this.email = email;
        this.subject = subject;
        this.body = body;
    }

    public SendMailRequest(String email, String subject, String body, String imageName, byte[] image) {
        this.email = email;
        this.subject = subject;
        this.body = body;
        this.imageName = imageName;
        this.image = image;
    }

    public SendMailRequest(String email, String subject, String body, String imageName, byte[] image, String pdfName, byte[] pdf) {
        this.email = email;
        this.subject = subject;
        this.body = body;
        this.imageName = imageName;
        this.image = image;
        this.pdfName = pdfName;
        this.pdf = pdf;
    }

    @Override
    public String toString() {
        return "SendMailRequest{" +
                "emailAddress='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", messageHtml='" + body + '\'' +
                ", imageName='" + imageName + '\'' +
                ", image=" + Arrays.toString(image) +
                ", pdfName='" + pdfName + '\'' +
                ", pdfData=" + Arrays.toString(pdf) +
                ", embeddedImages=" + embeddedData +
                '}';
    }
}
