package com.jk204.servlet;

import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "UploadServlet", value = "/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Part filePart = request.getPart("file");
            // 没有图片上传
            if (filePart.getSize() != 0) {
                String fileName = filePart.getSubmittedFileName();
                String ext = fileName.substring(fileName.lastIndexOf('.'));
                // 通过扩展名判断上传的是不是图片
                if (".jpg".equals(ext) || ".jpeg".equals(ext) || ".png".equals(ext)) {
                    String basename = UUID.randomUUID() + ext;
                    filePart.write(getServletContext().getRealPath("/workImg/") + basename);
                    out.print(Result.success(basename));
                    return;
                }
            }
            out.println(Result.error("上传失败，文件格式不正确！"));
        } catch (Exception e) {
            out.println(Result.error("服务器错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
