package com.jk204.servlet.user;

import com.jk204.dao.TeamDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RenameOfTeamServlet", value = "/user/RenameOfTeamServlet")
public class RenameOfTeamServlet extends HttpServlet {
    private final TeamDao teamDao = new TeamDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //获取队伍id
            int teamId = Integer.parseInt(request.getParameter("team_id"));
            //获取队伍新名字
            String teamName = request.getParameter("team_name");
            // 判断是请求人否为队长
            if ((int) request.getSession().getAttribute("userId") == teamDao.queryTeamByTeamId(teamId).getCaptain_id()) {
                out.print(teamDao.RenameTeam(teamId, teamName) ? Result.success(null) : Result.error("修改失败"));
            } else {
                out.print(Result.error("你并非队伍队长，无法修改队名"));
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
