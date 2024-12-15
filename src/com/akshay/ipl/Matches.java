package com.akshay.ipl;

import static com.akshay.ipl.Constants.*;

public class Matches {
    private int matchId;
    private int matchYear;
    private String winner;

    public Matches(String[] headRow){
        this.matchId = Integer.parseInt(headRow[MATCH_ID]);
        this.matchYear = Integer.parseInt(headRow[MATCH_YEAR]);
        this.winner = headRow[MATCH_WINNER];
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = Integer.parseInt(matchId);
    }

    public int getMatchYear() {
        return matchYear;
    }

    public void setMatchYear(String matchYear) {
        this.matchYear = Integer.parseInt(matchYear);
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
