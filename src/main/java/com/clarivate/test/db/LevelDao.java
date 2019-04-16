package com.clarivate.test.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


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


@Repository
public class LevelDao extends BaseDao {

	public LevelBean getLevelByName(String name){
        Handle handle = getHandle();
        LevelSQL levelSQL = handle.attach(LevelSQL.class);
        return levelSQL.getLevelByName(name);
    }

    public Integer insert(LevelBean item){
        Handle handle = getHandle();
        LevelSQL levelSQL = handle.attach(LevelSQL.class);
        return levelSQL.insert(item);
    }

    @RegisterMapper(LevelMapper.class)
    interface LevelSQL {
        @SqlQuery("select * from level where name = :name")
        LevelBean getLevelByName(@Bind("name") String level);

        @SqlUpdate("insert into level (name)" +
                    " values(:name) ")
        @GetGeneratedKeys
        Integer insert(@BindBean LevelBean level);
    }

    public static class LevelMapper implements ResultSetMapper<LevelBean> {
        @Override
        public LevelBean map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        	LevelBean bean = new LevelBean();
            bean.setId((Integer) r.getObject("id"));
            bean.setName(r.getString("name"));
            return bean;
        }
    }

}
