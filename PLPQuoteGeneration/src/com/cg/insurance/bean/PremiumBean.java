package com.cg.insurance.bean;

public class PremiumBean {

	private int policyQuestion;
	private String answer;
	private int business_segment;
	
	public int getPolicyQuestion() {
		return policyQuestion;
	}
	public void setPolicyQuestion(int i) {
		this.policyQuestion = i;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getBusiness_segment() {
		return business_segment;
	}
	public void setBusiness_segment(int business_segment) {
		this.business_segment = business_segment;
	}
	
	@Override
	public String toString() {
		return "PremiumBean [policyQuestion=" + policyQuestion + ", answer=" + answer + ", business_segment="
				+ business_segment + "]";
	}
	
	
}
