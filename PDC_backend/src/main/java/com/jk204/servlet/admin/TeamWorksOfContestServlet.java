package com.jk204.servlet.admin;

import com.jk204.dao.TeamDao;
import com.jk204.domain.Team;
import com.jk204.pojo.ContestTeamWork;
import com.jk204.pojo.Result;
import com.jk204.service.ContestTeamWorkService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TeamWorksOfContestServlet", value = "/admin/TeamWorksOfContestServlet")
public class TeamWorksOfContestServlet extends HttpServlet {
    private final ContestTeamWorkService contestTeamWorkService = new ContestTeamWorkService();
    private final TeamDao teamDao = new TeamDao();
    private final Boolean[] OPTION = {true, true, false};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            // 获取比赛id
            int contestId = Integer.parseInt(request.getParameter("contest_id"));
            // 获取团队信息， 找出所有参加比赛并且有作品的团队
            List<Team> teams = teamDao.queryTeamsOfHaveWorkByContestId(contestId);
            List<ContestTeamWork> ctwList = new ArrayList<>(teams.size());
            for (Team t : teams) {
                ctwList.add(contestTeamWorkService.getContestTeamWork(t, OPTION));
            }
            out.print(Result.success(ctwList));
        } catch (NullPointerException e) {
            out.print(Result.error("参数错误"));
        } catch (Exception e) {
            out.print(Result.error("服务器错误"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
