package com.clarivate.test.api;

import com.clarivate.test.db.PlayerBean;
import com.clarivate.test.db.ScoreBean;
import com.clarivate.test.service.PlayerService;
import com.clarivate.test.service.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {

    private static int i;

    @Autowired
    PlayerService playerService;
    
    @Autowired
    ScoreService scoreService;
    
    @GetMapping("/level/{level}/score")
    public List<ScoreBean> scoresList(@PathVariable("level") String level, @RequestParam(value = "filter", required = false) String filter) {
    	if(filter != null && filter.equals("highestscore")) {
    		return scoreService.highScoresByLevel(level);
    	} 
    	return scoreService.listScoresByLevel(level);
    }
    
    @PutMapping("/level/{level}/score/{score}")
    public ResponseEntity insertScore(@AuthenticationPrincipal PlayerBean player, @PathVariable("level") String level, @PathVariable("score") long score) {
    	scoreService.insertScore(player.getId(), level, score);
    	return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
