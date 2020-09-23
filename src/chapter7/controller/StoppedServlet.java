package chapter7.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter7.beans.User;
import chapter7.service.UserService;

@WebServlet(urlPatterns = { "/stopped" })
public class StoppedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	//リソースを呼ぶ
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//リクエストの引数（id）を持ってきて変数idに保持
		//そのIDの人の編集を行う
		int id = Integer.parseInt(request.getParameter("id"));
		//セッションよりログインユーザーの情報を取得
		User editUser = new UserService().getUser(id);
		//ログインユーザー情報のidを元にDBからユーザー情報取得
		request.setAttribute("editUser", editUser);
		request.getRequestDispatcher("Top.jsp").forward(request, response);
	}

	@Override
	//データに変更を加える
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		User stopUsers = getStop(request);
		//登録できる状態だったら（正しく入力されてたら）通る道
		new UserService().stop(stopUsers);
		response.sendRedirect("./");
	}

	private User getStop(HttpServletRequest request)
			throws IOException, ServletException {

		User editUser = new User();
		editUser.setId(Integer.parseInt(request.getParameter("id")));
		editUser.setStop(Integer.parseInt(request.getParameter("stopped")));
		return editUser;
	}
}
