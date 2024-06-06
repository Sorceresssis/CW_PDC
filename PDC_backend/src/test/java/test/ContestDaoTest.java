package test;

import com.jk204.dao.ContestDao;
import com.jk204.domain.Contest;
import com.jk204.pojo.ContestDetails;
import com.jk204.pojo.ContestProfile;
import com.jk204.pojo.NameValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContestDaoTest {
    private ContestDao contestDao;

    //初始化init
    @BeforeEach
    public void init() {
        contestDao = new ContestDao();
    }


    @Test
        //已写
    void contestDetailById() {
        ContestDetails contestd = contestDao.ContestDetailById(10);
        assertEquals("公益类", contestd.getTopic());
        System.out.println(contestd.getTopic());
    }

    @Test
        //已写
    void queryContests() {
        List<ContestProfile> queryContests = contestDao.queryContestsByCategoryId(1, 1, 4);
        assertEquals("音乐剧海报设计大赛", queryContests.get(1).getName());
    }

    @Test
        //已写
    void getContestStutasById() {
        int getContestStutasById = contestDao.getContestStutasById(6);
        assertEquals(2, getContestStutasById);
    }

    @Test
        //已写
    void queryContestsByKeyword() {
        List<ContestProfile> queryContestsByKeyword = contestDao.queryContestsByKeyword("大凉山", 1, 4);
        assertEquals("2020届“大凉山西昌民族电影周”海报征集", queryContestsByKeyword.get(0).getName());
    }

    @Test
        //已写
    void addContest() {
        Contest contest = new Contest("niubi", "2002", "电影", "null", "2002-02-02", "2002-02-06", "2002-02-20", 1);
        boolean addContest = contestDao.addContest(contest);
        assertEquals(true, addContest);
    }

    @Test
        //已写
    void editContest() {
        Contest contest = new Contest("niubi", "2002", "电影", "null", "2002-02-02", "2002-02-06", "2002-02-20", 1);
        boolean editContest = contestDao.editContest(1, contest);
        assertEquals(true, editContest);
    }

    @Test
        //已写
    void deleteContest() {
        boolean deleteContest = contestDao.deleteContest(3);
        assertEquals(true, deleteContest);
    }

    @Test
    void queryCountContestGroupByCategory() {
        List<NameValue> queryCountContestGroupByCategory = contestDao.queryCountContestGroupByCategory();
        assertEquals("影视类", queryCountContestGroupByCategory.get(0).getName());
    }
}
