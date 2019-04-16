package com.clarivate.test.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clarivate.test.db.LevelBean;
import com.clarivate.test.db.ScoreBean;
import com.clarivate.test.db.ScoreDao;

@Service
public class ScoreService {

	@Autowired
    private ScoreDao scoreDao;
	
	@Autowired
	private LevelService levelService;

    @Transactional
    public List<ScoreBean> listScoresByLevel(String level){
    	LevelBean levelbean = levelService.getLevelByName(level);
    	if(levelbean != null)
    		return scoreDao.listScoresByLevel(levelbean.getId().longValue());
    	return null;
    }
    
    @Transactional
    public List<ScoreBean> highScoresByLevel(String level){
    	LevelBean levelbean = levelService.getLevelByName(level);
    	if(levelbean != null)
    		return scoreDao.highScoresByLevel(levelbean.getId().longValue());
    	return null;
    }
    
    @Transactional
    public Integer insertScore(Integer player, String level, Long score){
    	Integer levelId = null;
    	LevelBean levelbean = levelService.getLevelByName(level);
    	
    	levelId = levelbean == null ? levelService.createLevel(level):levelbean.getId();
    	
    	ScoreBean bean = new ScoreBean();
    	bean.setPlayer(player.longValue());
    	bean.setLevel(levelId.longValue());
    	bean.setScore(score);
    	
        return scoreDao.insert(bean);
    }
}
