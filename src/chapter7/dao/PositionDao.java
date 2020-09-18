package chapter7.dao;

import static chapter7.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chapter7.beans.Position;
import chapter7.exception.SQLRuntimeException;

public class PositionDao {
	public List<Position> getPosition(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("positions.id as position_id, ");
			sql.append("positions.position_name as position_name ");
			sql.append("FROM positions ");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Position> ret = toPositionList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Position> toPositionList(ResultSet rs)
			throws SQLException {

		List<Position> ret = new ArrayList<Position>();
		try {
			while (rs.next()) {
				int id = rs.getInt("position_id");
				String name = rs.getString("position_name");

				Position message = new Position();
				message.setPositionId(id);
				message.setPositionName(name);
				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
