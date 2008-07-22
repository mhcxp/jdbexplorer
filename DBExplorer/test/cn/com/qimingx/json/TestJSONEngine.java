package cn.com.qimingx.json;

import java.util.Date;

import net.sf.json.JSON;

public class TestJSONEngine {
	public static void main(String[] args) {
		// Date date = new Date();
		// JSONObject json = new JSONObject();
		// json.element("date", date);
		// System.out.println(json.toString());

		MyObject obj = new MyObject();
		obj.setAge(28);
		obj.setBirthday(new Date());
		obj.setId(1l);
		obj.setName("Wangwei");
		JSON json = MyJSONUtils.toJsonExclude(obj, "birthday", "id", "age");
		System.out.println("exclude:" + json.toString());

		json = MyJSONUtils.toJsonInclude(obj, "birthday", "age");
		System.out.println("include:" + json.toString());
	}

	public static class MyObject {
		private Long id;
		private String name;
		private Integer age;
		private Date birthday;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Date getBirthday() {
			return birthday;
		}

		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}
	}
}
