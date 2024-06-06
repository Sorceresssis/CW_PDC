package com.jk204.dao;

import com.jk204.domain.Work;
import com.jk204.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

public class WorkDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //根据作品ID查询作品点赞数
    public boolean likeWork(int workId) {
        try {
            String sql = "UPDATE work SET likes=likes+1 WHERE id = ?";
            return template.update(sql, workId) > 0;
        } catch (Exception e) {
            return false;
        }
    }


    //查询并计数该作品所参加的比赛下与管理员评定等级awardGrade相同的等级奖项有几个
    public int queryCountOfContestAwardByWorkId(int workId, int awardGrade) {
        String sql = "SELECT COUNT(*) FROM WORK w JOIN team t ON w.team_id = t.id JOIN( SELECT t.contest_id FROM WORK w JOIN team t ON w.team_id = t.id WHERE w.id = ?) sub ON t.contest_id = sub.contest_id WHERE w.award = ?;";
        return template.queryForObject(sql, Integer.class, workId, awardGrade);
    }

    //根据作品ID和管理员作品评级为作品评级
    public boolean addAwardByWorkId(int workId, int awardGrade) {
        try {
            String sql = "UPDATE work SET award = ?, award_time = NOW() WHERE id = ?;";
            return template.update(sql, awardGrade, workId) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // 移除奖项
    public boolean removeAwardByWorkId(int workId) {
        try {
            String sql = "UPDATE work SET award = 0, award_time = NULL WHERE id = ?;";
            return template.update(sql, workId) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    //根据团队ID查询作品ID
    public Work queryWorkByTeamId(int teamId) {
        try {
            String sql = "Select * FROM work WHERE team_id=?;";
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Work.class), teamId);
        } catch (Exception e) {
            return null;
        }
    }

    public int addWork(Work w) {
        try {
            String sql = "INSERT INTO work(title, intro, team_id)VALUES(?,?,?);";
            // 创建一个KeyHolder对象用于存储生成的主键值
            KeyHolder keyHolder = new GeneratedKeyHolder();
            template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, w.getTitle());
                ps.setString(2, w.getIntro());
                ps.setInt(3, w.getTeam_id());
                return ps;
            }, keyHolder);
            // 通过KeyHolder对象获取生成的主键ID
            return Objects.requireNonNull(keyHolder.getKey()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public Work queryWorkById(int workId) {
        try {
            String sql = "SELECT * FROM work WHERE id = ?;";
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Work.class), workId);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateWork(Work work) {
        try {
            String sql = "UPDATE work SET title = ?, intro = ? WHERE id = ?;";
            return template.update(sql, work.getTitle(), work.getIntro(), work.getId()) > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
