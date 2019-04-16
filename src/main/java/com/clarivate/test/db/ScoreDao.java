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

import com.clarivate.test.util.DbUtil;

@Repository
public class ScoreDao extends BaseDao {

    public Integer insert(ScoreBean item){
        Handle handle = getHandle();
        ScoreSQL scoreSQL = handle.attach(ScoreSQL.class);
        return scoreSQL.insert(item);
    }
    
    public List<ScoreBean> listScoresByLevel(Long level){
    	Handle handle = getHandle();
        ScoreSQL scoreSQL = handle.attach(ScoreSQL.class);
        return scoreSQL.list(level);
    }
    
    public List<ScoreBean> highScoresByLevel(Long level){
    	Handle handle = getHandle();
        ScoreSQL scoreSQL = handle.attach(ScoreSQL.class);
        return scoreSQL.highScoresByLevel(level);
    }

    @RegisterMapper(ScoreMapper.class)
    interface ScoreSQL {
        @SqlQuery("select score, username from score s " + 
        		"left join player p on p.id = s.player " + 
        		"where level = :level order by score desc")
        List<ScoreBean> list(@Bind("level") Long level);
        
        @SqlQuery("select p.username as username, s.score as score from (" + 
        		"SELECT player," + 
        		"MAX(score) AS score " + 
        		"FROM score " + 
        		"WHERE level = :level " + 
        		"GROUP BY player ) s " + 
        		"left join player p on s.player = p.id "
        		+ "order by s.score desc")
        List<ScoreBean> highScoresByLevel(@Bind("level") Long level);

        @SqlUpdate("insert into score (player,level,score)" +
                    " values(:player,:level,:score) ")
        @GetGeneratedKeys
        Integer insert(@BindBean ScoreBean test);
    }

    public static class ScoreMapper implements ResultSetMapper<ScoreBean> {
        @Override
        public ScoreBean map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        	ScoreBean bean = new ScoreBean();
        	if(DbUtil.hasColumn(r, "player"))
        		bean.setPlayer((Long) r.getObject("player"));
        	if(DbUtil.hasColumn(r, "level"))
        		bean.setLevel((Long) r.getObject("level"));
        	if(DbUtil.hasColumn(r, "score"))
        		bean.setScore((Long) r.getObject("score"));
            if(DbUtil.hasColumn(r, "username"))
            	bean.setUsername(r.getString("username"));
            return bean;
        }
    }
}
