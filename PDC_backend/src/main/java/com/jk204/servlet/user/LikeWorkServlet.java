package com.jk204.servlet.user;

import com.jk204.dao.WorkDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "LikeWorkServlet", value = "/user/LikeWorkServlet")
public class LikeWorkServlet extends HttpServlet {
    private final WorkDao workDao = new WorkDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            int workId = Integer.parseInt(request.getParameter("work_id"));
            HttpSession session = request.getSession();
            Set<Integer> likeSet = (HashSet<Integer>) session.getAttribute("likeSet");

            if (likeSet == null) likeSet = new HashSet<>();

            if (likeSet.contains(workId)) {
                out.print(Result.error("您已经点过赞了"));
            } else {
                likeSet.add(workId);
                session.setAttribute("likeSet", likeSet);
                out.print(workDao.likeWork(workId) ? Result.success("点赞成功") : Result.error("点赞失败"));
            }
        } catch (Exception e) {
            out.print(Result.error("系统错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
