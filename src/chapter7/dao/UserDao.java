package chapter7.dao;

import static chapter7.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import chapter7.beans.User;
import chapter7.exception.NoRowsUpdatedRuntimeException;
import chapter7.exception.SQLRuntimeException;

public class UserDao {

	public List<User> getUser(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<User> ret = toUserList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs)
			throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				String branch = rs.getString("branch_name");
				int positionId = rs.getInt("position_id");
				String position = rs.getString("position_name");
				int stop = rs.getInt("status");
				Timestamp createdDate = rs.getTimestamp("created_date");
				Timestamp updatedDate = rs.getTimestamp("updated_date");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setBranch(branch);
				user.setPositionId(positionId);
				user.setPosition(position);
				user.setStop(stop);
				user.setCreatedDate(createdDate);
				user.setUpdatedDate(updatedDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	//登録（インサート）
	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append(" login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", status");
			sql.append(", created_date");
			sql.append(", updated_date");
			sql.append(") VALUES (");
			sql.append(" ?"); // login_id
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // position_id
			sql.append(", ?"); // status
			sql.append(", CURRENT_TIMESTAMP"); // created_date
			sql.append(", CURRENT_TIMESTAMP"); // updated_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranch());
			ps.setString(5, user.getPosition());
			ps.setInt(6, user.getStop());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//ホーム画面にユーザーを一覧表示するための表結合（INNER　JOIN)
	public List<User> usersSelect(Connection connection) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users ");
			sql.append("INNER JOIN ");
			sql.append("branches ON ");
			sql.append("users.branch_id = branches.id ");
			sql.append("INNER JOIN ");
			sql.append("positions ON ");
			sql.append("users.position_id = positions.id");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUsersList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUsersList(ResultSet rs)
			throws SQLException {
		//ホーム画面が呼び出されるときにDBの値を取得
		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String name = rs.getString("name");
				String branchName = rs.getString("branch_name");
				String positionName = rs.getString("position_name");
				int stop = rs.getInt("status");

				User message = new User();
				message.setId(id);
				message.setLoginId(loginId);
				message.setName(name);
				message.setBranch(branchName);
				message.setPosition(positionName);
				message.setStop(stop);
				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	//編集画面にユーザー情報を表示するための表結合
	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users ");
			sql.append("INNER JOIN ");
			sql.append("branches ON ");
			sql.append("users.branch_id = branches.id ");
			sql.append("INNER JOIN ");
			sql.append("positions ON ");
			sql.append("users.position_id = positions.id ");
			sql.append("WHERE users.id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//編集（アップデート）
	public void update(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			sql.append(", password = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", position_id = ?");
			sql.append(", updated_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getPositionId());
			ps.setInt(6, user.getId());

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	//ユーザー停止
	public void stop(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  status = ?");
			sql.append(", updated_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, user.getStop());
			ps.setInt(2, user.getId());

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	//重複チェック
	public int check(Connection connection, String loginidformat) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT COUNT(*) as cnt FROM users");
			sql.append(" WHERE");
			sql.append(" login_id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, loginidformat);

			ResultSet count = ps.executeQuery();

			int checkLoginId = checkList(count);
			//↓にＳＱＬ文の結果が入っている
			return checkLoginId;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//ＤＢから取得
	private int checkList(ResultSet rs)
			throws SQLException {
		//ホーム画面が呼び出されるときにDBの値を取得
		int count = 0;
		try {
			while (rs.next()) {
				count = rs.getInt("cnt");
			}
			return count;
		} finally {
			close(rs);
		}
	}
}