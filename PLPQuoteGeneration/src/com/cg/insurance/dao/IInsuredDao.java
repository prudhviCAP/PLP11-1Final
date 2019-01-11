package com.cg.insurance.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.cg.insurance.bean.CreateAccount;
import com.cg.insurance.bean.DetailedReportBean;
import com.cg.insurance.bean.InsuranceFormBean;
import com.cg.insurance.bean.PolicyBean;
import com.cg.insurance.bean.PremiumBean;
import com.cg.insurance.bean.Question;
import com.cg.insurance.bean.ReportGeneration;
import com.cg.insurance.exception.InsuranceException;

public interface IInsuredDao {

	public List<PolicyBean> viewMyPolicies(String userName) throws Exception;
	
	public List<Question> getQuestions(int businessSegment) throws InsuranceException;
	
	public int setPremium(int premium, String userName) throws InsuranceException;
	
	public boolean createAccount(CreateAccount createBeanServ) throws InsuranceException;
	
	public List<ReportGeneration> generateReport(String userName) throws SQLException, IOException;
	
	public DetailedReportBean getDetailedReport(long policyNumber) throws InsuranceException;
	
	public String login(String userName, String password) throws SQLException, IOException;
	
	public boolean isExistingUser(String userName) throws SQLException, IOException;

	public boolean createNewAccount(InsuranceFormBean insuranceFormBean) throws InsuranceException;

	public int setPolicyDetails(List<PremiumBean> premiumBeanList, String userName) throws InsuranceException;

	public boolean assignPolicy(String customerName,String userName) throws InsuranceException;

	public List<PolicyBean> viewMyCustomers(String userName);

}
