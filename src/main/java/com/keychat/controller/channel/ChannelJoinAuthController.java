package com.keychat.controller.channel;

import com.google.gson.Gson;
import com.keychat.controller.util.JsonUtil;
import com.keychat.dao.base.ChannelsDao;
import com.keychat.dao.base.ChannelsJoinDao;
import com.keychat.dao.base.ChannelsJoinJoinChannelsAnonym;
import com.keychat.dao.base.UsersJoinChannelsJoin;
import com.keychat.dto.base.*;
import com.keychat.dto.util.ResponseModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/channel/auth")
public class ChannelJoinAuthController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		ResponseModel res = null;

		HttpSession session = request.getSession();
		UsersModel loginUser = (UsersModel)session.getAttribute("loginUser");
//		        UsersModel loginUser = new UsersModel(
//                "ggg@naver.com",
//                "1234",
//                "hello",
//                "학생",
//                "010-111-1111"
//        );
		ChannelJoinAuthModel channelJoinAuthModel = JsonUtil.getModelFromJsonRequest(request, ChannelJoinAuthModel.class);
		boolean isAuth = ChannelsDao.isChannelAuthUser(channelJoinAuthModel);

		//참여하지 않은 방이였다면 참여 내역에 추가
		boolean isExistChannelUser = ChannelsJoinDao.isExistChannelUser(channelJoinAuthModel, loginUser);
		boolean isJoinChannel = false;
		if(!isExistChannelUser){
			isJoinChannel = ChannelsJoinDao.joinChannelUser(channelJoinAuthModel, loginUser);
		}
		System.out.println(isAuth + " " + isExistChannelUser + " " + isJoinChannel);
		if(loginUser != null && isAuth && (isExistChannelUser || isJoinChannel)) {
			res = new ResponseModel(200, "success", loginUser);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(new Gson().toJson(res));
		}
		else {
			response.sendError(500, new ResponseModel(500, "fail", "Cannot get user info").toString());
		}
    }	
}
