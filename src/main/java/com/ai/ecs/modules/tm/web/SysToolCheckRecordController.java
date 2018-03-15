/**
 *  
 */
package com.ai.ecs.modules.tm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.ecs.common.persistence.BaseEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ai.ecs.common.config.Global;
import com.ai.ecs.common.persistence.Page;
import com.ai.ecs.common.web.BaseController;
import com.ai.ecs.common.utils.StringUtils;
import com.ai.ecs.modules.tm.entity.SysToolCheckRecord;
import com.ai.ecs.modules.tm.service.SysToolCheckRecordService;

/**
 * 检查数据记录Controller
 * @author liulu6
 * @version 2017-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tm/sysToolCheckRecord")
public class SysToolCheckRecordController extends BaseController {

	@Autowired
	private SysToolCheckRecordService sysToolCheckRecordService;
	
	@ModelAttribute
	public SysToolCheckRecord get(@RequestParam(required=false) String id) {
		SysToolCheckRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysToolCheckRecordService.get(id);
		}
		if (entity == null){
			entity = new SysToolCheckRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("tm:sysToolCheckRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysToolCheckRecord sysToolCheckRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysToolCheckRecord.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
		Page<SysToolCheckRecord> page = sysToolCheckRecordService.findPage(new Page<SysToolCheckRecord>(request, response), sysToolCheckRecord);
		model.addAttribute("page", page);
		//return "modules/tm/sysToolCheckRecordList";
		return "modules/tm/checkRecordList";
	}
	
	@RequiresPermissions("tm:sysToolCheckRecord:view")
	@ResponseBody
	@RequestMapping(value = {"queryRemindList", ""})
	public List<SysToolCheckRecord> queryRemindList(SysToolCheckRecord sysToolCheckRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		sysToolCheckRecord.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
		return sysToolCheckRecordService.findList(sysToolCheckRecord);
	}

//	@RequiresPermissions("tm:sysToolCheckRecord:view")
//	@ResponseBody
//	@RequestMapping(value = {"queryCheckList", ""})
//	public List<SysToolCheckRecord> queryCheckList(SysToolCheckRecord sysToolCheckRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
//		sysToolCheckRecord.setDelFlag(BaseEntity.DEL_FLAG_DELETE);
//		return sysToolCheckRecordService.findList(sysToolCheckRecord);
//	}


	@RequiresPermissions("tm:sysToolCheckRecord:view")
	@RequestMapping(value = "form")
	public String form(SysToolCheckRecord sysToolCheckRecord, Model model) {
		model.addAttribute("sysToolCheckRecord", sysToolCheckRecord);
		return "modules/tm/sysToolCheckRecordForm";
	}

	@RequiresPermissions("tm:sysToolCheckRecord:edit")
	@RequestMapping(value = "save")
	public String save(SysToolCheckRecord sysToolCheckRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysToolCheckRecord)){
			return form(sysToolCheckRecord, model);
		}
		sysToolCheckRecordService.save(sysToolCheckRecord);
		addMessage(redirectAttributes, "保存检查数据成功");
		return "redirect:"+Global.getAdminPath()+"/tm/sysToolCheckRecord/?repage";
	}
	
	@RequiresPermissions("tm:sysToolCheckRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(SysToolCheckRecord sysToolCheckRecord, RedirectAttributes redirectAttributes) {
		sysToolCheckRecordService.delete(sysToolCheckRecord);
		addMessage(redirectAttributes, "删除检查数据成功");
		return "redirect:"+Global.getAdminPath()+"/tm/sysToolCheckRecord/?repage";
	}

}