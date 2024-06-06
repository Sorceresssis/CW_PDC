package com.jk204.pojo;

import java.util.List;

public class UserTeamOfContest {
    private ContestTeamWork created;
    private List<ContestTeamWork> joined;

    public UserTeamOfContest() {
    }

    public UserTeamOfContest(ContestTeamWork created, List<ContestTeamWork> joined) {
        this.created = created;
        this.joined = joined;
    }

    public ContestTeamWork getCreated() {
        return created;
    }

    public void setCreated(ContestTeamWork created) {
        this.created = created;
    }

    public List<ContestTeamWork> getJoined() {
        return joined;
    }

    public void setJoined(List<ContestTeamWork> joined) {
        this.joined = joined;
    }
}
