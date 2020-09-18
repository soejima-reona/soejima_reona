package chapter7.service;

import static chapter7.utils.CloseableUtil.*;
import static chapter7.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import chapter7.beans.Position;
import chapter7.dao.PositionDao;

public class PositionService {

	public List<Position> getPosition() {

		Connection connection = null;
		try {
			connection = getConnection();

			PositionDao positionDao = new PositionDao();
			List<Position> ret = positionDao.getPosition(connection);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}
