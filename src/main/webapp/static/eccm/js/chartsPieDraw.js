//标准饼图
function chartsPieDraw(pieDataParam, pieDataValues, pieElementId, pieText, pieSubtext, Radius, places, verticality, lr, tb, bgColor)
{

	var labelText = {
		normal : {
			label : {
				show : true,
				formatter : '{b}\n{d}%'
			}
		}
	};

	// 路径配置
	require.config({
		paths: {
			echarts: eccm+'/js/dist'
		}
	});

	// 使用
	require(
		[
			'echarts',
			'echarts/theme/macarons',  // 设置主题
			'echarts/chart/pie'   // 使用环形图，加载pie模块
		],
		function (ec) {
			// 基于准备好的dom，初始化echarts图表
			var chartPie = ec.init(document.getElementById(pieElementId));

			// 渠道饼状图相关配置
			optionPie = {
				backgroundColor: bgColor,
				title : {
					text: pieText,
					subtext: pieSubtext,
					x:'center',
					padding: '2'
				},
				tooltip : {
					trigger: 'item',
					formatter: "{b} : {c} ({d}%)"
				},
				legend: {
					orient : verticality,
					x: lr,
					y: tb,
					data: pieDataParam
				},
				calculable : true,
				series : [{
					type:'pie',
					roseType: 'area',
					radius: Radius,
					center: places,
					itemStyle : labelText,
					data: eval(pieDataValues)
				}]
			};

			// 为echarts对象加载数据
			chartPie.setOption(optionPie);
		}
	);
}
