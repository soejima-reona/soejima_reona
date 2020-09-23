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
