package com.cg.insurance.pl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.cg.insurance.bean.CreateAccount;
import com.cg.insurance.bean.CreateAccount.role;
import com.cg.insurance.bean.DetailedReportBean;
import com.cg.insurance.bean.InsuranceFormBean;
import com.cg.insurance.bean.PolicyBean;
import com.cg.insurance.bean.PremiumBean;
import com.cg.insurance.bean.Question;
import com.cg.insurance.bean.ReportGeneration;
import com.cg.insurance.service.IInsured;
import com.cg.insurance.service.IInsuredImpl;

public class InsuranceMain {

	
	static Scanner scanner = null;
	static CreateAccount createBean = null;
	static String userName = null;
	static String password = null;
	public static void main(String[] args) throws Exception {
	scanner=new Scanner(System.in);
	
	System.out.println("*****************Welcome To Online Insurance Quote Generation****************");
	System.out.println(" Please Login To Continue..");	
	System.out.print(" Enter Your Username  ");
	System.out.println("  example:ajay");
	userName = scanner.next();
	System.out.print(" Enter Your Password  ");
	System.out.println(" example: hello123");
	password = scanner.next();
	IInsuredImpl iInsuredImpl = new IInsuredImpl();
	IInsured iInsured = new IInsuredImpl();
	if(iInsuredImpl.isValidUserName(userName) && iInsuredImpl.isValidPassword(password)) {
		String roleC = iInsured.login(userName,password);
		if(roleC!=null)
		{
		switch(roleC) {
					case "user":
						while(true) {
							if(iInsured.isExistingUser(userName)) {
							System.out.println("--*---*---*---*---*---*---*--*---");
							System.out.println("     Welcome Insured User        ");
							System.out.println("*---*---*----*---*---*---*---*---");
							System.out.println("  1. View My Policies            ");
							System.out.println("  2. Request for New Policy      ");
							System.out.println("  3. Exit                        ");
							System.out.println("                                 ");
							System.out.println("--*---*-Choose your option--*----");
						
							int option1=0;
							System.out.println("Enter Option from Menu");
							option1 = scanner.nextInt();
							switch(option1) {
								case 1:
									try {
										IInsured  iInsured1 = new IInsuredImpl();
										List<PolicyBean> al1 = new ArrayList<>();
										al1 = iInsured1.viewMyPolicies(userName);
										Iterator<PolicyBean> itr2 = al1.iterator();
										if(!al1.isEmpty()) {
										while(itr2.hasNext()) {
											PolicyBean policyBean = new PolicyBean();
											policyBean = itr2.next();
											if(policyBean.getPolicyNumber()==0) {
												System.out.println("Policy Not Yet Assigned/nRequest still under process");
											}else if(policyBean.getPolicyNumber()>0){
												System.out.println(policyBean);
											}
											}
										}
										else {
											System.out.println("no policies existing for user "+userName);
										}
									}
									catch(Exception e){
										e.getStackTrace();
									}
									break;
								case 2:
									iInsured = new IInsuredImpl();
									
									int businessSegment;
									System.out.println(" Select Business segment number from ");
									System.out.println("1. Business_Auto \n 2. Restaurant \n 3.Apartment \n 4.General_Merchant ");
									businessSegment = scanner.nextInt();
									
									List<PremiumBean> premiumBeanList = new ArrayList<>();	
									List<Question> questionList = new ArrayList<>();
									questionList = iInsured.getQuestions(businessSegment);
									Iterator<Question> itr = questionList.iterator();
									if(questionList.isEmpty()) {
										System.out.println("no questions existing in business segment, please select other business segment");
									}
									else {
											int premium=10000;
											while(itr.hasNext()){
												PremiumBean premiumBean = new PremiumBean();
												Question question = new Question();
												question = itr.next();
												System.out.println("select "+question.getQuestion());
												System.out.println(" from \n1."+question.getAnswer1()+"\n2."+question.getAnswer2()+"\n3."+question.getAnswer3());
												int answer;
												answer = scanner.nextInt();
												premiumBean.setPolicyQuestion(question.getPolicyQuestionId());
												premiumBean.setBusiness_segment(businessSegment);
												switch(answer) {
													case 1:
														premium += question.getWeightage1();
														premiumBean.setAnswer(question.getAnswer1());
													break;
													case 2:
														premium += question.getWeightage2();
														premiumBean.setAnswer(question.getAnswer2());
													break;
													case 3:
														premium += question.getWeightage3();
														premiumBean.setAnswer(question.getAnswer3());
													break;
													default:
														System.out.println(" Select Valid Answer..");
													break;
													}
												premiumBeanList.add(premiumBean);
											}
											System.out.println(" Estimated Premium For Requested Policy is :"+premium);
											String yn = null;
											System.out.println(" Would you like to Continue with this policy: Yes/No");
											yn = scanner.next();
											switch(yn){
											case "Yes":
												int policyNumber = iInsured.setPremium(premium,userName);
												
												int setPolicydetails = iInsured.setPolicyDetails(premiumBeanList,userName);
												int addition = policyNumber + setPolicydetails;
												if(addition==2) {
													System.out.println(" Your Details has been Succesfully Sent, Wait for approval");
												}else {
													System.err.println(" Not able to Assign Policy");
												}
											break;
											case "No":
												System.out.println(" Visit our Application Webite for further details \n MyApplication.capgemini.com");
											break;
											}
									}
									break;
									case 3:
										System.exit(0);
									default:
										System.out.println("enter valid option.....");
									break;
							}
						}else {
							System.out.println("--*---*---*---*---*---*---*--*---");
							System.out.println("     Welcome Insured User        ");
							System.out.println("*---*---*----*---*---*---*---*---");
							System.out.println("  1. Create Account              ");
							System.out.println("  2. Exit                        ");
							System.out.println("                                 ");
							System.out.println("--*---*-Choose your option--*----");
						
							int option1=0;
							System.out.println("Enter Option from Menu");
							option1 = scanner.nextInt();
							switch(option1) {
								case 1:
									iInsured = new IInsuredImpl();
									System.out.println(" enter details and get scheme");
									InsuranceFormBean insuranceFormBean = new InsuranceFormBean();
									String insuredName;
									System.out.println(" insured name :");
									insuredName = scanner.next();
									insuranceFormBean.setInsuredName(insuredName);
								
									String insuredStreet;
									System.out.println(" street : ");
									insuredStreet = scanner.next();
									insuranceFormBean.setInsuredStreet(insuredStreet);
								
									String insuredCity;
									System.out.println(" city :");
									insuredCity = scanner.next();
									insuranceFormBean.setInsuredCity(insuredCity);
								
									String insuredState;
									System.out.println(" state :");
									insuredState = scanner.next();
									insuranceFormBean.setInsuredState(insuredState);
								
									int insuredZip;
									System.out.println(" enter your zip code ");
									insuredZip = scanner.nextInt();
									insuranceFormBean.setInsuredZip(insuredZip);
									
									insuranceFormBean.setUserName(userName);
									boolean createAccountResult = false;
									createAccountResult = iInsured.createNewAccount(insuranceFormBean);
									if(createAccountResult) {
									System.out.println(" login again to request policies..");
									}else {
										System.out.println(" unable to create account..try again ");
									}
								case 2:
									System.exit(0);
								default:
										System.out.println("enter valid option.....");
								break;
							
								}
							}
							break;
						}
						break;
				case "agent":
					while(true) {
						System.out.println("--*---*---*---*---*---*---*---*---*---*");
						System.out.println("       Welcome Insurance Agent         ");
						System.out.println("--*---*---*---*---*--*---*---*---*---*-");
						System.out.println("  1. Approve Requests from Insured User");
						System.out.println("  2. View My Customer Policies         ");
						System.out.println("  3. Exit                              ");
						System.out.println("                                       ");
						System.out.println("--*---*---*-Choose your option--*---*--");
						int option2=0;
						option2 = scanner.nextInt();
							switch(option2) {
								case 1:
									iInsuredImpl = new IInsuredImpl();
									String customerName = null;
									System.out.println(" Enter Customer name to verify request");
									customerName = scanner.next();
									iInsured = new IInsuredImpl();
									boolean assignPolicy = false;
									if(iInsuredImpl.isValidUserName(customerName)) {
										assignPolicy = iInsured.assignPolicy(customerName,userName);
										if(assignPolicy) {
											System.out.println(" Agent "+userName+" approved "+customerName+"'s request");
										}else {
											System.out.println(" Unable to assign policy as per "+customerName+"'s details");
										}
									}else {
										System.out.println("Invalid user name entered..");
									}
								break;
								case 2:
									iInsured = new IInsuredImpl();
									List<PolicyBean> policyBeanList = new ArrayList<>();
									policyBeanList = iInsured.viewMyCustomers(userName);
									Iterator<PolicyBean> policyBeanIterator = policyBeanList.iterator();
									if(!policyBeanList.isEmpty()) {
										while(policyBeanIterator.hasNext()){
											PolicyBean policyBean = new PolicyBean();
											policyBean = policyBeanIterator.next();
											System.out.println("Account Number"+"\t\t\t\tPolicy Number"+"\t\t\tPolicy Premium");
											System.out.println(policyBean.getAccountNumber()+"\t\t\t\t"+policyBean.getPolicyNumber()+"\t\t\t\t"+policyBean.getPolicyPremium());
										}
									}else {
										System.out.println("No Customers under"+userName+" have registered policy");
									}
								break;
								case 3:
									System.exit(0);
								default:
									System.out.println("Enter Valid Option");
								break;
						}
							break;
					}
					break;
				case "admin":
					while(true) {
						createBean = new CreateAccount();
						iInsured = new IInsuredImpl();
						iInsuredImpl = new IInsuredImpl();
						System.out.println("--*---*---*---*---*---*---*---*---*---*");
						System.out.println("          Welcome Admin                ");
						System.out.println("*---*---*----*---*---*---*---*---*---*-");
						System.out.println("  1. Create user/agent/admin account   ");
						System.out.println("  2. View Users Existing Policies      ");
						System.out.println("  3. View Agents Customer Details      ");
						System.out.println("  4. Generate Report                   ");
						
						System.out.println("  5. Exit                              ");
						System.out.println("                                       ");
						System.out.println("--*---*--Choose your option--*---*---*-");
					
						int option3 = 0;
						option3 = scanner.nextInt();
							switch(option3) {
								case 1:
										createBean=new CreateAccount();
										iInsuredImpl = new IInsuredImpl();
										String customerName2 = null;
										System.out.println("Enter Username ");
										customerName2 = scanner.next();
										if(iInsuredImpl.isValidUserName(customerName2)) {
										createBean.setUsername(customerName2);
										}
										String password;
										System.out.println("Enter Password ");
										password = scanner.next();
										if(iInsuredImpl.isValidPassword(password)) {
										createBean.setPassword(password);
										}
										try {
										CreateAccount.role roleCode;
										System.out.println("Enter Role Code As "+role.user+"/ "+role.agent+"/ "+role.admin);
										roleCode = CreateAccount.role.valueOf(scanner.next());
										createBean.setRole_code(roleCode);	
										}catch(IllegalArgumentException ie) {
											System.out.println(ie.getMessage());
										}
										iInsuredImpl.validateBean(createBean);
										boolean result=iInsured.createAccount(createBean);	
										if(result)
										{
											System.out.println("Profile Created Successfully");
										}
										else
										{
											System.out.println("Profile Creation Unsuccessful");
										}
								break;
								case 2:
									try {
										IInsured  iInsured1 = new IInsuredImpl();
										List<PolicyBean> al1 = new ArrayList<>();
										System.out.println(" Enter Insured Name to View His Policies");
										String customerName = scanner.next();
										al1 = iInsured1.viewMyPolicies(customerName);
										Iterator<PolicyBean> itr2 = al1.iterator();
										if(!al1.isEmpty()) {
										while(itr2.hasNext()) {
											PolicyBean policyBean = new PolicyBean();
											policyBean = itr2.next();
											if(policyBean.getPolicyNumber()==0) {
												System.out.println("Policy Not Yet Assigned/nRequest still under process");
											}else if(policyBean.getPolicyNumber()>0){
												System.out.println(policyBean);
											}
											}
										}
										else {
											System.out.println("no policies existing for user "+customerName);
										}
									}
									catch(Exception e){
										e.getStackTrace();
									}
									break;
								case 3:
									iInsured = new IInsuredImpl();
									List<PolicyBean> policyBeanList = new ArrayList<>();
									System.out.println(" Enter Agent name to View his Customers Details");
									String agentName1 = scanner.next();
									policyBeanList = iInsured.viewMyCustomers(agentName1);
									Iterator<PolicyBean> policyBeanIterator = policyBeanList.iterator();
									if(!policyBeanList.isEmpty()) {
										while(policyBeanIterator.hasNext()){
											PolicyBean policyBean = new PolicyBean();
											policyBean = policyBeanIterator.next();
											System.out.println("Account Number"+"\t\t\t\tPolicy Number"+"\t\t\tPolicy Premium");
											System.out.println(policyBean.getAccountNumber()+"\t\t\t\t"+policyBean.getPolicyNumber()+"\t\t\t\t"+policyBean.getPolicyPremium());
										}
									}else {
										System.out.println("No Customers under"+agentName1+" have registered policy");
									}
								break;    
								case 4:
									iInsured = new IInsuredImpl();
									System.out.println("Enter Username ");
									String customerName1 = scanner.next();
									List<ReportGeneration> li = new ArrayList<>();
									li =  iInsured.generateReport(customerName1);
									Iterator<ReportGeneration> itr2 = li.iterator();
									int i = 1;
									while(itr2.hasNext()) {
										System.out.println("{ serial No "+i+" "+itr2.next());
										i++;
									}
								
									System.out.println("        ");
									System.out.println(" 1. get detailed policy view ");
									System.out.println(" 2. exit  ");
									System.out.println("             ");
									System.out.println("select option ");
									int view=0;
									view = scanner.nextInt();
									switch(view) {
										case 1:
											DetailedReportBean detailedReportBean = new DetailedReportBean();
											System.out.println(" enter policy number to get detailed report ");
											long policyNumber = scanner.nextLong();
											detailedReportBean = iInsured.getDetailedReport(policyNumber);
											System.out.println(detailedReportBean);
										break;
										case 2:
											System.exit(0);
										default:
											System.out.println("invalid option ");
										break;
										}
								break;
								case 5:
										
								default:
								System.out.println(" enter valid option ");
								break;
						}
						break;
					}
					break;
					default:
						System.out.println("enter valid option    ");
					break;
			}
		}
		else {
			System.out.println("enter valid username and password");
	
	}
	}
	}}
