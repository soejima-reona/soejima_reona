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
import chapter7.service.BranchService;
import chapter7.service.PositionService;
import chapter7.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branch> branches = new BranchService().getBranch();
		request.setAttribute("branches", branches);

		List<Position> positions = new PositionService().getPosition();
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
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

		//バリデーションに引っかからなかったらUser.javaに送る
		if (isValid(request, messages) == true) {
			User user = new User();
			//青はjspのnameに合わせる、jspのVALUE値をUser.javaにセット（リクエストにjspからのデータが入っている）
			user.setLoginId(request.getParameter("login_id"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("name"));
			user.setBranch(request.getParameter("branches"));
			user.setPosition(request.getParameter("positions"));

			new UserService().register(user);
			response.sendRedirect("./");
		} else {
			//バリデーションに引っかかったら入力内容を保持
			session.setAttribute("errorMessages", messages);
			request.setAttribute("login_id", loginId);
			request.setAttribute("name", name);
			request.setAttribute("branches", branches);
			request.setAttribute("positions", positions);
			request.setAttribute("branch", branch);
			request.setAttribute("position", position);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		//バリデーションメッセージ
		String loginidformat = request.getParameter("login_id");
		String passwordformat = request.getParameter("password");
		String checkformat = request.getParameter("check");
		String nameformat = request.getParameter("name");
		int checkLoginId = new UserService().check(loginidformat);

		if (StringUtils.isEmpty(loginidformat) == true) {
			messages.add("ログインIDを入力してください");
		} else if (!loginidformat.matches("^[a-zA-Z0-9]{6,20}$")) {
			messages.add("ログインIDは6～20文字の半角英数字で入力してください");
		}
		if (StringUtils.isEmpty(passwordformat) == true) {
			messages.add("パスワードを入力してください");
		} else if (!passwordformat.matches("^[a-zA-Z0-9!-/:-@¥[-`{-~]]{6,20}$")) {
			messages.add("パスワードは6～20文字の記号を含む半角英数字で入力してください");
		}
		if (StringUtils.isEmpty(checkformat) == true) {
			messages.add("確認用パスワードを入力してください");
		} else if (!passwordformat.equals(checkformat)) {
			messages.add("パスワードが一致しません");
		}
		if (StringUtils.isEmpty(nameformat) == true) {
			messages.add("名称を入力してください");
		} else if (nameformat.length() > 10) {
			messages.add("名称は10文字以下で入力してください");
		}
		if (checkLoginId != 0) {
			messages.add("すでに登録されたログインIDです");
		}
		// TODO アカウントが既に利用されていないか、メールアドレスが既に登録されていないかなどの確認も必要
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}