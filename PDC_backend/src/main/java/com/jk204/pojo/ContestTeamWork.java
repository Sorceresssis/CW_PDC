package com.jk204.pojo;

public class ContestTeamWork {
    private ContestTitle contest;
    private TeamFullInfo team;
    private WorkFullInfo work;

    public ContestTeamWork() {
    }

    public ContestTeamWork(ContestTitle contest, TeamFullInfo team, WorkFullInfo work) {
        this.contest = contest;
        this.team = team;
        this.work = work;
    }

    public ContestTitle getContest() {
        return contest;
    }

    public void setContest(ContestTitle contest) {
        this.contest = contest;
    }

    public TeamFullInfo getTeam() {
        return team;
    }

    public void setTeam(TeamFullInfo team) {
        this.team = team;
    }

    public WorkFullInfo getWork() {
        return work;
    }

    public void setWork(WorkFullInfo work) {
        this.work = work;
    }
}
