package com.akshay.ipl;

import static com.akshay.ipl.Constants.*;

public class Deliveries {
    private int matchId;
    private int extraRuns;
    private int totalRuns;
    private int batsmanRun;
    private String bowlerName;
    private String bowlingTeam;
    private String batsmanName;

    public Deliveries(String[] headRow){
        this.matchId = Integer.parseInt(headRow[MATCH_ID]);
        this.extraRuns = Integer.parseInt(headRow[EXTRA_RUNS]);
        this.totalRuns = Integer.parseInt(headRow[TOTAL_RUNS]);
        this.batsmanRun = Integer.parseInt(headRow[BATSMAN_RUN]);
        this.batsmanName = headRow[BATSMAN_NAME];
        this.bowlerName = headRow[BOWLER_NAME];
        this.bowlingTeam = headRow[BOWLING_TEAM];
    }
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(int extraRuns) {
        this.extraRuns = extraRuns;
    }
    public int getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(int totalRuns) {
        this.totalRuns = totalRuns;
    }

    public int getBatsmanRun() {
        return batsmanRun;
    }

    public void setBatsmanRun(int batsmanRun) {
        this.batsmanRun = batsmanRun;
    }

    public String getBowlerName() {
        return bowlerName;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public String getBatsmanName() {
        return batsmanName;
    }

    public void setBatsmanName(String batsmanName) {
        this.batsmanName = batsmanName;
    }
}
