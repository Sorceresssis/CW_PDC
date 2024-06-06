package com.jk204.service;

import com.jk204.dao.*;
import com.jk204.domain.Team;
import com.jk204.domain.TeamMember;
import com.jk204.domain.Work;
import com.jk204.pojo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目所有于查询相关的核心
 * 比赛详情的结果展示需要的是 workFullInfo, TeamFullInfo
 * 比赛详情的报名情况需的是 TeamFullInfo(还没有队伍报名), workFullInfo(只是有些队伍没有长传作品)
 * 用户中心的参加过的队伍需要的是 TeamFullInfo, ContestTitle(点击可以直接跳跃到比赛详情)
 * 用户中心的获奖作品需要的是 TeamFullInfo, ContestTitle, workFullInfo(点击可以直接跳跃到比赛详情)
 * 管理员再颁发奖项的时候需要的是 TeamFullInfo, workFullInfo
 * /
 * 通过以上的所有的信息可得PDC项目的核心就是：团队，作品，比赛的关系，如何将这三者联系起来？
 * 在dao层和servlet层添加一个service层，将这三者联系起来,
 * 优点：因为我需要的筛选信息时可以在service层操作，代码复用，不需要建立很多新的pojo类，把数据筛选从Dao层解耦
 * /
 * 选择团队作为联通关系的纽带是最合适的，因为通过团队id不但可以找到团队信息，
 * 团队和作品是一对一的关系，所以也可以找到作品信息,
 * 团队和比赛一对多，所以也可以找到比赛信息
 */
public class ContestTeamWorkService {
    private final ContestDao contestDao = new ContestDao();
    private final WorkDao workDao = new WorkDao();
    private final WorkImageDao workImageDao = new WorkImageDao();
    private final TeamMemberDao teamMemberDao = new TeamMemberDao();
    private final UserDao userDao = new UserDao();

    /**
     * @param t      团队信息
     * @param option 选项,长度为3的Boolean数组，分别代表是否需要团队信息，作品信息，比赛信息
     * @return ContestTeamWork
     */
    public ContestTeamWork getContestTeamWork(Team t, Boolean[] option) throws Exception {
        if (option.length != 3) throw new Exception("option length must be 3");
        ContestTeamWork ctw = new ContestTeamWork();
        // 查询队伍信息
        if (option[0]) {
            TeamFullInfo teamFullInfo = new TeamFullInfo();
            // 队伍id
            teamFullInfo.setId(t.getId());
            // 队名
            teamFullInfo.setName(t.getName());
            // 加入队长信息
            teamFullInfo.setCaptain(userDao.queryUserProfileById(t.getCaptain_id()));
            // 查询队员id
            List<TeamMember> tms = teamMemberDao.queryTeamMemberByTeamId(t.getId());
            // 根据队员id查队员信息
            List<UserProfile> membersProfile = new ArrayList<>(tms.size());
            for (TeamMember tm : tms) {
                membersProfile.add(userDao.queryUserProfileById(tm.getUser_id()));
            }
            teamFullInfo.setMember(membersProfile);
            ctw.setTeam(teamFullInfo);
        }
        // 查询作品信息
        if (option[1]) {
            // 要注意work可能为空,因为有些队伍没有上传作品
            Work work = workDao.queryWorkByTeamId(t.getId());
            ctw.setWork(work == null ? null : new WorkFullInfo(
                    work.getId(), work.getTitle(), work.getIntro(), work.getLikes(),
                    work.getAward(),
                    work.getAward_time(),
                    workImageDao.queryImgUrlByWorkId(work.getId()),
                    work.getUpdate_time()
            ));
        }
        // 查询比赛信息
        if (option[2]) {
            ContestDetails contest = contestDao.ContestDetailById(t.getContest_id());
            ctw.setContest(new ContestTitle(contest.getId(), contest.getName()));
        }
        return ctw;
    }
}
