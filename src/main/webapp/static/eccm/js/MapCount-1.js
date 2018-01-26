require.config({
	paths:{
		echarts:'js/dist'
	}
});

var echarts;
var mapChart;
var map;

require(
	[
		'echarts',
		'echarts/chart/map'
	],
	requireCallback
);

map = document.getElementById('mapChart');

function requireCallback(ec){
	echarts = ec;
	mapChart = echarts.init(map);
	refresh();
}

function refresh(){
	if (mapChart && mapChart.dispose) {
		mapChart.dispose();
	}
	mapChart = echarts.init(map);
	var options = getoptions();
	mapChart.setOption(options,true);
}

function getoptions(){
	var cityMap = {
		"贵阳市": "520100",
		"六盘水市": "520200",
		"遵义市": "520300",
		"安顺市": "520400",
		"黔西南布依族苗族自治州": "522300",
		"黔东南苗族侗族自治州": "522600",
		"黔南布依族苗族自治州": "522700",
		"铜仁地区": "522200",
		"毕节地区": "522400"
	};

	var curIndx = 0;
	var mapType = [];

	for (var city in cityMap) {
		mapType.push(city);
		var geoJsonName = cityMap[city];
		var mapGeoData = require('echarts/util/mapData/params');

		// 自定义扩展图表类型
		mapGeoData.params[city] = {
			getGeoJson: (function (c) {
				var geoJsonName = cityMap[c];
				return function (callback) {
					$.getJSON('js/dist/china-main-city/'+geoJsonName+'.json',callback);
				}
			})(city)
		}
	}

	var ecConfig = require('echarts/config');

	mapChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
		var mt = param.target;
		var len = mapType.length;
		var f= false;

		for(var i=0;i<len;i++){
			if(mt == mapType[i]){
				f =true;
				mt=mapType[i];
			}
		}

		if(!f){
			mt='贵州';
		}

		//mt='贵阳市';
		option.series[0].mapType = mt;
		option.title.subtext = mt + ' （点击切换）';
		mapChart.setOption(option, true);
	});

	option = {
		title: {
			text : '渠道分布',
			subtext : '贵州'
		},
		tooltip : {
			trigger: 'item',
			formatter: '滚轮或点击切换<br/>{b}'
		},
		series : [
			{
				type: 'map',
				mapType: '贵州',
				selectedMode : 'single',
				itemStyle:{
					normal:{label:{show:true}},
					emphasis:{label:{show:true}}
				},
				data:[]
			}
		]
	};
	return option;
}
