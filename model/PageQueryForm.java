package com.bench.app.ch.user.wap.base.core.model.api;

import com.bench.common.doc.Doc;
import com.bench.common.lang.ToStringObject;

/**
 * 分页查询表单
 * 
 * @author chenbug
 *
 * @version $Id: PageQueryForm.java, v 0.1 2018年4月16日 下午12:48:20 chenbug Exp $
 */
public class PageQueryForm extends ToStringObject {
	/**
	 * 当前页码
	 */
	@Doc("当前页码")
	protected int currentPage = 1;

	/**
	 * 每页数量
	 */
	@Doc("每页数量")
	protected int pageSize = 10;

	/**
	 * 每页最大记录数80
	 */
	@Doc("每页最大记录数80")
	protected int maxPageSize = 80;

	public int getCurrentPage() {
		return currentPage <= 0 ? 1 : currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize > maxPageSize ? maxPageSize : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
