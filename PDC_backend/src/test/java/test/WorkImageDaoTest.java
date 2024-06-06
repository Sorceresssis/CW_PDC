package test;

import com.jk204.dao.WorkImageDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkImageDaoTest {
    private WorkImageDao workImageDao;

    @BeforeEach
    public void init() {
        workImageDao = new WorkImageDao();
    }

    @Test
    void queryImgUrlByWorkId() {
        List<String> queryimgUrlByWorkId = workImageDao.queryImgUrlByWorkId(1);
        assertEquals("001.jpg", queryimgUrlByWorkId.get(0));
        System.out.println(queryimgUrlByWorkId.get(0));
    }
}