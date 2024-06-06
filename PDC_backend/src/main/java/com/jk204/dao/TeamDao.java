package com.jk204.dao;

import com.jk204.domain.Team;
import com.jk204.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TeamDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //组建队伍(创建队伍参加比赛)
    public Boolean createTeam(String team_name, int captain_id, int contest_id) {
        try {
            String sql = "insert into team (name, captain_id, contest_id) VALUES (?,?,?)";
            return template.update(sql, team_name, captain_id, contest_id) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // 根据队长id查看队伍详情
    public Team queryTeamByCaptainIdContestId(int captainId, int contestId) {
        try {
            String sql = "select * from team where captain_id=? and contest_id=?;";
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Team.class),
                    captainId, contestId);
        } catch (Exception e) {
            return null;
        }
    }

    //根据队伍id查看某队伍的队长id(发送组队邀请)【已测试】
    public int queryCaptainIdByTeamId(int team_id) {
        int captain_id = 0;
        try {
            String sql = "select captain_id from team where id=?";
            captain_id = template.queryForObject(sql, Integer.class, team_id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return captain_id;
        }
    }

    //根据队伍id查询比赛id（contestid）[已测试]
    public int queryContestIdByTeamId(int team_id) {
        int contest_id = 0;
        try {
            String sql = "select contest_id from team where id=?";
            contest_id = template.queryForObject(sql, Integer.class, team_id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return contest_id;
        }
    }


    //根据用户id查看此人是否为同组比赛其他队伍的的队长(发送组队邀请+创建队伍)true是 false不是[已测试]
    public Boolean booleanInviteeIdentityById(int inviteeId, int contestId) {
        List<Team> teams = null;
        Boolean flag = false;
        try {
            String sql = "select captain_id from team where contest_id=?";
            teams = template.query(sql, new BeanPropertyRowMapper<>(Team.class), contestId);
            for (Team i : teams
            ) {
                if (i.getCaptain_id() == inviteeId) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return flag;
        }
    }

    //查询队伍名称是否已经存在【已测试】
    public boolean isExistTeamNameInContest(String teamName, int contestId) {
        String sql = "SELECT COUNT(*) FROM team WHERE contest_id = ? AND name = ?;";
        return template.queryForObject(sql, Integer.class, contestId, teamName) != 0;
    }

    // 已经创建队伍的队长不能再创建队伍
    public boolean isTeamCaptainOfContest(int userId, int contestId) {
        String sql = "SELECT COUNT(*) FROM team WHERE contest_id = ? AND captain_id = ?;";
        return template.queryForObject(sql, Integer.class, contestId, userId) != 0;
    }

    //根据队伍ID查询队伍
    public Team queryTeamByTeamId(int teamId) {
        try {
            String sql = "select * from team where id=?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Team.class), teamId);
        } catch (Exception e) {
            return null;
        }
    }

    //移除队员【已测试】
    public Boolean RemoveTeamMember(int memberId, int teamId) {
        int rows = 0;
        try {
            String sql = "delete from team_member where user_id=? and team_id=?";
            rows = template.update(sql, memberId, teamId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return rows > 0;
        }
    }

    //解散队伍 删除成员 退出比赛 teammember team[已测试]
    public Boolean DisbandsTheTeam(int team_id) {
        int a = 0, b = 0;
        try {
            String sqlteammember = "delete from team_member where team_id=?";
            String sqlteam = "delete from team where id=?";
            a = template.update(sqlteammember, team_id);
            b = template.update(sqlteam, team_id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return a > 0 && b > 0;
        }
    }

    //根据用户ID和比赛ID查询队伍
    public List<Team> queryTeamByContestIdUserId(int userId, int contestId) {
        String sql = "SELECT t.* FROM team t JOIN team_member tm ON t.id = tm.team_id WHERE t.contest_id = ? AND tm.user_id = ?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Team.class), contestId, userId);
    }

    //重命名队伍
    public boolean RenameTeam(int teamId, String teamName) {
        String sql = "UPDATE team SET name = ? WHERE id = ?;";
        return template.update(sql, teamName, teamId) > 0;
    }

    public List<Team> queryTeamsByUserId(int userId) {
        String sql = "SELECT * FROM team WHERE captain_id = ? UNION ALL SELECT t.* FROM team t JOIN team_member tm ON t.id = tm.team_id WHERE tm.user_id = ?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Team.class),
                userId, userId);
    }

    // 查询用户参加并且获奖的队伍
    public List<Team> queryTeamOfAwardByUserId(int userId) {
        String sql = "SELECT jt.* FROM(SELECT * FROM team WHERE captain_id = ? UNION ALL SELECT t.* FROM team t JOIN team_member tm ON t.id = tm.team_id WHERE tm.user_id = ?) jt JOIN work w ON jt.id = w.team_id WHERE w.award != 0 ORDER BY jt.id DESC;";
        return template.query(sql, new BeanPropertyRowMapper<>(Team.class), userId, userId);
    }

    // 查询某个比赛下的完成作品的所有队伍
    public List<Team> queryTeamsOfHaveWorkByContestId(int contestId) {
        // 通过内链接筛选出有作品的队伍
        try {
            String sql = "SELECT t.* FROM contest c JOIN team t ON c.id = t.contest_id JOIN work w ON w.team_id = t.id WHERE c.id = ?;";
            return template.query(sql, new BeanPropertyRowMapper<>(Team.class), contestId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Team> queryTeamOfAwardByContestId(int contestId) {
        String sql = "SELECT t.* FROM team t JOIN contest c ON t.contest_id = c.id JOIN work w ON t.id = w.team_id WHERE w.award != 0 AND c.id = ?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Team.class), contestId);
    }
}
