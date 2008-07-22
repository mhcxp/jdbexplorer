package cn.com.qimingx.dbe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.com.qimingx.core.ProcessResult;
import cn.com.qimingx.dbe.DBConnectionState;
import cn.com.qimingx.dbe.TreeOperator;
import cn.com.qimingx.dbe.action.bean.TreeNodeBean;
import cn.com.qimingx.dbe.service.DBInfoService;

/**
 * @author Wangwei
 * 
 * 用于提供 DBTreePanel 上的功能Action
 */
@Controller("dbeTreeAction")
public class DbeTreeActionController extends AbstractDbeActionController {
	// logger
	private static final Log log = LogFactory
			.getLog(DbeTreeActionController.class);

	//
	private TreeOperator treeOperator;

	// Tree nodes，读取当前Node 的子Nodes
	public void tree(HttpServletRequest req, HttpServletResponse resp,
			TreeNodeBean param) {
		log.debug("call dbeTreeAction.tree,param:" + param);

		// check current login state
		ProcessResult<DBConnectionState> prDBCS = checkLogin(req);
		if (prDBCS.isFailing()) {
			log.error(prDBCS.getMessage());
			sendJSON(resp, prDBCS.toJSON());
			return;
		}

		// process
		DBInfoService service = prDBCS.getData().getDBInfoService();
		ProcessResult<JSON> pr = treeOperator.tree(service, param);
		if (pr.isSuccess()) {
			sendJSON(resp, pr.getData().toString());
		} else {
			sendJSON(resp, pr.toJSON());
		}
	}

	// 删除 TreeNode 代表的元素
	public void drop(HttpServletRequest req, HttpServletResponse resp,
			TreeNodeBean bean) {
		log.debug("call dbeTreeAction.Drop TableOrView..," + bean);

		// check current login state
		ProcessResult<DBConnectionState> prDBCS = checkLogin(req);
		if (prDBCS.isFailing()) {
			log.error(prDBCS.getMessage());
			sendJSON(resp, prDBCS.toJSON());
			return;
		}

		// process
		DBInfoService service = prDBCS.getData().getDBInfoService();
		ProcessResult<String> pr = treeOperator.drop(service, bean);
		if (pr.isSuccess()) {
			sendJSON(resp, pr.toJSON());
		} else {
			// TODO:此处应该修改成返回 JSON 表示错误标志,
			sendErrorJSON(resp, pr.toJSON());
		}
	}

	// 打开指定元素
	public void open(HttpServletRequest req, HttpServletResponse resp,
			TreeNodeBean param) {
		log.debug("call dbeTreeAction.open,param:" + param);

		// check current login state
		ProcessResult<DBConnectionState> prDBCS = checkLogin(req);
		if (prDBCS.isFailing()) {
			log.error(prDBCS.getMessage());
			sendJSON(resp, prDBCS.toJSON());
			return;
		}

		// process
		DBInfoService service = prDBCS.getData().getDBInfoService();
		ProcessResult<JSON> pr = treeOperator.open(service, param);
		if (pr.isSuccess()) {
			sendJSON(resp, pr.getData().toString());
		} else {
			sendJSON(resp, pr.toJSON());
		}
	}

	public TreeOperator getTreeNodeLoader() {
		return treeOperator;
	}

	@Autowired
	public void setTreeNodeLoader(TreeOperator treeOperator) {
		this.treeOperator = treeOperator;
	}
}
