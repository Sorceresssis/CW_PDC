package com.jk204.dao;

import com.jk204.domain.TeamMember;
import com.jk204.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TeamMemberDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    // 查询队伍成员的数量
    public int queryCountTeamMemberByTeamId(int team_id) {
        String sql = "select count(*) from team_member where team_id=?";
        return template.queryForObject(sql, Integer.class, team_id);
    }

    //把新邀请队员加入到队伍成员中【已测试】
    public Boolean InsertNewMemberIntoTeam(int team_id, int inviteeId) {
        try {
            String sql = "insert into team_member(team_id, user_id) VALUES (?,?)";
            return template.update(sql, team_id, inviteeId) > 0;
        } catch (Exception e) {
            return false;
        }
    }

//    public Boolean deleteMember(int teamId, int userId) {
//        try {
//            String sql = "delete from team_member where team_id=? and user_id=?";
//            return template.update(sql, teamId, userId) > 0;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    //根据队伍ID查询团队成员
    public List<TeamMember> queryTeamMemberByTeamId(int teamId) {
        String sql = "SELECT id, team_id, user_id FROM team_member WHERE team_id =?;";
        return template.query(sql, new BeanPropertyRowMapper<>(TeamMember.class), teamId);
    }
}
