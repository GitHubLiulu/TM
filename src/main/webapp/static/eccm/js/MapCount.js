
//地图数据
var mapData=['遵义市','黔东南苗族侗族自治州','毕节地区','黔南布依族苗族自治州','铜仁地区','黔西南布依族苗族自治州','六盘水市','安顺市','贵阳市'];
var mapDataNum=['503','814','84','476','260','814','476','260','814'];

function chartsMapDraw(mapDataParam, mapElementId)
{
	// 路径配置
	require.config({
		paths: {
			echarts: eccm+'/js/dist'
		}
	});

	var echarts;
	var mapChart;
	var map;

	// 使用
	require(
		[
			'echarts',
			'echarts/chart/map'  // 使用地图，加载map模块
		],
		function (ec) {
			// 基于准备好的dom，初始化echarts图表
			var chartMap = ec.init(document.getElementById(mapElementId));

			// 地图相关数据配置
			optionMap = {
				title : {
					text: '渠道分布',
					subtext: '贵州省'
				},
				tooltip : {
					trigger: 'item'
				},
				dataRange: {
					min: 0,
					max: 2000,
					x: 'left',
					y: 'bottom',
					text:['高','低'],
					calculable : true
				},
				series : [{
					tooltip: {
						trigger: 'item',
						formatter: '{b} : {c}'
					},
					type: 'map',
					mapType: '贵州',
					selectedMode : 'single',
					itemStyle:{
						normal:{label:{show:true}},
						emphasis:{label:{show:true}}
					},
					data:[
						{name: mapData[0], value: mapDataNum[0]},
						{name: mapData[1], value: mapDataNum[1]},
						{name: mapData[2], value: mapDataNum[2]},
						{name: mapData[3], value: mapDataNum[3]},
						{name: mapData[4], value: mapDataNum[4]},
						{name: mapData[5], value: mapDataNum[5]},
						{name: mapData[6], value: mapDataNum[6]},
						{name: mapData[7], value: mapDataNum[7]},
						{name: mapData[8], value: mapDataNum[8]}
					]
				}]
			};

			// 为echarts对象加载数据
			chartMap.setOption(optionMap);

		}
	);
}

$(function() {

	chartsMapDraw(mapData, 'contrast_map');

});
