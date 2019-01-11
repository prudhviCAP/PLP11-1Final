package com.cg.insurance.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cg.insurance.bean.CreateAccount;
import com.cg.insurance.bean.DetailedReportBean;
import com.cg.insurance.bean.InsuranceFormBean;
import com.cg.insurance.bean.PolicyBean;
import com.cg.insurance.bean.PremiumBean;
import com.cg.insurance.bean.Question;
import com.cg.insurance.bean.ReportGeneration;
import com.cg.insurance.exception.InsuranceException;
import com.cg.insurance.util.DBConnection;

public class InsuredDaoImpl implements IInsuredDao {
	

	@Override
	public String login(String userName, String password) throws SQLException, IOException {
		Connection con = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			preparedStatement = con.prepareStatement(QueryMapper.CHECK_EXISTING_USER_QUERY);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			String roleC = null;
			while(resultSet.next()) {
				roleC = resultSet.getString(1);
			}
			return roleC;
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}		
		return null;
	}
	
	@Override
	public List<PolicyBean> viewMyPolicies(String userName) throws InsuranceException {
		
		Connection con1;
		try {
			con1 = DBConnection.getConnection();
		
			PreparedStatement preparedStatement1=null;
			ResultSet resultSet1=null;
			preparedStatement1=con1.prepareStatement(QueryMapper.VIEW_MY_POLICIES_QUERY);
			preparedStatement1.setString(1, userName);
			resultSet1=preparedStatement1.executeQuery();
			List<PolicyBean> list1 = new ArrayList<>();
			
			while(resultSet1.next()) {
				PolicyBean policyBean=new PolicyBean();
				policyBean.setPolicyNumber(resultSet1.getLong(1));
				policyBean.setPolicyPremium(resultSet1.getInt(2));
				policyBean.setAccountNumber(resultSet1.getDouble(3));
				list1.add(policyBean);
			}
			return list1;
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}

	@Override
	public List<Question> getQuestions(int businessSegment) throws InsuranceException{
		
		Connection con;
		try {
			con = DBConnection.getConnection();
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
			preparedStatement = con.prepareStatement(QueryMapper.GET_QUESTIONS_QUERY);
				preparedStatement.setInt(1, businessSegment);
			resultSet = preparedStatement.executeQuery();
			List<Question> list = new ArrayList<>();
			while(resultSet.next()) {
				Question question = new Question();
				question.setPolicyQuestionId(resultSet.getInt(1));
				question.setBusinessSegmentId(resultSet.getInt(2));
				question.setQuestion(resultSet.getString(3));
				question.setAnswer1(resultSet.getString(4));
				question.setWeightage1(resultSet.getInt(5));
				question.setAnswer2(resultSet.getString(6));
				question.setWeightage2(resultSet.getInt(7));
				question.setAnswer3(resultSet.getString(8));
				question.setWeightage3(resultSet.getInt(9));
				list.add(question);
			}
			return list;
		} catch (SQLException | IOException e) {
		e.printStackTrace();
		}		
		return null;
	}


	@Override
	public int setPremium(int premium,String userName) throws InsuranceException{
		Connection con;
		try {
			con = DBConnection.getConnection();
		
			PreparedStatement preparedStatement=null;
			PreparedStatement preparedStatement1=null;
			ResultSet resultSet=null;
			long accountNumber = 0;
		
			preparedStatement = con.prepareStatement(QueryMapper.SET_PREMIUM_SELECT_QUERY);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				accountNumber = resultSet.getLong(1);
			}
			preparedStatement1 = con.prepareStatement(QueryMapper.SET_PREMIUM_INSERT_QUERY);
			preparedStatement1.setDouble(1, premium);
			preparedStatement1.setLong(2, accountNumber);
			preparedStatement1.executeUpdate();
			return 1;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public boolean createAccount(CreateAccount createBean) throws InsuranceException{
		Connection connection;
		try {
				connection = DBConnection.getConnection();
				PreparedStatement ps=null;
				ResultSet rs=null;
				
				ps=connection.prepareStatement(QueryMapper.CREATE_ACCOUNT_QUERY);
				CreateAccount.role roleCode = createBean.getRole_code();
				ps.setString(1,createBean.getUsername());
				ps.setString(2,createBean.getPassword());
				ps.setString(3,roleCode.name());
				ps.executeUpdate();
				
				return true;
		}catch (SQLException | IOException e1) {
		e1.printStackTrace();
	}
		return false;
	}

	@Override
	public List<ReportGeneration> generateReport(String userName) throws SQLException, IOException {
		
		Connection connection= DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps = connection.prepareStatement(QueryMapper.GENERATE_REPORT_QUERY);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			List<ReportGeneration> li = new ArrayList<>();
			while(rs.next()) {
				ReportGeneration reportGeneration = new ReportGeneration();
				reportGeneration.setSerialNo(rs.getInt(1));
				reportGeneration.setAccountNumber(rs.getLong(2));
				reportGeneration.setPolicyNumber(rs.getLong(3));
				reportGeneration.setPolicyPremium(rs.getDouble(4));
				li.add(reportGeneration);
			}
			return li;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}


	@Override
	public DetailedReportBean getDetailedReport(long policyNumber) throws InsuranceException{
		Connection connection;
		try {
			connection = DBConnection.getConnection();
		PreparedStatement ps=null;
		PreparedStatement ps1=null;
		ResultSet rs=null;
		ResultSet rs1=null;
			
			ps = connection.prepareStatement(QueryMapper.SELECT_ACCOUNTS_QUERY);
			ps.setLong(1, policyNumber);
			rs = ps.executeQuery();
			String businessSegmentName1 = null;
			DetailedReportBean detailedReportBean = new DetailedReportBean();
			while(rs.next()) {
				detailedReportBean.setInsuredName(rs.getString(1));
				detailedReportBean.setInsuredStreet(rs.getString(2));
				detailedReportBean.setInsuredCity(rs.getString(3));
				detailedReportBean.setInsuredState(rs.getString(4));
				detailedReportBean.setInsuredZip(rs.getInt(5));
				businessSegmentName1 = rs.getString(6);
				detailedReportBean.setBusinessSegment(businessSegmentName1);
				detailedReportBean.setPremiumAmount(rs.getDouble(7));
			}
			ps1 = connection.prepareStatement(QueryMapper.SELECT_POL_QUES_QUERY);
			ps1.setString(1, businessSegmentName1);
			rs1 = ps1.executeQuery();
			while(rs1.next()) {
				detailedReportBean.setQuesDesc1(rs1.getString(1));
				detailedReportBean.setQuesDesc2(rs1.getString(2));
				detailedReportBean.setQuesDesc3(rs1.getString(3));
				detailedReportBean.setQuesDesc4(rs1.getString(4));
				detailedReportBean.setQuesDesc5(rs1.getString(5));
				detailedReportBean.setCoverage1(rs1.getString(6));
				detailedReportBean.setCoverage2(rs1.getString(7));
				detailedReportBean.setCoverage3(rs1.getString(8));
			}
			return detailedReportBean;
		}catch(SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean isExistingUser(String userName) throws SQLException, IOException {
		Connection connection= DBConnection.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
		ps = connection.prepareStatement(QueryMapper.IS_EXISTING_QUERY);
		ps.setString(1, userName);
		rs = ps.executeQuery();
		while(rs.next()) {
			if(rs.getInt(1)>0) {
				return true;
			}else {
				return false;
			}
		}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean createNewAccount(InsuranceFormBean insuranceFormBean) throws InsuranceException{
		try {
			Connection connection= DBConnection.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			ps = connection.prepareStatement(QueryMapper.REQUEST_ACCOUNT_QUERY);
			
			ps.setString(1, insuranceFormBean.getInsuredName());
			ps.setString(2,insuranceFormBean.getInsuredStreet());
			ps.setString(3,insuranceFormBean.getInsuredCity());
			ps.setString(4,insuranceFormBean.getInsuredState());
			ps.setInt(5,insuranceFormBean.getInsuredZip());
			ps.setString(6,insuranceFormBean.getUserName());
			ps.executeUpdate();
			return true;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public int setPolicyDetails(List<PremiumBean> premiumBeanList,String userName) throws InsuranceException{
		
		Connection connection;
		try {
			connection = DBConnection.getConnection();
		
			PreparedStatement ps=null;
			PreparedStatement ps1=null;
			PreparedStatement ps2=null;
			PreparedStatement ps3=null;
			ResultSet rs=null;
			ResultSet rs1=null;
		
			Iterator<PremiumBean> premiumBeanIterator = premiumBeanList.iterator();
			ps = connection.prepareStatement(QueryMapper.SET_POLICY_DETAILS_1_QUERY);
			
			ps.setString(1, userName);
			rs = ps.executeQuery();
			long policyNumber= 0;
			long accountNum=0;
			while(rs.next()) {
				policyNumber = rs.getLong(1);
			}
			while(premiumBeanIterator.hasNext()) {
				PremiumBean premiumBean = new PremiumBean();
				premiumBean = premiumBeanIterator.next();
			
				ps1 = connection.prepareStatement(QueryMapper.SET_POLICY_DETAILS_2_QUERY);
				ps1.setLong(1, policyNumber);
				ps1.setInt(2,premiumBean.getPolicyQuestion());
				ps1.setString(3,premiumBean.getAnswer());
				ps1.executeUpdate();
				
				ps2 = connection.prepareStatement(QueryMapper.SET_POLICY_DETAILS_3_QUERY);
				ps2.setInt(1, premiumBean.getBusiness_segment());
				rs1 = ps2.executeQuery();
				String businessSegmentName = null;
				while(rs1.next()) {
					businessSegmentName = rs1.getString(1);
				}
				ps3 = connection.prepareStatement(QueryMapper.SET_POLICY_DETAILS_4_QUERY);
				ps3.setString(1, businessSegmentName);
				ps3.setString(2, userName);
				ps3.executeUpdate();
		}
		return 1;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public boolean assignPolicy(String customerName,String userName) throws InsuranceException{
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
		
			ps = connection.prepareStatement(QueryMapper.ASSIGN_POLICY_QUERY);
			ps.setString(1, userName);
			ps.setString(2, customerName);
			int update = ps.executeUpdate();
			if(update==1) {
				return true;
			}else {
				return false;
			}
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		return false;
	}

	@Override
	public List<PolicyBean> viewMyCustomers(String userName) {
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement ps=null;
			ResultSet rs=null;
			ps = connection.prepareStatement(QueryMapper.VIEW_MY_CUSTOMERS_QUERY);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			List<PolicyBean> policyBeanList = new ArrayList<>();
			while(rs.next()) {
				PolicyBean policyBean = new PolicyBean();
				policyBean.setAccountNumber(rs.getLong(1));
				policyBean.setPolicyNumber(rs.getInt(2));
				policyBean.setPolicyPremium(rs.getInt(3));
				policyBeanList.add(policyBean);
			}
			return policyBeanList;
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
