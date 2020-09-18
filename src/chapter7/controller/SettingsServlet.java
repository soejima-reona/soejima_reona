package chapter7.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter7.beans.Branch;
import chapter7.beans.Position;
import chapter7.beans.User;
import chapter7.exception.NoRowsUpdatedRuntimeException;
import chapter7.service.BranchService;
import chapter7.service.PositionService;
import chapter7.service.UserService;

@WebServlet(urlPatterns = { "/setting" })
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	//リソースを呼ぶ
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//青ユーザーのidを持ってきてピンクに保持
		//そのIDの人の編集を行う
		int id = Integer.parseInt(request.getParameter("id"));
		//セッションよりログインユーザーの情報を取得
		User editUser = new UserService().getUser(id);
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		//idの存在確認
		if (editUser == null) {
			messages.add("idが存在しません");
			session.setAttribute("errorMessages", messages);
		}
		//ログインユーザー情報のidを元にDBからユーザー情報取得
		request.setAttribute("editUser", editUser);

		List<Branch> branches = new BranchService().getBranch();
		request.setAttribute("branches", branches);

		List<Position> positions = new PositionService().getPosition();
		request.setAttribute("positions", positions);

		request.getRequestDispatcher("setting.jsp").forward(request, response);
	}

	@Override
	//データに変更を加える
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		List<Branch> branches = new BranchService().getBranch();
		List<Position> positions = new PositionService().getPosition();
		HttpSession session = request.getSession();
		String loginId = request.getParameter("login_id");
		String name = request.getParameter("name");
		String branch = request.getParameter("branches");
		String position = request.getParameter("positions");
		User editUser = getEditUser(request);

		//バリデーションに引っかからなかったらUser.javaに送る
		if (isValid(request, messages) == true) {
			//登録できる状態だったら通る道
			try {
				new UserService().update(editUser);
			} catch (NoRowsUpdatedRuntimeException e) {
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				request.setAttribute("editUser", editUser);
				request.getRequestDispatcher("settings.jsp").forward(request, response);
				return;
			}
			session.setAttribute("loginUser", editUser);

			response.sendRedirect("./");
		} else {
			//バリデーションに引っかかったら入力内容を表示したままにする
			session.setAttribute("errorMessages", messages);
			request.setAttribute("editUser", editUser);
			request.setAttribute("login_id", loginId);
			request.setAttribute("name", name);
			request.setAttribute("branches", branches);
			request.setAttribute("positions", positions);
			//			request.setAttribute("branch", branch);
			//			request.setAttribute("position", position);

			request.getRequestDispatcher("setting.jsp").forward(request, response);
		}
	}

	//jspの入力値をUser.javaに送る（セット）処理
	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		User editUser = new User();
		editUser.setId(Integer.parseInt(request.getParameter("id")));
		editUser.setLoginId(request.getParameter("login_id"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		editUser.setPositionId(Integer.parseInt(request.getParameter("positionId")));

		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		//バリデーションメッセージ
		String loginidformat = request.getParameter("login_id");
		String passwordformat = request.getParameter("password");
		String checkformat = request.getParameter("check");
		String nameformat = request.getParameter("name");
		int checkLoginId = new UserService().check(loginidformat);
		String userLoginid = request.getParameter("userLoginid");

		if (StringUtils.isEmpty(loginidformat) == true) {
			messages.add("ログインIDを入力してください");
		} else if (!loginidformat.matches("^[a-zA-Z0-9]{6,20}$")) {
			messages.add("ログインIDは6～20文字の半角英数字で入力してください");
			//入力されたログインIDがSQL上に1以上＆＆そのIDが既に入力されているIDと一致しなかった場合、ログインID被りの為エラー表示
		} else if (checkLoginId != 0 && !loginidformat.equals(userLoginid)) {
			messages.add("すでに登録されたログインIDです");
		}
		if (!passwordformat.matches("^[a-zA-Z0-9!-/:-@¥[-`{-~]]{6,20}$") && passwordformat.length() != 0) {
			messages.add("パスワードは6～20文字の記号を含む半角英数字で入力してください");
		} else if (!passwordformat.equals(checkformat)) {
			messages.add("パスワードが一致しません");
		}
		if (StringUtils.isEmpty(nameformat) == true) {
			messages.add("名称を入力してください");
		} else if (nameformat.length() > 10) {
			messages.add("名称は10文字以下で入力してください");
		}

		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		//バリデーションに引っかからなかったら登録
		//引っかかったら（sizeが1以上だったら）通らない
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}