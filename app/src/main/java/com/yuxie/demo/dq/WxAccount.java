package com.yuxie.demo.dq;

public class WxAccount {

	private String name;
	private String pic;
	
	
	public WxAccount() {
		super();
	}

	@Override
	public String toString() {
		return "WxAccount [name=" + name + ", pic=" + pic + "]";
	}

	public WxAccount(String name, String pic) {
		super();
		this.name = name;
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}
