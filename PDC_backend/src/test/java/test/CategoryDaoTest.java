package test;

import com.jk204.dao.CategoryDao;
import com.jk204.domain.Category;
import com.jk204.pojo.KeyOfName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryDaoTest {
    private CategoryDao categoryDao;

    @BeforeEach
    public void init() {
        categoryDao = new CategoryDao();
    }

    @Test
        //已写
    void queryCategorys() {
        List<Category> queryCategorys = categoryDao.queryCategorys();
        assertEquals(7, queryCategorys.size());

    }

    @Test
        //已写
    void getCategoryById() {
        KeyOfName getCategoryById = categoryDao.getCategoryById(1);
        assertEquals("影视类", getCategoryById.getName());
    }
}