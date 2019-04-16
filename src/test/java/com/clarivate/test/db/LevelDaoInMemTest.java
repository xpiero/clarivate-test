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
public class LevelDaoInMemTest {

    private static Handle handle;

    @InjectMocks
    @Spy
    private LevelDao target;

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

        handle.execute("INSERT INTO level (name) VALUES ('1');");
        
    }


    @After
    public void after() {
        //clean data
        handle.execute("DROP ALL OBJECTS;");
    }

    @Test
    public void test_level_find_by_name() {

        //when
        LevelBean existingLevel = target.getLevelByName("1");
        LevelBean nonExistingLevel = target.getLevelByName("15");

        //then
        Assert.assertEquals(existingLevel.getName(), "1");
        Assert.assertEquals(nonExistingLevel, null);
    }
    
    @Test
    public void test_level_insert() {

        //when
        LevelBean beforeInsert = target.getLevelByName("2");
        LevelBean levelBean = new LevelBean();
        levelBean.setName("2");
        
        target.insert(levelBean);
        LevelBean afterInsert = target.getLevelByName("2");

        //then
        Assert.assertEquals(beforeInsert, null);
        Assert.assertEquals(afterInsert.getName(), "2");
    }
}