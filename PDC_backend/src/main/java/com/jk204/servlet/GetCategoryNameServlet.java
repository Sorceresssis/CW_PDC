package com.jk204.servlet;

import com.jk204.dao.CategoryDao;
import com.jk204.pojo.KeyOfName;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetCategoryNameServlet", value = "/GetCategoryNameServlet")
public class GetCategoryNameServlet extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据分类ID获取分类名称
        PrintWriter out = response.getWriter();
        try {
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            KeyOfName categoryName = categoryDao.getCategoryById(categoryId);
            out.print(categoryName == null ? Result.error("数据不存在") : Result.success(categoryName));
        } catch (Exception e) {
            out.print(Result.error("系统错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
