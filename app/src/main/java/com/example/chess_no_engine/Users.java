package com.example.chess_no_engine;

public class Users {
    String name, password;
    int loseScore, winScore, drawScore;

    public Users(){

    }

    public Users(String name, String password, int loseScore, int winScore, int drawScore){
        this.name = name;
        this.password = password;
        this.loseScore = loseScore;
        this.winScore = winScore;
        this.drawScore = drawScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLoseScore() {
        return loseScore;
    }

    public void setLoseScore(int loseScore) {
        this.loseScore = loseScore;
    }

    public int getWinScore() {
        return winScore;
    }

    public void setWinScore(int winScore) {
        this.winScore = winScore;
    }

    public int getDrawScore() {
        return drawScore;
    }

    public void setDrawScore(int drawScore) {
        this.drawScore = drawScore;
    }
}
