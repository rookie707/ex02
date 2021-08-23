package org.zerock.domain;


public class Criteria {
	// 페이지번호(pageNum)
	private int pageNum;
	// 한 페이지당 데이터 갯수(amount)
	private int amount;
	// type
	private String type;
	// keyword
	private String keyword;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Criteria() { // 기본생성자
		this(1,10); // 기본페이지번호 1, 페이지를 10개를 보여줌
	}
	public Criteria(int pageNum, int amount) { // 매개변수가 2개인 생성자
		this.pageNum=pageNum; // 페이지 번호
		this.amount=amount; // 보여주는 페이지 갯수
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", type=" + type + ", keyword=" + keyword + "]";
	}
	
}
