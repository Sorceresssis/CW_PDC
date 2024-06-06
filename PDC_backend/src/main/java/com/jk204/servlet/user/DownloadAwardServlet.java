package com.jk204.servlet.user;

import com.itextpdf.text.DocumentException;
import com.jk204.dao.WorkDao;
import com.jk204.domain.Work;
import com.jk204.util.ITextUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "DownloadAwardServlet", value = "/DownloadAwardServlet")
public class DownloadAwardServlet extends HttpServlet {
    private final WorkDao workDao = new WorkDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DownloadAwardServlet");
        int workId = Integer.parseInt(request.getParameter("workId"));
        Work w = workDao.queryWorkById(workId);
        Map<String, String> data = new HashMap<>();
        data.put("name", (String) request.getSession().getAttribute("username")); // 字段需要对应pdf模板里面的名称
        data.put("award", w.getAward() == 1 ? "一等奖" : w.getAward() == 2 ? "二等奖" : "三等奖");

        String filePath = getServletContext().getRealPath("/awardPDF/") + "awardTemplate.pdf";
        String newPDFPath = getServletContext().getRealPath("/awardPDF/") + UUID.randomUUID() + ".pdf";
        try {
            ITextUtil.geratePdf(filePath, newPDFPath, data);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        File file = new File(newPDFPath);
        if (file.exists()) {
            // 设置响应的内容类型
            response.setContentType("application/octet-stream");
            // 设置响应的头信息，指示浏览器下载文件
            response.setHeader("Content-Disposition", "attachment; filename=\"award.pdf\"");

            // 创建输入流读取文件内容
            FileInputStream fis = new FileInputStream(file);
            // 获取响应的输出流
            OutputStream os = response.getOutputStream();

            // 将文件内容写入响应的输出流中
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            // 关闭输入流和输出流
            fis.close();
            os.close();
        } else {
            // 文件不存在，返回错误信息
            response.getWriter().println("File not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
