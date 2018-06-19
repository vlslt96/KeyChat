package com.keychat.controller.channelkeywordrecom;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keychat.dao.base.ChannelsKeywordRecomDao;

@WebServlet(urlPatterns = "/channelKeywordRecom/list")
public class ChannelKeywordRecomListController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");   
		String channel_name = request.getParameter("channel_name").trim();
		String nonkeyword = "분석된 키워드가 존재하지 않습니다.";
		RequestDispatcher rd = null;
		try {
			ArrayList<String> list = ChannelsKeywordRecomDao.findKeyword(channel_name);
			int size = list.size();
			if(size >= 1) {
				request.setAttribute("keyword1", list.get(0));
				if(size >= 2) {
					request.setAttribute("keyword2", list.get(1));
					if(size >= 3) {
						request.setAttribute("keyword3", list.get(2));
						if(size >= 4) {
							request.setAttribute("keyword4", list.get(3));
							if(size >= 5) {
								request.setAttribute("keyword5", list.get(4));
								if(size >= 6) {
									request.setAttribute("keyword6", list.get(5));
									if(size >= 7) {
										request.setAttribute("keyword7", list.get(6));
										if(size >= 8) {
											request.setAttribute("keyword8", list.get(7));
											if(size >= 9) {
												request.setAttribute("keyword9", list.get(8));
												if(size >= 10) {
													request.setAttribute("keyword10", list.get(9));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			try {ArrayList<String[]> categories = ChannelsKeywordRecomDao.findCategory(channel_name); // 순서 1
	    		if(categories.size() >= 3) {
			    	String[] categories1 = categories.get(0);
			    	String category1 = categories1[0];
			    	double num1 = (double)Integer.valueOf(categories1[1]);
			    	
			    	String[] categories2 = categories.get(1);
			    	String category2 = categories2[0];
			    	double num2 = (double)Integer.valueOf(categories2[1]);
			    	
			    	String[] categories3 = categories.get(2);
			    	String category3 = categories3[0];
			    	double num3 = (double)Integer.valueOf(categories3[1]);
			    	
			    	double per1 = Math.floor(num1/(num1+num2+num3)*100);
			    	double per2 = Math.floor(num2/(num1+num2+num3)*100);
			    	double per3 = Math.floor(num3/(num1+num2+num3)*100);
			    	
			    	request.setAttribute("category1", category1);
			    	request.setAttribute("category2", category2);
			    	request.setAttribute("category3", category3);
			    	
			    	request.setAttribute("per1", per1);
			    	request.setAttribute("per2", per2);
			    	request.setAttribute("per3", per3);
			    	
			    }else if(categories.size() >= 2){
			    	String[] categories1 = categories.get(0);
			    	String category1 = categories1[0];
			    	double num1 = (double)Integer.valueOf(categories1[1]);
			    	
			    	String[] categories2 = categories.get(1);
			    	String category2 = categories2[0];
			    	double num2 = (double)Integer.valueOf(categories2[1]);
			    	
			    	double num3 = (double)0;
			    	
			    	double per1 = Math.floor(num1/(num1+num2)*100);
			    	double per2 = Math.floor(num2/(num1+num2)*100);
			    	double per3 = Math.floor(num3/(num1+num2+num3)*100);
			    	
			    	request.setAttribute("category1", category1);
			    	request.setAttribute("category2", category2);
			    	
			    	request.setAttribute("per1", per1);
			    	request.setAttribute("per2", per2);
			    	request.setAttribute("per3", per3);
			    }else if(categories.size() >= 1) {
			    	String[] categories1 = categories.get(0);
			    	String category1 = categories1[0];
			    	double num1 = (double)Integer.valueOf(categories1[1]);
			    	
			    	double per1 = Math.floor(num1/(num1)*100);
			    	
			    	request.setAttribute("category1", category1);
			    	
			    	request.setAttribute("per1", per1);
			    }else {
	    			request.setAttribute("error", nonkeyword);
	    			rd = request.getRequestDispatcher("/jsp/error.jsp");
//					request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); //1 // 순서 2
	    		}
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("error", nonkeyword);
				rd = request.getRequestDispatcher("/jsp/error.jsp");
//				request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); //2
			}
			rd = request.getRequestDispatcher("/jsp/CategoriesGraph.jsp");
//			request.getRequestDispatcher("/CategoriesGraph.jsp").forward(request, response); //3 // 순서 3
		} catch (SQLException e) {
			e.printStackTrace();
			//분석된 키워드가 존재하지 않습니다 출력.
			request.setAttribute("error", nonkeyword);
			rd = request.getRequestDispatcher("/jsp/error.jsp");
//			request.getRequestDispatcher("/jsp/error.jsp").forward(request, response); //4
			
		}
		//forwarding 하는 로직이 안맞아. ???? 1, 3이 중복으로 forward 하잖아 아..? 그럼  음...? 저 경우가 될수가 있잖아 
		//그래서 forward가 두번 실행되서 한번 forward하고 다시 forward할 수 없다고 예외나는거야~아..
		//그러면 성공한 포워드저거를 이프문 안에 넣어주면 되려나?
		//순서 봐바
		rd.forward(request, response);
    }
}