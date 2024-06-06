package test;

import com.jk204.dao.TeamMemberDao;
import com.jk204.domain.TeamMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamMemberDaoTest {
    //重新推送
    private TeamMemberDao teamMemberDao;

    @BeforeEach
    public void init() {
        teamMemberDao = new TeamMemberDao();
    }

    @Test
    void queryCountTeamMemberByTeamId() {
        int Result = teamMemberDao.queryCountTeamMemberByTeamId(1);
        assertEquals(3, Result);
    }

    @Test
    void insertNewMemberIntoTeam() {
        boolean Result = teamMemberDao.InsertNewMemberIntoTeam(1, 24);
        assertEquals(true, Result);
    }

    @Test
        //已写
    void queryTeamMemberByTeamId() {
        List<TeamMember> Result = teamMemberDao.queryTeamMemberByTeamId(1);
        assertEquals(4, Result.size());
    }
}