package multi.database.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import multi.database.db1.dao.Db1DAO;
import multi.database.db2.dao.Db2DAO;
import multi.database.model.Model;

@Service("withDaoService")
public class WithDaoService {
	private static final Logger logger = LoggerFactory.getLogger(WithDaoService.class);

	@Autowired
	@Qualifier("db1DAO")
	private Db1DAO db1DAO;
	
	@Autowired
	@Qualifier("db2DAO")
	private Db2DAO db2DAO;

	public int moveDb1toDb2(String modelId) {
		Model model = db1DAO.findModelByModelId(modelId);
		return db2DAO.saveModel(model);
	}
}
