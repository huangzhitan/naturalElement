    var   Optionss={
     title : {
        show : true,
        text: '访问 vs 咨询',
        textStyle: { color: '#333', fontWeight: 'bold', fontSize: '16', fontFamily: '微软雅黑' } 
    },
    tooltip : {
        trigger: 'item',
        formatter: "{b}: {c}"
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: false},
            dataView : {show: false, readOnly: true},
            restore : {show: false},
            saveAsImage : {show: false}
        }
    },
    calculable : false,
    series : [
                {
            name:'韦恩图',
            type:'venn',
            itemStyle: {
                normal: {
                    label: {
                        show: false,
                        textStyle: {
                            fontFamily: '微软雅黑',
                            fontSize: 16,
                            fontStyle: 'italic',
                            fontWeight: 'bolder'
                        }
                    },
                    labelLine: {
                        show: false,
                        length: 10,
                        lineStyle: {
                            // color: 各异,
                            width: 1,
                            type: 'solid'
                        }
                    }
                },
                emphasis: {
                    color: '#cc99cc',
                    borderWidth: 3,
                    borderColor: '#996699'
                }
            },
            
            data:[]
        }
    ]           
        } ; 

