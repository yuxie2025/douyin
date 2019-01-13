package com.yuxie.demo.sy;

import java.util.List;

public class UserPendingTokensBean {

	/**
	 * code : 200 msg : success data :
	 * {"cash":"0.0896598","collected_cash":"1.0896598"
	 * ,"ticket":"0.0","token":"110.449"
	 * ,"amount":"0.0","pending_tokens":[{"id":33216898
	 * ,"amount":"0.005"},{"id":33590654
	 * ,"amount":"0.0005"},{"id":33590676,"amount"
	 * :"0.005"},{"id":33590742,"amount"
	 * :"0.05"},{"id":33590804,"amount":"0.0005"
	 * },{"id":33590821,"amount":"0.005"
	 * },{"id":33590965,"amount":"0.005"},{"id":
	 * 33591084,"amount":"0.0005"},{"id"
	 * :33591110,"amount":"0.005"},{"id":33591259
	 * ,"amount":"0.005"},{"id":33591325
	 * ,"amount":"0.05"},{"id":33591406,"amount"
	 * :"0.0005"},{"id":33591454,"amount"
	 * :"0.005"},{"id":33591570,"amount":"0.0005"
	 * },{"id":33591624,"amount":"0.005"
	 * },{"id":33591772,"amount":"0.05"},{"id":33591842
	 * ,"amount":"0.0005"},{"id":
	 * 33591891,"amount":"0.005"},{"id":33591984,"amount"
	 * :"0.005"},{"id":33592128
	 * ,"amount":"0.05"},{"id":33592177,"amount":"0.0005"
	 * },{"id":33592333,"amount"
	 * :"0.005"},{"id":33592379,"amount":"0.0005"},{"id"
	 * :33592404,"amount":"0.005"
	 * },{"id":33592464,"amount":"0.05"},{"id":33592521
	 * ,"amount":"0.0005"},{"id":
	 * 33592568,"amount":"0.005"},{"id":33592701,"amount"
	 * :"0.05"},{"id":33592761,
	 * "amount":"0.0005"},{"id":33592940,"amount":"0.05"}
	 * ,{"id":33592976,"amount"
	 * :"0.0005"},{"id":33593061,"amount":"0.005"},{"id":
	 * 33593158,"amount":"0.05"
	 * },{"id":33593230,"amount":"0.0005"},{"id":33593305
	 * ,"amount":"0.005"},{"id"
	 * :33593356,"amount":"0.05"},{"id":33593408,"amount":"0.0005"}]}
	 */

	private String code;
	private String msg;
	private DataBean data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
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

	public static class DataBean {
		/**
		 * cash : 0.0896598 collected_cash : 1.0896598 ticket : 0.0 token :
		 * 110.449 amount : 0.0 pending_tokens :
		 * [{"id":33216898,"amount":"0.005"
		 * },{"id":33590654,"amount":"0.0005"},{"id"
		 * :33590676,"amount":"0.005"},{
		 * "id":33590742,"amount":"0.05"},{"id":33590804
		 * ,"amount":"0.0005"},{"id"
		 * :33590821,"amount":"0.005"},{"id":33590965,"amount"
		 * :"0.005"},{"id":33591084
		 * ,"amount":"0.0005"},{"id":33591110,"amount":"0.005"
		 * },{"id":33591259,"amount"
		 * :"0.005"},{"id":33591325,"amount":"0.05"},{"id"
		 * :33591406,"amount":"0.0005"
		 * },{"id":33591454,"amount":"0.005"},{"id":33591570
		 * ,"amount":"0.0005"},{
		 * "id":33591624,"amount":"0.005"},{"id":33591772,"amount"
		 * :"0.05"},{"id":
		 * 33591842,"amount":"0.0005"},{"id":33591891,"amount":"0.005"
		 * },{"id":33591984
		 * ,"amount":"0.005"},{"id":33592128,"amount":"0.05"},{"id"
		 * :33592177,"amount"
		 * :"0.0005"},{"id":33592333,"amount":"0.005"},{"id":33592379
		 * ,"amount":"0.0005"
		 * },{"id":33592404,"amount":"0.005"},{"id":33592464,"amount"
		 * :"0.05"},{"id"
		 * :33592521,"amount":"0.0005"},{"id":33592568,"amount":"0.005"
		 * },{"id":33592701
		 * ,"amount":"0.05"},{"id":33592761,"amount":"0.0005"},{"id"
		 * :33592940,"amount"
		 * :"0.05"},{"id":33592976,"amount":"0.0005"},{"id":33593061
		 * ,"amount":"0.005"
		 * },{"id":33593158,"amount":"0.05"},{"id":33593230,"amount"
		 * :"0.0005"},{"id"
		 * :33593305,"amount":"0.005"},{"id":33593356,"amount":"0.05"
		 * },{"id":33593408,"amount":"0.0005"}]
		 */

		private String cash;
		private String collected_cash;
		private String ticket;
		private String token;
		private String amount;
		private List<PendingTokensBean> pending_tokens;

		public String getCash() {
			return cash;
		}

		public void setCash(String cash) {
			this.cash = cash;
		}

		public String getCollected_cash() {
			return collected_cash;
		}

		public void setCollected_cash(String collected_cash) {
			this.collected_cash = collected_cash;
		}

		public String getTicket() {
			return ticket;
		}

		public void setTicket(String ticket) {
			this.ticket = ticket;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public List<PendingTokensBean> getPending_tokens() {
			return pending_tokens;
		}

		public void setPending_tokens(List<PendingTokensBean> pending_tokens) {
			this.pending_tokens = pending_tokens;
		}

		public static class PendingTokensBean {
			/**
			 * id : 33216898 amount : 0.005
			 */

			private String id;
			private String amount;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getAmount() {
				return amount;
			}

			public void setAmount(String amount) {
				this.amount = amount;
			}
		}
	}
}
