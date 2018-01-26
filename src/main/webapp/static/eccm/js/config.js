var checkCount = 5;   //最多可以选择5个

//定义分页组件
var perPageNum=15;

function tab(nav,content,on,type)
{
	$(nav).children().bind(type,(function(){
		var $tab=$(this);
		var tab_index=$tab.prevAll().length;
		var $content = $(content).children();
		$(nav).children().removeClass(on);
		$tab.addClass(on);
		$content.hide();
		$content.eq(tab_index).show();
	}));
}

// 弹窗展示事件
function showPop(id){
	$('.addpop').hide();
	$('#'+id).show();
	$('.backMask').show();
	$('body').addClass('ovf_hide');
	if(id == "paidPop"){
		selectSubmit();
	}
}

$(function (){

	//页首导航
	$(".nav > li").hover(function(){
		$(this).find('.subNav').stop(true,true).slideDown(200);
	},function(){
		$(this).find('.subNav').stop(true,true).slideUp(200);
	});

	//公告、政策Tab切换
	tab(".Tab .TabHead",".Tab .TabNote","on","mousedown");

	//选择框多选事件
	$(document).on("click",".choose li",function(){
		var self=$(this);
		var parent_ul=$(this).parent("ul");
		var iTemp=parent_ul.children('.on').length;
		if(self.hasClass('on'))
		{
			self.removeClass('on');
		}
		else{
			if( iTemp == checkCount )
			{
				alert("您最多只可选择"+checkCount+"项！");
				return;
			}
			else{
				self.addClass('on');
			}
		}
	});

	//选择框单选
	$(document).on("click",".radio_choose li",function(){
		var self=$(this);
		var parent_ul=$(this).parent("ul");
		if (!self.hasClass('on')){
			self.addClass('on').siblings('li').removeClass('on');
		}
	});

	//弹窗关闭事件
	$(document).on('click','.addpopClose',function(){
		$(this).closest('.addpop').hide();
		$('.backMask').hide();
		$('body').removeClass('ovf_hide');
	});

	//弹窗关闭事件
	$(".jp-holder").jPages({
		containerID : "thelist",
		first: '首页',
		last: '尾页',
		previous: '上一页',
		next: '下一页',
		perPage: perPageNum,
		callback:function(page, items){
			$('.label_checkbox').removeClass('on').find("input").prop("check",false);
			if ($('.jp-last').hasClass('jp-disabled')) {
				$('#itemContainer').css('minHeight','auto');
			};
		}
	});

});
