package multi.database.db2.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import multi.database.model.Model;

public interface Db2Mapper {
	@Select("SELECT * FROM multi_db_model WHERE model_id = #{modelId}")
	public Model findModelByModelId(String modelId);

	@Insert("INSERT INTO multi_db_model (model_id) VALUES (#{modelId})")
	public int saveModel(Model model);
}

