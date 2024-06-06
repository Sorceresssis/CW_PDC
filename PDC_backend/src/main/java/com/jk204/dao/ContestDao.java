package com.jk204.dao;

import com.jk204.domain.Contest;
import com.jk204.pojo.ContestDetails;
import com.jk204.pojo.ContestProfile;
import com.jk204.pojo.NameValue;
import com.jk204.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ContestDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    public List<NameValue> queryCountContestGroupByCategory() {
        try {
            String sql = "SELECT cg.id, cg.name, COUNT(*) AS value FROM contest ct JOIN category cg ON ct.category_id = cg.id GROUP BY cg.id;";
            return template.query(sql, new BeanPropertyRowMapper<>(NameValue.class));
        } catch (Exception e) {
            return null;
        }
    }

    public List<ContestProfile> queryAllContest(int pageNo, int pageSize) {
        String sql = "SELECT * FROM contest ORDER BY starting_time DESC LIMIT ?,?";
        return template.query(sql, new BeanPropertyRowMapper<>(ContestProfile.class),
                (pageNo - 1) * pageSize, pageSize);
    }

    //主页分页寻找比赛
    public List<ContestProfile> queryContestsByCategoryId(int categoryId, int pageNo, int pageSize) {
        // 根据开始时间排序
        String sql = "SELECT id, name, `year`, topic, starting_time, registration_deadline, result_announcement_time FROM contest WHERE category_id=? ORDER BY starting_time DESC limit ?,?;";
        return template.query(sql, new BeanPropertyRowMapper<>(ContestProfile.class), categoryId,
                (pageNo - 1) * pageSize, pageSize);
    }

    public List<ContestProfile> queryContestsByKeyword(String keyword, int pageNo, int pageSize) {
        // 由分词器根据全文索引搜索，并且根据匹配度排序，匹配度越高，越靠前
        String sql = "SELECT id, name, year, topic, starting_time, registration_deadline, result_announcement_time FROM contest WHERE MATCH(name) AGAINST(?) ORDER BY MATCH(name) AGAINST(?) DESC LIMIT ?,?;";
        return template.query(sql, new BeanPropertyRowMapper<>(ContestProfile.class),
                keyword, keyword, (pageNo - 1) * pageSize, pageSize);
    }
    
    //通过id获取比赛的详细信息ContestDetailById
    public ContestDetails ContestDetailById(int id) {
        ContestDetails contestd = null;
        try {
            String sql = "SELECT c.id, c.name, c.year, c.topic, c.intro, c.starting_time, c.registration_deadline, c.result_announcement_time,( CASE WHEN NOW()< c.starting_time THEN 1 WHEN NOW() < c.registration_deadline THEN 2 WHEN NOW() < c.result_announcement_time THEN 3 ELSE 4 END) AS status, cg.id AS category_id FROM contest c LEFT JOIN category cg ON c.category_id = cg.id WHERE c.id = ?;";
            contestd = template.queryForObject(sql, new BeanPropertyRowMapper<>(ContestDetails.class), id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return contestd;
        }
    }

    /**
     * 获取该比赛的状态1为未开始，2为报名中，3为评选中，4为已结束
     */
    public int getContestStutasById(int id) {
        try {
            String sql = "select (CASE WHEN NOW()< starting_time  THEN 1\n" +
                    "WHEN NOW() < registration_deadline THEN 2\n" +
                    "WHEN NOW() < result_announcement_time THEN 3\n" +
                    "ELSE 4 END) AS status\n" +
                    "from contest where id = ?;";
            return template.queryForObject(sql, Integer.class, id);
        } catch (Exception e) {
            return 0;
        }
    }

    //管理员添加比赛
    public boolean addContest(Contest contest) {
        String sql = "INSERT INTO contest(name,year,topic,intro,starting_time,registration_deadline,result_announcement_time,category_id) VALUES (?,?,?,?,?,?,?,?);";
        return template.update(sql,
                contest.getName(),
                contest.getYear(),
                contest.getTopic(),
                contest.getIntro(),
                contest.getStarting_time(),
                contest.getRegistration_deadline(),
                contest.getResult_announcement_time(),
                contest.getCategory_id()
        ) == 1;
    }

    //管理员修改比赛
    public boolean editContest(int contestId, Contest contest) {
        try {
            String sql = "UPDATE contest SET name=?,year=?,topic=?,intro=?,starting_time=?,registration_deadline=?,result_announcement_time=?,category_id=? WHERE id=?;";
            return template.update(sql,
                    contest.getName(),
                    contest.getYear(),
                    contest.getTopic(),
                    contest.getIntro(),
                    contest.getStarting_time(),
                    contest.getRegistration_deadline(),
                    contest.getResult_announcement_time(),
                    contest.getCategory_id(),
                    contestId
            ) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    //管理员删除比赛
    public boolean deleteContest(int contestId) {
        String sql = "DELETE FROM contest WHERE id=?;";
        return template.update(sql, contestId) == 1;
    }
}
