package com.jk204.servlet.user;

import com.jk204.dao.TeamDao;
import com.jk204.domain.Team;
import com.jk204.pojo.ContestTeamWork;
import com.jk204.pojo.Result;
import com.jk204.pojo.UserTeamOfContest;
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

/**
 * 再该比赛下用户的所有队伍，创建的队伍，加入的队伍
 */
@WebServlet(name = "UserTeamsOfContestServlet", value = "/user/UserTeamsOfContestServlet")
public class UserTeamsOfContestServlet extends HttpServlet {
    private final TeamDao teamDao = new TeamDao();
    private final ContestTeamWorkService contestTeamWorkService = new ContestTeamWorkService();
    private final Boolean[] OPTION = {true, true, false};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            int contestId = Integer.parseInt(request.getParameter("contest_id"));
            int userId = (int) request.getSession().getAttribute("userId");

            UserTeamOfContest userTeamOfContest = new UserTeamOfContest();
            // 根据Userid获得创建的队伍
            Team createdTeam = teamDao.queryTeamByCaptainIdContestId(userId, contestId);
            // 根据规则：队长只能报名一个队伍，组员不限制。所以队长不能加入队伍，组员不能创建队伍
            // 这就导致了created 和 joined 只能存在一个。
            // 如果用户已经创建过队伍了，
            if (createdTeam != null) {
                userTeamOfContest.setCreated(contestTeamWorkService.getContestTeamWork(createdTeam, OPTION));
            } else {
                // 加入的队伍列表 一个用户可以加入多个队伍
                List<Team> joinedTeam = teamDao.queryTeamByContestIdUserId(userId, contestId);
                List<ContestTeamWork> joined = new ArrayList<>(joinedTeam.size());
                for (Team t : joinedTeam) {
                    joined.add(contestTeamWorkService.getContestTeamWork(t, OPTION));
                }
                userTeamOfContest.setJoined(joined);
            }
            out.print(Result.success(userTeamOfContest));
        } catch (Exception e) {
            out.print(Result.error("服务器异常"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
