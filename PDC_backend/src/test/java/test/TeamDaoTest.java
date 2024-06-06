package test;

import com.jk204.dao.TeamDao;
import com.jk204.domain.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamDaoTest {

    private TeamDao teamDao;

    @BeforeEach
    public void init() {
        teamDao = new TeamDao();
    }


    @Test
        //已写
    void queryTeamByCaptainIdContestId() {
        Team team = teamDao.queryTeamByCaptainIdContestId(3, 2);
        assertEquals("Team1", team.getName());
    }

    @Test
        //已写
    void queryTeamByContestIdUserId() {
        List<Team> queryTeamByContestIdUserId = teamDao.queryTeamByContestIdUserId(4, 2);
        assertEquals("Team1", queryTeamByContestIdUserId.get(0).getName());
    }

    @Test
        //已写
    void isExistTeamNameInContest() {
        boolean isExistTeamNameInContest = teamDao.isExistTeamNameInContest("Team1", 2);
        assertEquals(true, isExistTeamNameInContest);
    }

    @Test
        //已写
    void isTeamCaptainOfContest() {
        boolean isTeamCaptainOfContest = teamDao.isTeamCaptainOfContest(3, 2);
        assertEquals(true, isTeamCaptainOfContest);
    }

    @Test
        //已写
    void createTeam() {
        boolean createTeam = teamDao.createTeam("Team19", 27, 10);
        assertEquals(true, createTeam);
    }

    @Test
        //已写
    void queryCaptainIdByTeamId() {
        int queryCaptainIdByTeamId = teamDao.queryCaptainIdByTeamId(1);
        assertEquals(3, queryCaptainIdByTeamId);
    }

    @Test
        //已写
    void queryContestIdByTeamId() {
        int queryContestIdByTeamId = teamDao.queryContestIdByTeamId(1);
        assertEquals(2, queryContestIdByTeamId);
    }

    @Test
        //已写
    void booleanInviteeIdentityById() {
        boolean booleanInviteeIdentityById = teamDao.booleanInviteeIdentityById(4, 25);
        assertEquals(false, booleanInviteeIdentityById);
    }

    @Test
        //已写
    void RenameTeam() {
        boolean RenameTeam = teamDao.RenameTeam(1, "Team1");
        assertEquals(true, RenameTeam);
    }

    @Test
        //已写
    void DisbandsTheTeam() {
        boolean DisbandsTheTeam = teamDao.DisbandsTheTeam(1);
        assertEquals(true, DisbandsTheTeam);
    }

    @Test
        //已写
    void RemoveTeamMember() {
        boolean RemoveTeamMember = teamDao.RemoveTeamMember(8, 2);
        assertEquals(true, RemoveTeamMember);
    }

    @Test
        //已写
    void queryTeamsByUserId() {
        List<Team> queryTeamsByUserId = teamDao.queryTeamsByUserId(4);
        assertEquals(true, queryTeamsByUserId.isEmpty());
    }

    @Test
        //已写
    void queryTeamOfAwardByUserId() {
        List<Team> queryTeamOfAwardByUserId = teamDao.queryTeamOfAwardByUserId(14);
        assertEquals(false, queryTeamOfAwardByUserId.isEmpty());
    }

    @Test
    void queryTeamsOfHaveWorkByContestId() {
        List<Team> queryTeamsOfHaveWorkByContestId = teamDao.queryTeamsOfHaveWorkByContestId(5);
        assertEquals(true, queryTeamsOfHaveWorkByContestId.isEmpty());
    }
}