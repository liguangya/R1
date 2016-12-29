package com.itheima.bos.domain;

import java.util.Date;

public class Msg {
		
		private  String  from;
		private  String  content;
		private  String  date;
		private String to;
		private Integer type;
		
		
		public String getTo() {
			return to;
		}
		public void setTo(String to) {
			this.to = to;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		
		
		
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public Msg() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
}
