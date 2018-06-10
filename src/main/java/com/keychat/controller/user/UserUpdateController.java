package com.keychat.controller.user;

        import com.google.gson.Gson;
        import com.keychat.controller.util.JsonUtil;
        import com.keychat.dao.base.UsersDao;
        import com.keychat.dto.base.UsersModel;
        import com.keychat.dto.util.ResponseModel;

        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;

@WebServlet(urlPatterns = "/user/update")
public class UserUpdateController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ResponseModel res;
        UsersModel usersModel = JsonUtil.getModelFromJsonRequest(request, UsersModel.class);
        boolean isExist = !UsersDao.isExistNickname(usersModel) &&
                UsersDao.isExactPassword(usersModel);
        boolean isUpdated = UsersDao.updateUser(usersModel);

        if(isExist && isUpdated)
            res = new ResponseModel(200, "success", usersModel);
        else
            res = new ResponseModel(500, "fail", "Cannot create user");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(res));
    }
}
