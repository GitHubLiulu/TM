/**
 *  
 */
package com.ai.ecs.modules.tm.web;

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
import com.ai.ecs.modules.tm.entity.SysRemindInfo;
import com.ai.ecs.modules.tm.service.SysRemindInfoService;

/**
 * 周期定义Controller
 * @author liulu6
 * @version 2017-11-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tm/sysRemindInfo")
public class SysRemindInfoController extends BaseController {

	@Autowired
	private SysRemindInfoService sysRemindInfoService;
	
	@ModelAttribute
	public SysRemindInfo get(@RequestParam(required=false) String id) {
		SysRemindInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysRemindInfoService.get(id);
		}
		if (entity == null){
			entity = new SysRemindInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("tm:sysRemindInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysRemindInfo sysRemindInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysRemindInfo> page = sysRemindInfoService.findPage(new Page<SysRemindInfo>(request, response), sysRemindInfo); 
		model.addAttribute("page", page);
		return "modules/tm/sysRemindInfoList";
	}
	
	
//	@RequestMapping(value = {"queryRemindList", ""})
//	public List<SysRemindInfo> queryRemindList(SysRemindInfo sysRemindInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//		List<SysRemindInfo> list = sysRemindInfoService.findList(sysRemindInfo);
//		return list;
//	}
	

	@RequiresPermissions("tm:sysRemindInfo:view")
	@RequestMapping(value = "form")
	public String form(SysRemindInfo sysRemindInfo, Model model) {
		model.addAttribute("sysRemindInfo", sysRemindInfo);
		return "modules/tm/sysRemindInfoForm";
	}

	/**
	 * 批量更新跳转
	 * @param sysRemindInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("tm:sysRemindInfo:view")
	@RequestMapping(value = "batchForm")
	public String batchForm(SysRemindInfo sysRemindInfo, Model model) {
		model.addAttribute("sysRemindInfo", sysRemindInfo);
		return "modules/tm/sysBatRemindInfoForm";
	}

	@RequiresPermissions("tm:sysRemindInfo:edit")
	@RequestMapping(value = "save")
	public String save(SysRemindInfo sysRemindInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysRemindInfo)){
			return form(sysRemindInfo, model);
		}
		if (!"".equals(sysRemindInfo.getToolId()) && sysRemindInfo.getToolId() != null) {
			sysRemindInfo.setId(sysRemindInfo.getToolId());
		}
		sysRemindInfoService.save(sysRemindInfo);
		addMessage(redirectAttributes, "保存提醒周期定义成功");
		return "redirect:"+Global.getAdminPath()+"/tm/sysRemindInfo/?repage";
	}

	/**
	 * 批量更新
	 * @param sysRemindInfo
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("tm:sysRemindInfo:edit")
	@RequestMapping(value = "saveBatch")
	public String saveBatch(SysRemindInfo sysRemindInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysRemindInfo)){
			return form(sysRemindInfo, model);
		}
		sysRemindInfoService.updateBatch(sysRemindInfo);
		addMessage(redirectAttributes, "批量保存提醒周期定义成功");
		return "redirect:"+Global.getAdminPath()+"/tm/sysRemindInfo/?repage";
	}
	
	@RequiresPermissions("tm:sysRemindInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SysRemindInfo sysRemindInfo, RedirectAttributes redirectAttributes) {
		sysRemindInfoService.delete(sysRemindInfo);
		addMessage(redirectAttributes, "删除提醒周期定义成功");
		return "redirect:"+Global.getAdminPath()+"/tm/sysRemindInfo/?repage";
	}

}