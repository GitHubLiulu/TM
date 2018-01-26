//堆积折线图
function chartsLineDraw(lineElementId, lineText, lineSubtext, lineTime, lineName, series,x)
{
	if(!x) {
		x = '50';
	}
	
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
			'echarts/theme/helianthus',  // 设置主题
			'echarts/chart/line'   // 使用折线图，加载line模块
		],
		function (ec,theme) {
			// 基于准备好的dom，初始化echarts图表
			var chartLine = ec.init(document.getElementById(lineElementId),theme);

			// 渠道饼状图相关配置
			optionLine = {
				title : {
					text: lineText,
					subtext: lineSubtext
				},
				tooltip: {
					trigger: 'axis'
				},
				legend: {
					data: lineName
				},
				calculable: true,
				grid: {
					borderWidth: 0,
					x: x,
					y: 65,
					y2: 40,
					x2: 30,
				},
				xAxis: [{
					type : 'category',
					boundaryGap : false,
					axisLabel:{
						textStyle:{
							fontSize:'14',
							fontFamily:'Microsoft Yahei',
						},
						formatter:function(val) {
							if (!isNaN(val)) {
								 if (val > 10000) {
								    	return val  / 10000 + "万"
								 }
							}
						    return val;
						}
					},
					splitLine: {
						show: true,
						lineStyle:{
							color: '#FFF',
							type: 'dashed'
						}
					},
					data: lineTime
				}],
				yAxis : [{
					type : 'value',
					axisLabel:{
						formatter:function(val) {
							if (!isNaN(val)) {
								 if (val >= 10000) {
								    	return val  / 10000 + "万"
								 }
							}
						    return val;
						}
					}
				}],
				series: series
		};

			// 为echarts对象加载数据
			chartLine.setOption(optionLine);
		}
	);
}
