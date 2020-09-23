package chapter7.dao;

import static chapter7.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chapter7.beans.Branch;
import chapter7.exception.SQLRuntimeException;

public class BranchDao {
	public List<Branch> getBranch(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("branches.id as branch_id, ");
			sql.append("branches.name as branch_name ");
			sql.append("FROM branches ");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Branch> ret = toBranchList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Branch> toBranchList(ResultSet rs)
			throws SQLException {

		List<Branch> ret = new ArrayList<Branch>();
		try {
			while (rs.next()) {
				int id = rs.getInt("branch_id");
				String name = rs.getString("branch_name");

				Branch message = new Branch();
				message.setBranchId(id);
				message.setBranchName(name);
				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
