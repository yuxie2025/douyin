package com.yuxie.demo.dq;

import java.util.List;

public class User {

	/**
	 * code : 200 msg : 成功 data :
	 * {"id":"44042e79e38348919b8da4a6ac1aa252","autoId"
	 * :"227617","name":"用户227617"
	 * ,"photo":"","sign":"暂未设置签名","phone":"13550654021"
	 * ,"token":"51f0a75f34ce839d296aeea99bae7e55c509c9bf027487f1ff9fda9c"
	 * ,"isRenzheng"
	 * :false,"guanzhushu":3,"fensishu":0,"ifwechat":2,"ifpassword":
	 * 2,"zhifubao":""} data2 : []
	 */

	private int code;
	private String msg;
	private DataBean data;
	private List<?> data2;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public List<?> getData2() {
		return data2;
	}

	public void setData2(List<?> data2) {
		this.data2 = data2;
	}

	public static class DataBean {
		/**
		 * id : 44042e79e38348919b8da4a6ac1aa252 autoId : 227617 name : 用户227617
		 * photo : sign : 暂未设置签名 phone : 13550654021 token :
		 * 51f0a75f34ce839d296aeea99bae7e55c509c9bf027487f1ff9fda9c isRenzheng :
		 * false guanzhushu : 3 fensishu : 0 ifwechat : 2 ifpassword : 2
		 * zhifubao :
		 */

		private String id;
		private String autoId;
		private String name;
		private String photo;
		private String sign;
		private String phone;
		private String token;
		private String isRenzheng;
		private String guanzhushu;
		private String fensishu;
		private String ifwechat;
		private String ifpassword;
		private String zhifubao;

		private String imei;

		public String getImei() {
			return imei;
		}

		public void setImei(String imei) {
			this.imei = imei;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAutoId() {
			return autoId;
		}

		public void setAutoId(String autoId) {
			this.autoId = autoId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getIsRenzheng() {
			return isRenzheng;
		}

		public void setIsRenzheng(String isRenzheng) {
			this.isRenzheng = isRenzheng;
		}

		public String getGuanzhushu() {
			return guanzhushu;
		}

		public void setGuanzhushu(String guanzhushu) {
			this.guanzhushu = guanzhushu;
		}

		public String getFensishu() {
			return fensishu;
		}

		public void setFensishu(String fensishu) {
			this.fensishu = fensishu;
		}

		public String getIfwechat() {
			return ifwechat;
		}

		public void setIfwechat(String ifwechat) {
			this.ifwechat = ifwechat;
		}

		public String getIfpassword() {
			return ifpassword;
		}

		public void setIfpassword(String ifpassword) {
			this.ifpassword = ifpassword;
		}

		public String getZhifubao() {
			return zhifubao;
		}

		public void setZhifubao(String zhifubao) {
			this.zhifubao = zhifubao;
		}

		@Override
		public String toString() {
			return "DataBean [id=" + id + ", autoId=" + autoId + ", name="
					+ name + ", photo=" + photo + ", sign=" + sign + ", phone="
					+ phone + ", token=" + token + ", isRenzheng=" + isRenzheng
					+ ", guanzhushu=" + guanzhushu + ", fensishu=" + fensishu
					+ ", ifwechat=" + ifwechat + ", ifpassword=" + ifpassword
					+ ", zhifubao=" + zhifubao + ", imei=" + imei + "]";
		}



	}

	@Override
	public String toString() {
		return "User [code=" + code + ", msg=" + msg + ", data=" + data
				+ ", data2=" + data2 + "]";
	}


}
