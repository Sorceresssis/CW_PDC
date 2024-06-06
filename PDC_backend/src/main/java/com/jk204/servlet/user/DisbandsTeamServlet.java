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

@WebServlet(name = "DisbandsTeamServlet", value = "/user/DisbandsTeamServlet")
public class DisbandsTeamServlet extends HttpServlet {
    private final TeamDao teamDao = new TeamDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            //获取队伍id
            int teamId = Integer.parseInt(request.getParameter("team_id"));
            //1根据标识符uid检查修改者是否为队长 如果非队长则无修改权限 只能查看 (在teamDao中检查是否为队长)
            //1.1先查看该队伍的队长id
            int captaimId = teamDao.queryCaptainIdByTeamId(teamId);
            //1.2根据该用户id，并与队伍的队长进行匹配 若是一致则有修改权限 若是不一致 则无法移除队员（执行操作的）
            int userId = (int) request.getSession().getAttribute("userId");
            out.print(userId == captaimId ? Result.success(teamDao.DisbandsTheTeam(teamId)) : Result.error("您非队伍队长，无法解散队伍"));
        } catch (Exception e) {
            out.print(Result.error("系统错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
