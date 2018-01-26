// 标准条形图
function chartsLinearBarDraw(barLegend, barDataParam, barElementId, barText, barSubtext)
{
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
			'echarts/chart/bar'  // 使用柱状图，加载bar模块
		],
		function (ec) {
			// 基于准备好的dom，初始化echarts图表
			var chartBar = ec.init(document.getElementById(barElementId));

			// KPI柱状图相关配置
			var zrColor = require('zrender/tool/color');
			var colorList = [ '#ff7f50','#87cefa','#da70d6','#32cd32','#6495ed', '#ff69b4','#ba55d3','#cd5c5c','#ffa500','#40e0d0','#6CA58B','#6CA571','#9CA56C','#A5946C','#C3D2CB'];
			var itemStyle = {
				normal: {
					color: function(params) {
						if (params.dataIndex < 0) {
							// for legend
							return zrColor.lift(
								colorList[colorList.length - 1],
								params.seriesIndex * 0.1
							);
						}
						else {
							// for bar
							return zrColor.lift(
								colorList[params.dataIndex],
								params.seriesIndex * 0.1
							);
						}
					},
					label: {
						show: true,
						position: 'right',
						formatter: '{c}'
					},
					barBorderRadius: 4
				},
				emphasis: {
					barBorderRadius: 4
				}
			};
			optionBar = {
				title : {
					text: barText,
					subtext: barSubtext
				},
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'false'
					},
					formatter: function(params) {
						// for text color
						var color = colorList[params[0].dataIndex];
						var res = '<div>';
						res += '<strong>' + params[0].name + '</strong>'
						for (var i = 0, l = params.length; i < l; i++) {
							res += '<br/>' + params[i].seriesName + params[i].value
						}
						res += '</div>';
						return res;
					}
				},
				calculable: true,
				grid: {
					x: 95,
					y: 50,
					y2: 20,
					x2: 20,
					borderWidth: 0
				},
				xAxis : [{
					type : 'value',
					splitLine: {
						show: true,
						lineStyle:{
							type: 'dashed'
						}
					}
				}],
				yAxis: [{
					type: 'category',
					axisLabel:{
						textStyle:{
							fontSize:'12',
							fontFamily:'Microsoft Yahei',
						},
						formatter:function(val) {
							if (!isNaN(val)) {
								 if (val >= 10000) {
								    	return val  / 10000 + "万"
								 }
							}
						    return val;
						}
					},
					splitLine: {
						show: false
					},
					data: barLegend
				}],
				series: [{
					type: 'bar',
					itemStyle: itemStyle,
					barWidth: 20,
					data: barDataParam
				}]
			};

			// 为echarts对象加载数据
			chartBar.setOption(optionBar);
		}
	);
}
