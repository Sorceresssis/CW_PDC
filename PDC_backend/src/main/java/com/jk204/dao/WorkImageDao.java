package com.jk204.dao;

import com.jk204.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class WorkImageDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //根据作品ID查询作品图片
    public List<String> queryImgUrlByWorkId(int workId) {
        String sql = "select imgUrl from work_image where work_id = ?";
        return template.queryForList(sql, String.class, workId);
    }

    public boolean addImgUrlByWorkId(int workId, String imgUrl) {
        String sql = "INSERT INTO work_image(imgUrl, work_id) VALUES(?,?);";
        return template.update(sql, imgUrl, workId) > 0;
    }

    public boolean removeImgUrlByWorkId(int workId, String imgUrl) {
        String sql = "DELETE FROM work_image WHERE work_id = ? AND imgUrl = ?;";
        return template.update(sql, workId, imgUrl) > 0;
    }
}