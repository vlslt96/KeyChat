package com.keychat.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.keychat.controller.util.DBUtil;
import com.keychat.dto.base.ChannelChatHistoryReadModel;
import com.keychat.dto.base.ChannelsCategoriesModel;
import com.keychat.dto.base.ChannelsEntitiesModel;
import com.keychat.dto.base.ChannelsKeywordRecomModel;

public class ChannelsKeywordRecomDao {
	public static boolean saveEntity(ChannelsEntitiesModel channelsEntitiesModel) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO CHANNELS_ENTITIES VALUES(CHANNELS_ENTITIES_ID_SEQ.nextval, ?, ?, SYSTIMESTAMP)";
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channelsEntitiesModel.getEntities());
			pstmt.setString(2, channelsEntitiesModel.getChannel_name());
			pstmt.executeUpdate();
		} catch (SQLException s) {
			s.printStackTrace();
		} finally {
			DBUtil.close(pstmt, con);
		}
		return true;
	}

	public static ArrayList<String> getEntitiesList(ChannelChatHistoryReadModel channelChatHistoryReadModel) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT ENTITY FROM (SELECT ENTITY, CREATED_DATE FROM CHANNELS_ENTITIES WHERE CHANNEL_NAME = ? ORDER BY CREATED_DATE DESC)  WHERE ROWNUM <= 5";

		ArrayList<String> entityList = new ArrayList<String>();
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channelChatHistoryReadModel.getChannelName());
//			pstmt.setInt(2, channelChatHistoryReadModel.getCount());
			rset = pstmt.executeQuery();
			while (rset.next()) {
				entityList.add(rset.getString(1));
			}
		} catch (SQLException s) {
			s.printStackTrace();
		} finally {
			DBUtil.close(pstmt, con);
		}
		return entityList;
	}

	//키워드값을 받아와서 키워드테이블에 저장한다
	public static boolean saveKeyword(ChannelsKeywordRecomModel channelsKeywordRecomModel) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO CHANNELS_KEYWORD_RECOM VALUES (CHANNELS_KEYWORD_RECOM_ID_SEQ.nextval, ?, ?, SYSTIMESTAMP)";
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channelsKeywordRecomModel.getKeyword());
			pstmt.setString(2, channelsKeywordRecomModel.getChannel_name());
			pstmt.executeUpdate();
		} catch (SQLException s) {
			s.printStackTrace();
		} finally {
			DBUtil.close(pstmt, con);
		}
		return true;
	}



	public static ArrayList<String> getKeywordList(ChannelChatHistoryReadModel channelChatHistoryReadModel) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT KEYWORD FROM (SELECT KEYWORD, CREATED_DATETIME FROM CHANNELS_KEYWORD_RECOM WHERE CHANNEL_NAME = ? ORDER BY CREATED_DATETIME DESC) WHERE ROWNUM <= 5";

		ArrayList<String> keywordList = new ArrayList<String>();
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channelChatHistoryReadModel.getChannelName());
			rset = pstmt.executeQuery();
			while (rset.next()) {
				keywordList.add(rset.getString(1));
			}
		} catch (SQLException s) {
			s.printStackTrace();
		} finally {
			DBUtil.close(pstmt, con);
		}
		return keywordList;
	}

	// 채널 참여자가 보낸 메시지를 가지고 분석해서 가장 많은 분포도를 group by로 묶고 count(*)으로 인기 순위를 나타낸다.
	public static ArrayList<String> findKeyword(String channel_name) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select KEYWORD, count(*) from CHANNELS_KEYWORD_RECOM where CHANNEL_NAME = ? group by KEYWORD order by count(*) desc";
		ArrayList<String> list = new ArrayList<String>();
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channel_name);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(rset.getString(1));
			}
		} catch (SQLException s) {
			s.printStackTrace();
			throw s;
		} finally {
			DBUtil.close(pstmt, con);
		}
		return list;
	}

	//카테고리 DB에 저장
	public static boolean saveCategory(ChannelsCategoriesModel channelsCategoriesModel) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String query = "INSERT INTO CHANNELS_CATEGORIES VALUES(CHANNELS_CATEGORIES_ID_SEQ.nextval, ?, ?, SYSTIMESTAMP)";
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channelsCategoriesModel.getCategories());
			pstmt.setString(2, channelsCategoriesModel.getChannel_name());
			pstmt.executeUpdate();
		} catch (SQLException s) {
			s.printStackTrace();
		} finally {
			DBUtil.close(pstmt, con);
		}
		return true;
	}

	public static ArrayList<String[]> getCategoryList(ChannelChatHistoryReadModel channelChatHistoryReadModel) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT CATEGORIES, COUNT(CATEGORIES) FROM (SELECT CATEGORIES, DATETIME FROM CHANNELS_CATEGORIES WHERE CHANNEL_NAME = ? ORDER BY DATETIME DESC) GROUP BY CATEGORIES order by count(CATEGORIES) desc";

		ArrayList<String[]> list = new ArrayList<String[]>();

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channelChatHistoryReadModel.getChannelName());
//			pstmt.setInt(2, channelChatHistoryReadModel.getCount());
			rset = pstmt.executeQuery();
			while (rset.next()) {
				String a = rset.getString(1);
				String b = rset.getString(2);
				String[] li = new String[2];
				li[0] = a;
				li[1] = b;
				list.add(li);
			}
		} catch (SQLException s) {
			s.printStackTrace();
		} finally {
			DBUtil.close(pstmt, con);
		}
		return list;
	}

	//카테고리 불러오기
	public static ArrayList<String[]> findCategory(String channel_name) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "SELECT CATEGORIES, COUNT(*) FROM (SELECT CATEGORIES, DATETIME FROM CHANNELS_CATEGORIES WHERE CHANNEL_NAME = ? ORDER BY DATETIME DESC) GROUP BY CATEGORIES";
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, channel_name);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				String a = rset.getString(1);
				String b = rset.getString(2);
				String[] li = new String[2];
				li[0] = a;
				li[1] = b;
				list.add(li);
			}
		} catch (SQLException s) {
			s.printStackTrace();
			throw s;
		} finally {
			DBUtil.close(pstmt, con);
		}return list;
	}

	
}