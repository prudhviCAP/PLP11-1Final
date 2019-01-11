package com.cg.insurance.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cg.insurance.bean.CreateAccount;
import com.cg.insurance.bean.DetailedReportBean;
import com.cg.insurance.bean.InsuranceFormBean;
import com.cg.insurance.bean.PolicyBean;
import com.cg.insurance.bean.PremiumBean;
import com.cg.insurance.bean.Question;
import com.cg.insurance.bean.ReportGeneration;
import com.cg.insurance.dao.IInsuredDao;
import com.cg.insurance.dao.InsuredDaoImpl;
import com.cg.insurance.exception.InsuranceException;


public class IInsuredImpl implements IInsured{

	IInsuredDao iInsuredDao = null;
	
	@Override
	public String login(String userName, String password) throws SQLException, IOException {
		iInsuredDao = new InsuredDaoImpl();
		String roleC = iInsuredDao.login(userName,password);
		return roleC;
	}
	
	@Override
	public List<PolicyBean> viewMyPolicies(String userName) throws Exception {
		iInsuredDao=new InsuredDaoImpl();
		List<PolicyBean> list1=new ArrayList<>();
		list1=iInsuredDao.viewMyPolicies(userName);
		return list1;
	}

	@Override
	public List<Question> getQuestions(int businessSegment)throws InsuranceException {
		iInsuredDao = new InsuredDaoImpl();
		List<Question> list = new ArrayList<>();
		try {
			list = iInsuredDao.getQuestions(businessSegment);
		} catch (InsuranceException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int setPremium(int premium, String userName) throws InsuranceException{
		int premiumSet= 0;
		iInsuredDao = new InsuredDaoImpl();
		try {
			premiumSet = iInsuredDao.setPremium(premium,userName);
			return premiumSet;
		} catch (InsuranceException e) {
			e.printStackTrace();
		}
		return premiumSet;
		
	}
	
	@Override
	public boolean createAccount(CreateAccount createBean) throws InsuranceException {
		
		iInsuredDao=new InsuredDaoImpl();
		CreateAccount createBeanServ=createBean;
		
		try {
			iInsuredDao.createAccount(createBeanServ);
			return true;
		} catch (InsuranceException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<ReportGeneration> generateReport(String userName) throws IOException, SQLException {
		iInsuredDao=new InsuredDaoImpl();
		List<ReportGeneration> li = new ArrayList<>();
		li = iInsuredDao.generateReport(userName);
		return li;
	}
	
	@Override
	public DetailedReportBean getDetailedReport(long policyNumber) throws IOException, SQLException {
		DetailedReportBean detailedReportBean = new DetailedReportBean();
		iInsuredDao=new InsuredDaoImpl();
		try {
			detailedReportBean = iInsuredDao.getDetailedReport(policyNumber);
		} catch (InsuranceException e) {
			e.printStackTrace();
		}
		return detailedReportBean;
	}
	public CreateAccount validateBean(CreateAccount createBean) {
		
		List<String> validationErrors = new ArrayList<String>();
		
		if(!(isValidName(createBean.getUsername())))
		{
			validationErrors.add("\n UserName Should Be In Alphabets And Minimum 3 Character Long \n");
		}
		
		if(!(isValidpassword(createBean.getPassword())))
		{
			validationErrors.add("\n Password Should be Minimum 3 Characters \n");
		}
		
	
		if(validationErrors.isEmpty())
		{
			return createBean;
		}
		else
		{
			return null;
		}
	}	
		private boolean isValidName(String username) {
		
			Pattern namePattern = Pattern.compile("^[A-Za-z]{3,}$");
			Matcher nameMatcher = namePattern.matcher(username);
			return nameMatcher.matches();
		}
		
		private boolean isValidpassword(String password) {
			
			Pattern namePattern = Pattern.compile("^[A-Za-z]{3,}$");
			Matcher nameMatcher = namePattern.matcher(password);
			return nameMatcher.matches();
		}
		
		public boolean isValidUserName(String userName) {
			Pattern userNamePattern = Pattern.compile("^[A-Za-z]{3,}$");
			Matcher userNameMatcher = userNamePattern.matcher(userName);
			return userNameMatcher.matches();
		}

		public boolean isValidPassword(String password) {
			Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9]{3,10}");
			Matcher passwordMatcher = passwordPattern.matcher(password);
			return passwordMatcher.matches();
		}

		@Override
		public boolean isExistingUser(String userName) throws SQLException, IOException {
			iInsuredDao = new InsuredDaoImpl();
			boolean createAccountResult = iInsuredDao.isExistingUser(userName);
			return createAccountResult;
		}

		@Override
		public boolean createNewAccount(InsuranceFormBean insuranceFormBean)throws InsuranceException{
			iInsuredDao = new InsuredDaoImpl();
			boolean createNewAccount=false;
			try {
				createNewAccount = iInsuredDao.createNewAccount(insuranceFormBean);
				return createNewAccount;
			} catch (InsuranceException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public int setPolicyDetails(List<PremiumBean> premiumBeanList,String userName) throws InsuranceException{
			iInsuredDao =  new InsuredDaoImpl();
			int premiumSet;
			try {
				premiumSet = iInsuredDao.setPolicyDetails(premiumBeanList,userName);
				return premiumSet;
			} catch (InsuranceException e) {
				e.printStackTrace();
			}
			return 0;
			
		}

		@Override
		public boolean assignPolicy(String customerName,String userName) throws InsuranceException{
			iInsuredDao = new InsuredDaoImpl();
			boolean assignPolicy;
			try {
				assignPolicy = iInsuredDao.assignPolicy(customerName,userName);
				return assignPolicy;
			} catch (InsuranceException e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		public List<PolicyBean> viewMyCustomers(String userName) {
			iInsuredDao = new InsuredDaoImpl();
			List<PolicyBean> policyBeanList = new ArrayList<>();
			policyBeanList = iInsuredDao.viewMyCustomers(userName);
			return policyBeanList;
		}

}
