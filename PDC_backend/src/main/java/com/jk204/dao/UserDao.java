package com.jk204.dao;

import com.jk204.domain.User;
import com.jk204.pojo.UserProfile;
import com.jk204.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //注册新用户
    public boolean addUser(int rule, long uid, String username, String pwd, String salt, String phone) {
        try {
            String sql = "INSERT INTO user ( rule, uid, nickname, username, pwd, salt, phone )VALUES (?, ?, ?, ?, ?, ?, ?);";
            //3.调用update方法，写入数据库
            return template.update(sql, rule, uid, username, username, pwd, salt, phone) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //根据ID查询用户
    public User queryUserById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?;";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    //根据ID查询用户信息
    public UserProfile queryUserProfileById(int id) {
        try {
            String sql = "SELECT uid, nickname FROM user WHERE id = ?;";
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(UserProfile.class), id);
        } catch (Exception e) {
            return null;
        }
    }

    //通过用户名查询用户
    public User getUserByUsername(String username) {
        try {
            String sql = "SELECT * FROM user WHERE username = ? OR phone = ?";
            return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, username);
        } catch (Exception e) {
            return null;
        }
    }

    //根据被邀请人的uid获取用户的id（发送组队邀请）
    public int queryUserIdByUid(String uid) {
        int userId = 0;
        try {
            String sql = "select id from user where uid=?";
            userId = template.queryForObject(sql, Integer.class, uid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userId;
        }
    }

    public List<UserProfile> queryUserProfilesByUidLikeSearch(String search, int pageNo, int pageSize) {
        try {
            String sql = "SELECT uid, nickname FROM user WHERE uid LIKE ? AND rule = 0 LIMIT ?,?;";
            return template.query(sql, new BeanPropertyRowMapper<>(UserProfile.class),
                    search + "%", (pageNo - 1) * pageSize, pageSize);
        } catch (Exception e) {
            return null;
        }
    }
}
