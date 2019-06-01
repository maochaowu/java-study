package com.mcw.cora.mybatis.entity;

/**
 * @Author yibi
 * Date 2019/5/28 21:34
 * Version 1.0
 **/
public class StSc {

    private Integer id;

    private Integer stId;

    private Integer scId;

    private Integer score;

    public StSc(Integer stId, Integer scId, Integer score) {
        this.stId = stId;
        this.scId = scId;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStId() {
        return stId;
    }

    public void setStId(Integer stId) {
        this.stId = stId;
    }

    public Integer getScId() {
        return scId;
    }

    public void setScId(Integer scId) {
        this.scId = scId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
