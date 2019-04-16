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
public class PlayerDaoInMemTest {

    private static Handle handle;

    @InjectMocks
    @Spy
    private PlayerDao target;

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

        handle.execute("INSERT INTO player (username, password) VALUES ('Luke','$2a$10$B4UdYw6FkhZeSsyjtKsM1ueZbBvHiKU3DTDpO/hNBCGn1DU8roo.G');");
        
    }

    @After
    public void after() {
        //clean data
        handle.execute("DROP ALL OBJECTS;");
    }


    @Test
    public void test_player_find_by_username() {

        //when
        PlayerBean existingPlayer = target.findByUsername("Luke");
        PlayerBean nonExistingPlayer = target.findByUsername("Kylie");

        //then
        Assert.assertEquals(existingPlayer.getUsername(), "Luke");
        Assert.assertEquals(nonExistingPlayer, null);
    }
}