package com.clarivate.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clarivate.test.db.LevelBean;
import com.clarivate.test.db.LevelDao;

@Service
public class LevelService {

	@Autowired
    private LevelDao levelDao;

    @Transactional
    public LevelBean getLevelByName(String name){
        return levelDao.getLevelByName(name);
    }
    
    @Transactional
    public Integer createLevel(String name) {
    	LevelBean bean = new LevelBean();
    	bean.setName(name);
    	return levelDao.insert(bean);
    }
}
