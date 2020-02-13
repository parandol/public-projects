package multi.database.db2.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import multi.database.model.Model;

@Repository("db2DAO")
public class Db2DAO {
	@Autowired
	@Qualifier("db2SqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	public Model findModelByModelId(String modelId) {
		return sqlSession.selectOne("model.findModelByModelId", modelId);
	}

	public int saveModel(Model model) {
		return sqlSession.insert("model.saveModel", model);
	}
}
