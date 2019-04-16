package com.clarivate.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clarivate.test.db.PlayerBean;
import com.clarivate.test.db.PlayerDao;

import java.util.Date;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerDao playerDao;
    
    @Transactional
    public List<PlayerBean> list(){
        System.out.println(playerDao.list());
        return playerDao.list();
    }

    @Transactional
    public PlayerBean login(String username, String password){
    	PlayerBean player = findByUsername(username);
    	if(new BCryptPasswordEncoder().matches(password, player.getPassword()))
    		return player;
    	return null;
    }
    
    @Transactional
    public PlayerBean findByUsername(String username){
    	return playerDao.findByUsername(username);
    }
    
    @Transactional
    public Integer createUser(String username, String password) {
    	PlayerBean user = new PlayerBean();
    	user.setUsername(username);
    	user.setPassword(new BCryptPasswordEncoder().encode(password));
    	return playerDao.insert(user);
    }

}
