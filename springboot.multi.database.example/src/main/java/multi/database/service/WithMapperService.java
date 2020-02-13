package multi.database.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import multi.database.db1.dao.Db1Mapper;
import multi.database.db2.dao.Db2Mapper;
import multi.database.model.Model;

@Service("withMapperService")
public class WithMapperService {
	private static final Logger logger = LoggerFactory.getLogger(WithMapperService.class);

	@Autowired
	Db1Mapper db1Mapper;
	
	@Autowired
	Db2Mapper db2Mapper;

	public int moveDb1toDb2(String modelId) {
		Model model = db1Mapper.findModelByModelId(modelId);
		return db2Mapper.saveModel(model);
	}
}
