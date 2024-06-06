package com.jk204.dao;

import com.jk204.domain.Category;
import com.jk204.pojo.KeyOfName;
import com.jk204.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //获取所有分类
    public List<Category> queryCategorys() {
        String sql = "SELECT id, name FROM category;";
        return template.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    //根据分类ID获取分类名称
    public KeyOfName getCategoryById(int id) {
        String sql = "SELECT name FROM category WHERE id=?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(KeyOfName.class), id);
    }
}
