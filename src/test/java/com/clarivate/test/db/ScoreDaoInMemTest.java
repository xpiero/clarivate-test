package com.clarivate.test.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import com.clarivate.test.db.ScoreDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ScoreDaoInMemTest {

    private static Handle handle;

    @InjectMocks
    @Spy
    private ScoreDao target;

    @Before
    public void before() throws ClassNotFoundException, IOException {
        //setup in memory database
        Class.forName("org.h2.Driver");
        DBI dbi = new DBI("jdbc:h2:mem:test");
        handle = dbi.open();
        handle.execute("set mode MySQL;");

        //run create table script
        String sql = new String(Files.readAllBytes(Paths.get("./dbscript.sql")));
        handle.createScript(sql).executeAsSeparateStatements();

        //mock (as spy) to return handle ( it is like a DB connection, but for the JDBI framework)
        doReturn(handle).when(target).getHandle();

        //insert some data
        handle.execute("INSERT INTO player (username, password) VALUES ('Luke','$2a$10$B4UdYw6FkhZeSsyjtKsM1ueZbBvHiKU3DTDpO/hNBCGn1DU8roo.G');");
        handle.execute("INSERT INTO player (username, password) VALUES ('Liam','$2a$10$2mfFqlsK1RxOMKYD0VD5a.XlOmoS07n/uc9Yp5L695i8UY1KsYEhG');");
        handle.execute("INSERT INTO player (username, password) VALUES ('Lesley','$2a$10$B4UdYw6FkhZeSsyjtKsM1ueZbBvHiKU3DTDpO/hNBCGn1DU8roo.G');");

        handle.execute("INSERT INTO level (name) VALUES ('1');");
        
        handle.execute("INSERT INTO score (player, level, score) VALUES (1,1,25);");
        handle.execute("INSERT INTO score (player, level, score) VALUES (1,1,5);");
        handle.execute("INSERT INTO score (player, level, score) VALUES (1,1,50);");
        handle.execute("INSERT INTO score (player, level, score) VALUES (2,1,15);");
        handle.execute("INSERT INTO score (player, level, score) VALUES (3,1,125);");
    }



    @After
    public void after() {
        //clean data
        handle.execute("DROP ALL OBJECTS;");
    }

    @Test
    public void test_score_list() {
        //given
    	Long level = new Long(1);

        //when
        List<ScoreBean> scoreList = target.listScoresByLevel(level);

        //then
        Assert.assertEquals(scoreList.size(), 5);
    }
    
    @Test
    public void test_highscore_list() {
        //given
    	Long level = new Long(1);

        //when
        List<ScoreBean> scoreList = target.highScoresByLevel(level);

        //then
        Assert.assertEquals(scoreList.size(), 3);
    }


    @Test
    public void test_score_insert() {
    	Long level = new Long(1);
    	Long player = new Long(3);
    	Long score = new Long(65);

        //when
        List<ScoreBean> beforeList = target.listScoresByLevel(level);
        ScoreBean scoreBean = new ScoreBean();
        scoreBean.setPlayer(player);
        scoreBean.setLevel(level);
        scoreBean.setScore(score);
        
        target.insert(scoreBean);
        List<ScoreBean> afterList = target.listScoresByLevel(level);

        //then
        Assert.assertEquals(beforeList.size(), 5);
        Assert.assertEquals(afterList.size(), 6);
    }
}