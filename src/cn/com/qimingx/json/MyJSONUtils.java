package cn.com.qimingx.json;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

public class MyJSONUtils {
	private static final MyJSONUtils self = new MyJSONUtils();

	// 生产JSON，并对指定属性进行排除
	public static JSON toJsonExclude(Object o, String... excludeProperty) {
		return self.jsonExclude(o, excludeProperty);
	}

	public static JSON toJsonInclude(Object o, String... includeProperty) {
		return self.jsonInclude(o, includeProperty);
	}

	// 按名称过滤属性
	private static class NamedPropertyFilter implements PropertyFilter {
		private String[] names;
		private boolean exclude = true;

		public void setExclude(boolean exclude) {
			this.exclude = exclude;
		}

		public NamedPropertyFilter(String[] names) {
			this.names = names;
		}

		public NamedPropertyFilter(String[] names, boolean exclude) {
			this.names = names;
			this.exclude = exclude;
		}

		public boolean apply(Object source, String property, Object value) {
			if (names == null || names.length < 1) {
				return exclude;
			}
			for (String name : names) {
				if (name.equals(property)) {
					return exclude;
				}
			}
			return !exclude;
		}
	}

	/**
	 * 转换对象到json
	 * 
	 * @param o
	 * @param excludeProperty,要排除的属性
	 * @return
	 */
	public JSON jsonExclude(Object o, String... excludeProperty) {
		JsonConfig config = new MyJsonConfig();
		// config.setExcludes(excludeProperty);
		NamedPropertyFilter filter = new NamedPropertyFilter(excludeProperty);
		// config.setJavaPropertyFilter(filter);
		config.setJsonPropertyFilter(filter);
		JSON json = JSONSerializer.toJSON(o, config);
		return json;
	}

	/**
	 * 转换对象到JOSN
	 * 
	 * @param o
	 * @param includeProperty,要包含的属性
	 * @return
	 */
	public JSON jsonInclude(Object o, String... includePropertys) {
		JsonConfig config = new MyJsonConfig();
		// config.setExcludes(excludeProperty);
		NamedPropertyFilter filter = new NamedPropertyFilter(includePropertys);
		filter.setExclude(false);
		// config.setJavaPropertyFilter(filter);
		config.setJsonPropertyFilter(filter);
		JSON json = JSONSerializer.toJSON(o, config);
		return json;
	}

}
