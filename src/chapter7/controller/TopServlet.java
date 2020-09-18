package chapter7.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter7.beans.Branch;
import chapter7.beans.Position;
import chapter7.beans.User;
import chapter7.service.BranchService;
import chapter7.service.PositionService;
import chapter7.service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<User> users = new UserService().getUsers();
		request.setAttribute("users", users);
		request.getRequestDispatcher("Top.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> users = new ArrayList<String>();
		List<Branch> branch = new BranchService().getBranch();
		List<Position> position = new PositionService().getPosition();

		User user = new User();
		user.setName(request.getParameter("name"));
		user.setBranch(request.getParameter("branches"));
		user.setPosition(request.getParameter("positions"));

		//左：jspと繋がっている（どこに渡したいか）
		//右：何を渡すか　値
		request.setAttribute("users", users);
		request.setAttribute("branches", branch);
		request.setAttribute("positions", position);
		request.getRequestDispatcher("Top.jsp").forward(request, response);

		//青はjspのnameに合わせる、jspのVALUE値をUser.javaにセット（リクエストにjspからのデータが入っている）
		user.setName(request.getParameter("name"));
		response.sendRedirect("./");
	}

}
