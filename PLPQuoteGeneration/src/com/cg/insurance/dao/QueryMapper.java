package com.cg.insurance.dao;

public interface QueryMapper {

		String CHECK_EXISTING_USER_QUERY = "select role_code from user_role where user_name = ? and password = ?";

		String VIEW_MY_POLICIES_QUERY = "select policy_number,policy_premium,b.account_number from accounts a,policy b where a.user_name=? and a.account_number=b.account_number";

		String GET_QUESTIONS_QUERY = "select pol_ques_id,a.bus_seg_id,pol_ques_desc,pol_ques_ans1,pol_ques_ans1_weightage,pol_ques_ans2,pol_ques_ans2_weightage,pol_ques_ans3,pol_ques_ans3_weightage from policy_questions a,business_segment b where b.bus_seg_id=? and a.bus_seg_id=b.bus_seg_id";

		String SET_PREMIUM_SELECT_QUERY = "select account_number from accounts where user_name = ?";
		
		String SET_PREMIUM_INSERT_QUERY = "insert into policy values(policy_number_seq.nextval,?,?)";
		
		String CREATE_ACCOUNT_QUERY = "insert into user_role values(?,?,?)";
		
		String GENERATE_REPORT_QUERY = "select policy_id_seq.nextval,a.account_number,b.policy_number,b.policy_premium from accounts a,policy b where a.user_name=? and a.account_number=b.account_number";
		
		String SELECT_ACCOUNTS_QUERY = "select a.insured_name,a.insured_street,a.insured_city,a.insured_state,a.insured_zip,a.business_segment,b.policy_premium from accounts a,policy b where b.policy_number=? AND b.account_number=a.account_number ";

		String SELECT_POL_QUES_QUERY = "select a.pol_ques_desc from policy_questions a,business_segment b where a.bus_seg_id=b.bus_seg_id and bus_seg_name=?";
		
		String IS_EXISTING_QUERY = "select count(*) from accounts where user_name=?";
		
		String REQUEST_ACCOUNT_QUERY = "insert into accounts values(account_number_seq.nextval,?,?,?,?,?,null,?,null)";
		
		String SET_POLICY_DETAILS_1_QUERY = "select max(policy_number) from accounts a,policy b where a.user_name = ? and a.account_number=b.account_number";
		
		String SET_POLICY_DETAILS_2_QUERY = "insert into policy_details values(?,?,?)";
		
		String SET_POLICY_DETAILS_3_QUERY = "select bus_seg_name from business_segment where bus_seg_id=?";
		
		String SET_POLICY_DETAILS_4_QUERY = "update accounts set business_segment=? where user_name=?";
		
		String ASSIGN_POLICY_QUERY = "update accounts set agent_name = ? where insured_name = ?";
		
		String VIEW_MY_CUSTOMERS_QUERY = "select a.account_number,b.policy_number,b.policy_premium from accounts a,policy b where agent_name=?";
}
