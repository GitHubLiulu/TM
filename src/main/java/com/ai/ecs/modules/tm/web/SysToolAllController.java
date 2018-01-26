/**
 *  
 */
package com.ai.ecs.modules.tm.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ai.ecs.common.config.Global;
import com.ai.ecs.common.persistence.Page;
import com.ai.ecs.common.web.BaseController;
import com.ai.ecs.common.utils.StringUtils;
import com.ai.ecs.modules.sys.entity.Dict;
import com.ai.ecs.modules.sys.service.DictService;
import com.ai.ecs.modules.tm.entity.SysToolAll;
import com.ai.ecs.modules.tm.entity.SysToolCheckRecord;
import com.ai.ecs.modules.tm.service.SysToolAllService;
import com.ai.ecs.modules.tm.service.SysToolCheckRecordService;

/**
 * 工具管理Controller
 * @author liulu6
 * @version 2017-11-10
 * 日期2018
 */
@Controller
@RequestMapping(value = "${adminPath}/tm/sysToolAll")
public class SysToolAllController extends BaseController {

	@Autowired
	private SysToolAllService sysToolAllService;
	
	@Autowired
	private SysToolCheckRecordService sysToolCheckRecordService;
	
	@Autowired
	private DictService dictService;

	/**
	 * ID用户于校验后跳转保存
	 */
	private String toolIdReturn;
	
	@ModelAttribute
	public SysToolAll get(@RequestParam(required=false) String id) {
		SysToolAll entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysToolAllService.get(id);
		}
		if (entity == null){
			entity = new SysToolAll();
		}
		return entity;
	}
	
	@RequiresPermissions("tm:sysToolAll:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysToolAll sysToolAll, HttpServletRequest request, HttpServletResponse response, Model model) {
		Dict dict = new Dict();
		dict.setType(Global.TOOL_SPECIFICATION);
		List<Dict> tool_specification = dictService.findList(dict);
		model.addAttribute("tool_specification", tool_specification);
		dict.setType(Global.TOOL_TYPE);
		List<Dict> tool_type = dictService.findList(dict);;
		model.addAttribute("tool_type", tool_type);
		sysToolAll.setCheckFlag("");
		Page<SysToolAll> page = sysToolAllService.findPage(new Page<SysToolAll>(request, response), sysToolAll); 
		model.addAttribute("page", page);
		return "modules/tm/sysToolAllList";
	}
	
	@RequiresPermissions("tm:sysToolAll:view")
	@RequestMapping(value = "checkList")
	public String checkList(SysToolAll sysToolAll, HttpServletRequest request, HttpServletResponse response, Model model) {
		Dict dict = new Dict();
		dict.setType(Global.TOOL_SPECIFICATION);
		List<Dict> tool_specification = dictService.findList(dict);
		model.addAttribute("tool_specification", tool_specification);
		dict.setType(Global.TOOL_TYPE);
		List<Dict> tool_type = dictService.findList(dict);;
		model.addAttribute("tool_type", tool_type);
		sysToolAll.setCheckFlag(Global.CHECK_NO);
		List<SysToolAll> page = sysToolAllService.findNeedCheckToolList(sysToolAll);
		model.addAttribute("page", page);
		return "modules/tm/sysToolAllCheckList";
	}
	
	@RequiresPermissions("tm:sysToolAll:view")
	@RequestMapping(value = "checkForm")
	public String checkForm(SysToolAll sysToolAll, Model model) {
		model.addAttribute("sysToolAll", sysToolAll);
		return "modules/tm/sysToolAllCheckForm";
	}
	

	@RequiresPermissions("tm:sysToolAll:view")
	@RequestMapping(value = "form")
	public String form(SysToolAll sysToolAll, Model model) {
		model.addAttribute("sysToolAll", sysToolAll);
		return "modules/tm/sysToolAllForm";
	}

	/**
	 * 校验完成后用于跳转
	 * @param sysToolAll
	 * @param model
	 * @return
	 */
	@RequiresPermissions("tm:sysToolAll:view")
	@RequestMapping(value = "checkFormReturn")
	public String checkFormReturn(SysToolAll sysToolAll, Model model, HttpServletRequest httpServletRequest) {
		//String toolId = httpServletRequest.getParameter("id");
		if (StringUtils.isNotBlank(toolIdReturn)){
			sysToolAll = sysToolAllService.get(toolIdReturn);
			sysToolAll.setOrigin(Global.CHECK_NO);
		}
		model.addAttribute("sysToolAll", sysToolAll);
		return "modules/tm/sysToolAllForm";
		//return "redirect:"+Global.getAdminPath()+"/tm/sysToolAll/checkList?repage";
	}

	@RequiresPermissions("tm:sysToolAll:edit")
	@RequestMapping(value = "save")
	public String save(SysToolAll sysToolAll, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysToolAll)){
			return form(sysToolAll, model);
		}
		sysToolAllService.save(sysToolAll);
		addMessage(redirectAttributes, "保存工具管理成功");
		return "redirect:"+Global.getAdminPath()+"/tm/sysToolAll/?repage";
	}
	
	@RequiresPermissions("tm:sysToolAll:edit")
	@RequestMapping(value = "checkSave")
	public String checkSave(SysToolAll sysToolAll, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysToolAll)){
			return form(sysToolAll, model);
		}
		if(!"".equals(sysToolAll.getCheckTimes())){
			int checkTimes= Integer.parseInt(sysToolAll.getCheckTimes());
			sysToolAll.setCheckTimes((checkTimes+1)+"");
		}
		sysToolAll.setPreCheckDate(new Date());
		sysToolAll.setCheckFlag(Global.CHECK_YES);
		sysToolAllService.save(sysToolAll);
		
		//更新提醒用户表SYS_TOOL_CHECK_RECORD
		SysToolCheckRecord sysToolCheckRecord = new SysToolCheckRecord();
		sysToolCheckRecord.setId(sysToolAll.getToolId());
		sysToolCheckRecord.setToolId(sysToolAll.getToolId());
		sysToolCheckRecord.setDelFlag(Global.CHECK_YES);
		sysToolCheckRecordService.save(sysToolCheckRecord);
		addMessage(redirectAttributes, "校验工具成功");
		//<td><a href="${ctx}/tm/sysToolAll/form?id=${sysToolAll.toolId}">
		toolIdReturn = sysToolAll.getToolId();
		//如果是循环校验，校验完成后跳转到确认下次校验周期，如果是非循环校验则不跳转
		if (Global.CHECK_NO.equals(sysToolAll.getIsCycle())) {
			return "redirect:"+Global.getAdminPath()+"/tm/sysToolAll/checkFormReturn";
		}else {
			return "redirect:"+Global.getAdminPath()+"/tm/sysToolAll/checkList?repage";
		}

	}
	
	@RequiresPermissions("tm:sysToolAll:edit")
	@RequestMapping(value = "delete")
	public String delete(SysToolAll sysToolAll, RedirectAttributes redirectAttributes) {
		sysToolAllService.delete(sysToolAll);
		addMessage(redirectAttributes, "删除工具管理成功");
		return "redirect:"+Global.getAdminPath()+"/tm/sysToolAll/?repage";
	}


}