package com.dsmh.common.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * @author: Asher
 * @date: 2022/4/25 20:13
 * @description:
 */
@Slf4j
public class PdfUtil {

    public static void main(String[] args) throws IOException, DocumentException {
       /* for(int i=0;i<=100;i++){
            FileOutputStream fOut = new FileOutputStream("D://text"+ i +".pdf");
            List<PdfItextModel> pdfItextModels = new ArrayList<>();
            //报单号
            pdfItextModels.add(new PdfItextModel(160F, 662.5F, 1, RandomUtil.randomChinese() + RandomUtil.randomChinese() + "",getBaseFont(), 11));

            //被保险人
            pdfItextModels.add(new PdfItextModel(140F, 638.5F, 1, "被保险人" + i, getBaseFont(), 11));
            pdfItextModels.add(new PdfItextModel(340F, 638.5F, 1, "男", getBaseFont(), 11));
            pdfItextModels.add(new PdfItextModel(155F, 621.5F, 1, "证件类型", getBaseFont(), 11));
            pdfItextModels.add(new PdfItextModel(360F, 621.5F, 1, RandomUtil.randomNumbers(18), getBaseFont(), 11));
            pdfItextModels.add(new PdfItextModel(370F, 606.5F, 1, RandomUtil.randomNumbers(11), getBaseFont(), 11));
            //委托人
            pdfItextModels.add(new PdfItextModel(175F, 350.5F, 1, "委托人", getBaseFont(), 11));
            //申请日期
            pdfItextModels.add(new PdfItextModel(430F, 35.5F, 1, "申请日期", getBaseFont(), 11));
            OutputStream outputStream = signSinglePsw("https://frontend-1309422305.cos.ap-guangzhou.myqcloud.com/insr-docs/hgic_lipeishenqingshu.pdf", pdfItextModels);
            byte[] bytes = parse(outputStream).readAllBytes();
            fOut.write(bytes);
            outputStream.close();
            fOut.close();
        }*/
    }

    /**
     * 获取输出文件
     *
     * @param inputStream
     * @param list
     * @param fileName
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static MultipartFile signSinglePsw(InputStream inputStream, List<PdfItextModel> list, String fileName) {
        try (OutputStream outputStream = signSinglePsw(inputStream, list); InputStream pdfInputStream = parse(outputStream)) {
            return getMultipartFile(pdfInputStream, fileName);
        } catch (Exception ex) {
            log.error("###signSinglePsw error", ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 获取输出流
     *
     * @param pdfUrl
     * @param list
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static OutputStream signSinglePsw(String pdfUrl, List<PdfItextModel> list) throws IOException, DocumentException {
        URL url = new URL(pdfUrl);
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(inputStream);
        inputStream.close();
        return signSinglePsw(reader, list, outputStream);
    }

    /**
     * 获取输出流
     *
     * @param inputStream
     * @param list
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static OutputStream signSinglePsw(InputStream inputStream, List<PdfItextModel> list) throws IOException, DocumentException {
        OutputStream outputStream = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(inputStream);
        inputStream.close();
        return signSinglePsw(reader, list, outputStream);
    }

    /**
     * @param oldPswFilePath 原来的文件地址
     * @param newPswPath
     * @param list           需要添加的详细信息
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public OutputStream signSinglePsw(String oldPswFilePath, String newPswPath, List<PdfItextModel> list) throws IOException, DocumentException {
        int lastIndex = oldPswFilePath.lastIndexOf('.');
        String suffix = oldPswFilePath.substring(lastIndex + 1);
        if (!"pdf".equalsIgnoreCase(suffix)) {
            throw new RuntimeException("Not is PDF file");
        }
        PdfReader reader = new PdfReader(oldPswFilePath);
        FileOutputStream fOut = new FileOutputStream(newPswPath);
        return signSinglePsw(reader, list, fOut);
    }

    private static OutputStream signSinglePsw(PdfReader reader, List<PdfItextModel> list, OutputStream outputStream) throws IOException, DocumentException {
        PdfStamper stp = new PdfStamper(reader, outputStream);
        for (PdfItextModel model : list) {
            Float xCoordinate = model.getXCoordinate();
            Float yCoordinate = model.getYCoordinate();
            Integer pageNum = model.getPageNum();
            String content = model.getContent();
            if (xCoordinate == null || yCoordinate == null ||
                    pageNum == null || pageNum == 0 || content == null || "".equals(content)) {
                continue;
            }
            PdfContentByte pdfContentByte = stp.getOverContent(pageNum);
            pdfContentByte.beginText();
            pdfContentByte.setFontAndSize(model.getBaseFont(), model.getFontSize());
            addDeptReview(xCoordinate, yCoordinate, pdfContentByte, content);
            pdfContentByte.endText();
        }
        stp.close();
        reader.close();
        return outputStream;
    }

    /**
     * 添加水印
     *
     * @param x       x轴坐标
     * @param y       y轴坐标
     * @param content 签字内容
     * @param keyword 水印内容
     */
    private static void addDeptReview(float x, float y, PdfContentByte content, String keyword) {
        content.setColorFill(BaseColor.BLACK);
        content.showTextAligned(Element.ALIGN_LEFT, keyword, x, y, 0);
    }

    /**
     * 获取基础文字
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static BaseFont getBaseFont() throws IOException, DocumentException {
        return BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
    }

    /**
     * 获取封装得MultipartFile
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return MultipartFile
     */
    public static MultipartFile getMultipartFile(InputStream inputStream, String fileName) throws IOException {
        FileItem fileItem = createFileItem(inputStream, fileName);
        return new CommonsMultipartFile(fileItem);
    }


    /**
     * FileItem类对象创建
     *
     * @param inputStream inputStream
     * @param fileName    fileName
     * @return FileItem
     */
    private static FileItem createFileItem(InputStream inputStream, String fileName) throws IOException {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";
        FileItem item = factory.createItem(textFieldName, MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
        int bytesRead;
        byte[] buffer = new byte[8192];
        try (OutputStream os = item.getOutputStream()) {
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error("###createFileItem error", e);
            throw new IllegalArgumentException("文件转化异常");
        }
        return item;
    }

    public static ByteArrayInputStream parse(OutputStream out) {
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PdfItextModel {
        private Float xCoordinate;

        private Float yCoordinate;

        private Integer pageNum;

        private String content;

        private BaseFont baseFont;

        private Integer fontSize;
    }
}
