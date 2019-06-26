package com.pengheng.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 封装分页数据
 */

import javax.servlet.http.HttpServletRequest;

public class Page {
	private static final String PAGE_NO = "page";
	private static final String PAGE_SIZE = "limit";

	public static String DEFAULT_PAGESIZE = "10";
	private int pageNo; // 当前页码
	private int pageSize; // 每页行数
	private int totalRecord; // 总记录数
	private int totalPage; // 总页数
	private Map<String, String> params; // 查询条件
	private Map<String, List<String>> paramLists; // 数组查询条件
	private String searchUrl; // Url地址
	private String pageNoDisp; // 可以显示的页号(分隔符"|"，总页数变更时更新)

	private Collection dataCollection;

	private static ThreadLocal<Page> pageLocal = new ThreadLocal<Page>();

	private Page() {
		pageNo = 1;
		pageSize = Integer.valueOf(DEFAULT_PAGESIZE);
		totalRecord = 0;
		totalPage = 0;
		params = new HashMap();
		paramLists = new HashMap();
		searchUrl = "";
		pageNoDisp = "";
		pageLocal.set(this);
	}

	public static Page newBuilder(HttpServletRequest request) {
		Page page = new Page();
		Map map = request.getParameterMap();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			if (PAGE_NO.equals(key.toString())) {
				String[] pageNos = (String[]) map.get(PAGE_NO);
				if (pageNos != null && pageNos.length > 0) {
					page.setPageNo(Integer.valueOf(pageNos[0]));
				}
			} else if (PAGE_SIZE.equals(key.toString())) {
				String[] pageSizes = (String[]) map.get(PAGE_SIZE);
				if (pageSizes != null && pageSizes.length > 0) {
					page.setPageSize(Integer.valueOf(pageSizes[0]));
				}
			} else {
				Object value = map.get(key);
				if (value instanceof List) {
					page.paramLists.put(key.toString(), (List) value);
				} else if (value != null) {
					page.params.put(key.toString(), value.toString());
				}
			}
		}

		return page;
	}

	public static Page newBuilder(int pageNo, int pageSize) {
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		return page;
	}

	public static Page newBuilder(int pageNo, int pageSize, String url) {
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setSearchUrl(url);
		return page;
	}

	/**
	 * 生成查询map
	 * 
	 * @param request
	 * @return
	 */
	public static Map getSearchPageMap(HttpServletRequest request) {
		Map searchMap = new HashMap();
		Page page = pageLocal.get();
		if (page == null) {
			page = newBuilder(request);
		}
		searchMap.put("page", page);
		Map map = request.getParameterMap();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			if (PAGE_NO.equals(key.toString())) {
				String[] pageNos = (String[]) request.getParameterMap().get("page");
				if (pageNos != null && pageNos.length > 0) {
					page.setPageNo(Integer.valueOf(pageNos[0]));
				}
			} else if (PAGE_SIZE.equals(key.toString())) {
				String[] pageSizes = (String[]) request.getParameterMap().get("limit");
				if (pageSizes != null && pageSizes.length > 0) {
					page.setPageSize(Integer.valueOf(pageSizes[0]));
				}
			} else {
				Object value = map.get(key);
				if (value != null && value instanceof Object[] && ((Object[]) value).length == 1) {

					Object v = ((Object[]) value)[0];
					if (v instanceof String) {
						if ("".equals(v)) {
							continue;
						}
					}

					searchMap.put(key, ((Object[]) value)[0]);
				} else {
					searchMap.put(key, value);
				}

				if (value instanceof List) {
					page.paramLists.put(key.toString(), (List) value);
				} else if (value != null) {
					page.params.put(key.toString(), value.toString());
				}
			}
		}
		Map<String, String> userMap = (Map<String, String>) request.getSession().getAttribute("UserInfo");
		if (userMap != null && userMap.size() > 0) {
			String orgId = Toolkits.defaultString(userMap.get("orgId"));
			if (!"100083".equals(orgId)) {
                searchMap.put("orgId", userMap.get("orgId"));
            }
		}
		return searchMap;
	}

	public static Page getCurrentPage() {
		return pageLocal.get();
	}

	public Map<String, Object> getPageReturn() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", this.getTotalRecord());
		map.put("from", (this.getPageNo() - 1) * this.getPageSize());
		if (dataCollection != null) {
			map.put("to", (this.getPageNo() - 1) * this.getPageSize() + dataCollection.size());
			map.put("rows", dataCollection);
		}
		return map;
	}

	// 当期查询 中奖查询使用
	public Map<String, Object> getPageReturn(Map paramMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", this.getTotalRecord());
		map.put("from", (this.getPageNo() - 1) * this.getPageSize());
		if (dataCollection != null) {
			map.put("to", (this.getPageNo() - 1) * this.getPageSize() + dataCollection.size());
			map.put("rows", dataCollection);
		}
		map.put("drawNo", StringUtil.getObj(paramMap.get("drawNo")));
		map.put("totalMoney", StringUtil.getObj(paramMap.get("totalMoney")));
		map.put("totalInableMoney", StringUtil.getObj(paramMap.get("totalInableMoney")));
		map.put("aleradyCashMoney", StringUtil.getObj(paramMap.get("aleradyCashMoney")));
		map.put("outSideMoney", StringUtil.getObj(paramMap.get("outSideMoney")));
		map.put("sumMoney", StringUtil.getObj(paramMap.get("sumMoney")));
		map.put("sumNum", StringUtil.getObj(paramMap.get("sumNum")));
		// 客户签约信息下载使用
		map.put("onPageTotalMoney", StringUtil.getObj(paramMap.get("onPageTotalMoney")));
		map.put("onPageTotalNum", StringUtil.getObj(paramMap.get("onPageTotalNum")));
		map.put("result", "ok");
		return map;
	}

	public Map<String, Object> getPageReturn(Collection resultData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", this.getTotalRecord());
		map.put("from", (this.getPageNo() - 1) * this.getPageSize());
		if (resultData != null) {
			map.put("to", (this.getPageNo() - 1) * this.getPageSize() + resultData.size());
			map.put("rows", resultData);
		}
		map.put("result", "ok");
		return map;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		// refreshPage();
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, List<String>> getParamLists() {
		return paramLists;
	}

	public void setParamLists(Map<String, List<String>> paramLists) {
		this.paramLists = paramLists;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public String getPageNoDisp() {
		return pageNoDisp;
	}

	public void setPageNoDisp(String pageNoDisp) {
		this.pageNoDisp = pageNoDisp;
	}

	public Collection getDataCollection() {
		return dataCollection;
	}

	public void setDataCollection(Collection dataCollection) {
		this.dataCollection = dataCollection;
	}

}