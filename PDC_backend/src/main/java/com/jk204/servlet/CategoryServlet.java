package com.jk204.servlet;

import com.jk204.dao.CategoryDao;
import com.jk204.domain.Category;
import com.jk204.pojo.Result;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    private final CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取竞赛分类
        PrintWriter out = response.getWriter();
        try {
            out.print(Result.success(categoryDao.queryCategorys()));
        } catch (Exception e) {
            out.print(Result.error("服务器错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
