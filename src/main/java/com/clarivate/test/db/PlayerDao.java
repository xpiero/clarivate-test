package com.clarivate.test.db;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PlayerDao extends BaseDao {

    public List<PlayerBean> list(){
        Handle handle = getHandle();
        PlayerSQL playerSQL = handle.attach(PlayerSQL.class);
        return playerSQL.list();
    }

    public Integer insert(PlayerBean item){
        Handle handle = getHandle();
        PlayerSQL playerSQL = handle.attach(PlayerSQL.class);
        return playerSQL.insert(item);
    }
    
    public PlayerBean findByUsername(String username) {
    	Handle handle = getHandle();
    	PlayerSQL playerSQL = handle.attach(PlayerSQL.class);
        return playerSQL.findByUsername(username);
    }

    @RegisterMapper(PlayerMapper.class)
    interface PlayerSQL {
        @SqlQuery("select * from Player")
        List<PlayerBean> list();
        
        @SqlQuery("select * from Player where username = :username and password = :password")
        PlayerBean login(@Bind("username") String username, @Bind("password") String password);
        
        @SqlQuery("select * from Player where username = :username")
        PlayerBean findByUsername(@Bind("username") String username);

        @SqlUpdate("insert into Player (username,password)" +
                    " values(:username,:password) ")
        @GetGeneratedKeys
        Integer insert(@BindBean PlayerBean test);
    }

    public static class PlayerMapper implements ResultSetMapper<PlayerBean> {
        @Override
        public PlayerBean map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
            PlayerBean bean = new PlayerBean();
            bean.setId((Integer) r.getObject("id"));
            bean.setUsername(r.getString("username"));
            bean.setPassword(r.getString("password"));
            return bean;
        }
    }

}
