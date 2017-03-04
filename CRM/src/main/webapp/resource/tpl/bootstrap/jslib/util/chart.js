 Highcharts.theme =
 { colors: ["#7cb5ec", "#f7a35c", "#90ee7e", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee", "#55BF3B", "#DF5353", "#7798BF", "#50B432"],
 chart: { backgroundColor: { linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 }, stops: [ [0, 'rgb(255, 255, 255)'], [1, 'rgb(240, 240, 255)'] ] },
 borderWidth: 0,bordercolor:'#50B432', plotBackgroundColor: 'rgba(255, 255, 255, .9)', plotShadow: true, plotBorderWidth: 1 }, 
title: { style: { color: '#000', font: 'bold 16px "Trebuchet MS", Verdana, sans-serif' } }, 
subtitle: { style: { color: '#666666', font: 'bold 12px "Trebuchet MS", Verdana, sans-serif' } },
 xAxis: { gridLineWidth: 1, lineColor: '#000', tickColor: '#000', labels: { style: { color: '#000', font: '11px Trebuchet MS, Verdana, sans-serif' } }, 
title: { style: { color: '#333', fontWeight: 'bold', fontSize: '12px', fontFamily: 'Trebuchet MS, Verdana, sans-serif' } } }, 
yAxis: { minorTickInterval: 'auto', lineColor: '#000', lineWidth: 1, tickWidth: 1, tickColor: '#000', 
labels: { style: { color: '#000', font: '11px Trebuchet MS, Verdana, sans-serif' } }, 
title: { style: { color: '#333', fontWeight: 'bold', fontSize: '12px', fontFamily: 'Trebuchet MS, Verdana, sans-serif' } } },
 legend: { itemStyle: { font: '9pt Trebuchet MS, Verdana, sans-serif', color: 'black' }, itemHoverStyle: { color: '#039' }, 
itemHiddenStyle: { color: 'gray' } }, labels: { style: { color: '#99b' } }, navigation: { buttonOptions: { theme: { stroke: '#CCCCCC' } } } };
 var highchartsOptions = Highcharts.setOptions(Highcharts.theme); 



var chart =
                                                { chart: {
                                                        reflow :true,
//                                                        width:$(this).parent().css("width"),
         type: 'column',
 zoomType: 'x'
        },  navigation : {
			buttonOptions : {
				enabled : true
			}
		},  credits: {enabled: false},
                                                    title: {
                                                        text: '',
                                                        x: -20 //center
                                                    },
                                                    subtitle: {
                                                        text: '',
                                                        x: -20
                                                    },
                                                    xAxis: {
                                                        
                                                        type: 'category',
                                                        
//                                                       
//                                                          labels: {                     formatter: function() {                         return categories[this.value];                     }}, 
                                                       
                                                         
                                                        
                                                    },
                                                     exporting:{
                   buttons:{contextButton: {
	                 menuItems: [
                              {
	                    text: '导出PNG',
	                    onclick: function () {
	                        this.exportChart({type: 'image/png'});
	                    }
	                 }
                         ]} },
             width: 1600,
             sourceWidth:1600,
            formAttributes: {"accept-charset":"utf-8"},
            url: "/LAA/app/report/exportPng/" 
                    },
                                                    yAxis: {
                                                        title: {
                                                            text: ''
                                                        },
                                                        plotLines: [{
                                                                value: 0,
                                                                width: 1,
                                                                color: '#808080'
                                                            }], min: 0
                                                    },
                                                    tooltip: {
                                                        valueSuffix: '',
                                                        
                                                    },
                                                    legend: {
                                                        layout: 'vertical',
                                                        align: 'right',
                                                        verticalAlign: 'middle',
                                                        borderWidth: 0
                                                    }, plotOptions: { column: { pointPadding:0.2, borderWidth: 1,minPointLength:50 ,dataLabels:{enabled:true,style:{"fontSize": "9px"
                                                                ,"fontWeight": ""},inside:true,rotation:90} } },
                                                    series: []
                                                };
 
  function maxWidth(data,chart){
      var columnCount = 0;
    if(data.length>0){
        for(var i=0;i<data.length;i++){
            var subCount=0;
            subCount = data[i].data.length;
            columnCount+=subCount;
        }
    }  
    if(0<=columnCount && columnCount<=10)
        chart.plotOptions.column.pointWidth=40;
    if(10<columnCount && columnCount<=20)
        chart.plotOptions.column.pointWidth=25;
    if(20<columnCount && columnCount<=30)
        chart.plotOptions.column.pointWidth=15;
    if(30<columnCount && columnCount<=40)
        chart.plotOptions.column.pointWidth=10;
    if(40<columnCount )
        chart.plotOptions.column.pointWidth=8;
  };  
  var chartpie ={ credits: {enabled: false
                                                        } ,chart: { plotBackgroundColor: null, plotBorderWidth: null, plotShadow: false },
                                                title: {text:'' }, exporting:{
                   buttons:{contextButton: {
	                 menuItems: [
                              {
	                    text: '导出PNG',
	                    onclick: function () {
	                        this.exportChart({type: 'image/png'});
	                    }
	                 }
                         ]} },
             width: 1600,
             sourceWidth:1600,
            formAttributes: {"accept-charset":"utf-8"},
            url: "/LAA/app/report/exportPng/" 
                    },
                                                tooltip: {
       formatter: function() {
            return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 2) +'% ';
         }                                          
      }, 
                                                plotOptions: { pie: { allowPointSelect: true, cursor: 'pointer', 
                                                        dataLabels: { enabled: true, color: '#000000', connectorColor: '#000000' ,formatter:function(){
                                                              return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.point.y,null,0) +'';   
                                                        }} } },
                                                series: [{ type: 'pie', name: '', 
                                                        data: [  ] }] }; 
                                                