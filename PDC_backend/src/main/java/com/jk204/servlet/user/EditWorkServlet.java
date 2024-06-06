package com.jk204.servlet.user;

import com.jk204.dao.TeamDao;
import com.jk204.dao.WorkDao;
import com.jk204.dao.WorkImageDao;
import com.jk204.domain.Work;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EditWorkServlet", value = "/user/EditWorkServlet")
@MultipartConfig
public class EditWorkServlet extends HttpServlet {
    private final WorkDao workDao = new WorkDao();
    private final TeamDao teamDao = new TeamDao();
    private final WorkImageDao workImageDao = new WorkImageDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            int workId = Integer.parseInt(request.getParameter("work_id"));
            int teamId = Integer.parseInt(request.getParameter("team_id"));
            // 鉴权，判断是否是队长
            if (teamDao.queryTeamByTeamId(teamId).getCaptain_id() != (int) request.getSession().getAttribute("userId")) {
                out.print(Result.error("无权操作"));
                return;
            }

            String title = request.getParameter("title");
            String intro = request.getParameter("intro");
            String[] imgs = request.getParameterValues("imgs");
            String[] delImgs = request.getParameterValues("del_imgs");

            // 作品信息
            // 判断是添加还是修改
            if (workId == 0) {
                workId = workDao.addWork(new Work(title, intro, teamId));
            } else {
                workDao.updateWork(new Work(workId, title, intro));
            }
            // 处理图片
            if (workId == 0) {
                out.print(Result.error("添加失败"));
                return;
            }
            // 处理图片
            out.print(Result.success(handleImgs(imgs, delImgs, workId)));
        } catch (Exception e) {
            out.println(Result.error("系统错误"));
        }
    }

    private List<Boolean> handleImgs(String[] imgs, String[] delImgs, int workId) {
        List<Boolean> res = new ArrayList<>(delImgs.length);
        for (int i = 0; i < delImgs.length; i++) {
            // 1表示添加，0表示删除 
            res.add(Integer.parseInt(delImgs[i]) == 1
                    ? workImageDao.addImgUrlByWorkId(workId, imgs[i])
                    : workImageDao.removeImgUrlByWorkId(workId, imgs[i]));
        }
        return res;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
