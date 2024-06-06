package test;

import com.jk204.dao.WorkDao;
import com.jk204.domain.Work;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkDaoTest {
    //重新
    private WorkDao workDao;

    @BeforeEach
    public void init() {
        workDao = new WorkDao();
    }

    @Test
        //已写
    void likeWork() {
        boolean Result = workDao.likeWork(6);
        assertEquals(true, Result);
    }

    @Test
        //已写
    void queryCountOfContestAwardByWorkId() {
        int Result = workDao.queryCountOfContestAwardByWorkId(6, 3);
        assertEquals(3, Result);
    }

    @Test
        //已写
    void addAwardByWorkId() {
        boolean Result = workDao.addAwardByWorkId(2, 1);
        assertEquals(true, Result);
    }

    @Test
        //已写
    void removeAwardByWorkId() {
        boolean Result = workDao.removeAwardByWorkId(2);
        assertEquals(true, Result);
    }

    @Test
    void queryWorkByTeamId() {
        Work work = workDao.queryWorkByTeamId(2);
        assertEquals(2, work.getTeam_id());
    }

    @Test
    void queryWorkById() {
        Work queryWorkById = workDao.queryWorkById(4);
        assertEquals("服装设计", queryWorkById.getTitle());
    }
}