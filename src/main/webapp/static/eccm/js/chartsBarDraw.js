// 标准柱状图
function chartsBarDraw(barLegend, barDataParam, barElementId, barText, barSubtext)
{
	var iRotate = 0;
	var iLength = 0;
	var y2Length = 20;
	var fontSize = 14;
	for(var i = 0; i < barLegend.length; i++) {
		var text = barLegend[i];
		if (text && isNaN(text)) {  // 是文字类型时
			if (text.length > iLength) {
				iLength = text.length;  // 找出最长的文字
			}
		}
	}
	
	if(iLength <= 4) {
		fontSize = 14;
		iRotate = 0;
		y2Length = 20;
	} else if (iLength > 4 && iLength <= 8) {
		fontSize = 12;
		iRotate = 20;
		y2Length = 20;
	} else if (iLength > 8 && iLength <= 12) {
		fontSize = 12;
		iRotate = 20;
		y2Length = 60;
	} else if (iLength > 12) {
		fontSize = 12;
		iRotate = 20;
		y2Length = 80;
	} else {
		fontSize = 14;
		iRotate = 0;
		y2Length = 20;
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
			'echarts/theme/macarons',  // 设置主题
			'echarts/chart/bar'  // 使用柱状图，加载bar模块
		],
		function (ec) {
			// 基于准备好的dom，初始化echarts图表
			var chartBar = ec.init(document.getElementById(barElementId));

			// KPI柱状图相关配置
			var zrColor = require('zrender/tool/color');
			var colorList = [ '#32cd32','#da70d6','#ff7f50','#6495ed', '#ff69b4','#87cefa','#ba55d3','#cd5c5c','#ffa500','#40e0d0'];
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
						position: 'top',
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
					showDelay : 2, // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
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
					x: 50,
					y: 70,
					y2: y2Length,
					x2: 10,
					borderWidth: 0
				},
				xAxis: [{
					type: 'category',
					axisLabel:{
						interval:0,
						rotate:iRotate,
						textStyle:{
							fontSize: ''+ fontSize,
							fontFamily:'Microsoft Yahei',
						}
					},
					splitLine: {
						show: false
					},
					data: barLegend
				}],
				grid: { // 控制图的大小，调整下面这些值就可以，
		             x: 100,
		             x2: 60,
		             y2: 100,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
		         },
				yAxis : [{
					type : 'value',
					splitLine: {
						show: true,
						lineStyle:{
							type: 'dashed'
						}
					},
					axisLabel : {
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
				series: [{
					type: 'bar',
					itemStyle: itemStyle,
					barWidth: 24,
					data: barDataParam
				}]
			};

			// 为echarts对象加载数据
			chartBar.setOption(optionBar);
		}
	);
}
