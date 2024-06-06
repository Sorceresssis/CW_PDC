package com.jk204.servlet.user;

import com.jk204.dao.TeamDao;
import com.jk204.dao.TeamMemberDao;
import com.jk204.dao.UserDao;
import com.jk204.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TeamInvitationServlet", value = "/user/TeamInvitationServlet")
public class TeamInvitationServlet extends HttpServlet {
    private final TeamMemberDao teamMemberDao = new TeamMemberDao();
    private final TeamDao teamDao = new TeamDao();
    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            int userId = (int) request.getSession().getAttribute("userId");//登录人的信息（发送邀请链接人的id）
            int teamId = Integer.parseInt(request.getParameter("team_id"));
            String uid = request.getParameter("uid"); //被邀请人 （暂未加入队伍人的uid）
            //1根据标识符uid检查邀请者是否为队长 如果非队长则无邀请权限 只能查看 (在teamDao中检查是否为队长)
            //1.1.1先查看该队伍的队长id
            int captainId = teamDao.queryCaptainIdByTeamId(teamId);
            //1.1.2.1看登录人是否是队长 若是则有邀请权限 若不是则无法邀请队友
            if (userId == captainId) {
                //1.2是队长之后，检查队伍是否已满员 若是满了 则不能再邀请人员 最多只有四人(在TeamMemberDao中) false未满员 true已满员；
                if (teamMemberDao.queryCountTeamMemberByTeamId(teamId) < 4) {
                    //1.3查看被邀请人是否为同组比赛其他队伍的队长 若是则不能加入队伍 若不是则可以加入队伍
                    //1.3.1查询该队伍的比赛id
                    int contestId = teamDao.queryContestIdByTeamId(teamId);
                    //1.3.2根据该用户uid查询被邀请者id:inviteeId，看与其他队伍的队长是否匹配
                    int inviteeId = userDao.queryUserIdByUid(uid);//userid
                    if (inviteeId == 0) {
                        out.print(Result.error("该用户不存在"));
                        return;
                    }
                    if (!teamDao.booleanInviteeIdentityById(inviteeId, contestId)) {
                        //如果不是其他队伍的队长 则自动加入队伍 写入数据库;
                        out.print(teamMemberDao.InsertNewMemberIntoTeam(teamId, inviteeId) == true ?
                                Result.success(null)
                                : Result.error("邀请失败，该成员已在队伍内"));
                    } else {
                        Result.error("该比赛中被邀请人已组队，暂不可邀请");
                    }
                } else {//1.2.2已满员
                    Result.error("该队伍已满员，暂不可以邀请新成员");
                }
            } else {
                //1.1.2.2不是队长 无法邀请
                Result.error("您非队伍队长，暂无邀请权限");
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
