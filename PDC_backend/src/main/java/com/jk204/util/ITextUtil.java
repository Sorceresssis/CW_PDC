package com.jk204.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ITextUtil {
    // 测试, 具体使用方法参考main方法
    public static void main(String[] args) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("name", "何骏涛"); // 字段需要对应pdf模板里面的名称
        data.put("award", "一等奖");

        geratePdf("src/main/resources/awardTemplate.pdf",
                "src/main/resources/testA.pdf",
                data);
    }

    /**
     * 生成pdf
     *
     * @param fileName   模板文件路径
     * @param newPDFPath 生成的新文件路径
     * @param data       生成的数据 key为pdf模板中的字段名称 value为需要填入的值
     */
    public static void geratePdf(String fileName, String newPDFPath, Map<String, String> data) throws IOException, DocumentException {
        try {
            PdfReader reader = new PdfReader(fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            /* 2.读取PDF模板内容 */
            PdfStamper ps = new PdfStamper(reader, bos);

            /* 3.法1:设置使用itext-asian.jar的中文字体 */
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			/* 法2:使用项目下的自定义的中文字体
			bfChinese = BaseFont.createFont("static/font/SIMSUN.TTC,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);*/

			/* 法3:使用windows系统下的字体库
			bfChinese = BaseFont.createFont("c://windows//fonts//simsun.ttc,1",BaseFont.IDENTITY_H, false);*/

            ArrayList<BaseFont> fontList = new ArrayList<>();
            fontList.add(bf);

            /* 4.获取模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            fields.setSubstitutionFonts(fontList);

            fillData(fields, data);            //调用方法执行写入

            /* 必须要调用这个，否则文档不会生成的 */
            ps.setFormFlattening(true);
            ps.close();

            /* 5.将要生成的目标PDF文件名称 */
            OutputStream fos = Files.newOutputStream(Paths.get(newPDFPath));
            fos.write(bos.toByteArray());
//            System.out.println("新证书已生成:" + newPDFPath);
            fos.flush();
            fos.close();
            bos.close();
        } catch (IOException | DocumentException e) {
            System.out.println("证书生成失败,原因:" + e.getLocalizedMessage());
        }
    }

    // 获取pdf模板中有哪些字段key+赋值的值value
    public static void fillData(AcroFields fields, Map<String, String> data) throws IOException, DocumentException {
        for (String key : data.keySet()) {
            String value = data.get(key); // 调用data方法获取值
            fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
        }
    }
}
