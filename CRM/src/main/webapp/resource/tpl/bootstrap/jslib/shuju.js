 $(document).ready(function () {
               satart();
            var $terms = $('#charts-body>div');
            if ($terms.length == 1) {
                $('.itemdevCharts').css('width', '100%');
            }
            else if ($terms.length == 2) {
                $('.itemdevCharts').css('width', '50%');
            } else if ($terms.length == 3) {
                $('.itemdevCharts').css('width', '33%');
            } else {
                $('.itemdevCharts').css('width', '25%');
            }
            ;
        });
   
  
                function selDataselect_ZK() {
                    var selectvar = document.getElementById("selDatabase").value;
                    var yixuanss = document.getElementById("yixuans").value;
                    if (yixuanss == '') {
                        document.getElementById("yixuans").value = selectvar;
                    } else {
                        document.getElementById("yixuans").value = yixuanss + "," + selectvar;
                    }

                }
                function shenhenext() {

                    layer.open({
                        type: 2,
                        title: '教师审核',
                        area: ['500px', '500px'],
                        shade: 0.8,
                        content: ['../audit/shenhe.html', 'no']
                    });
                }
       
       
        function satart() {
        require.config({
            paths: {
                echarts: '${RESOURCE}/bootstrap/jslib'
            }
        });
        require(
                [
                    'echarts',
                    'echarts/chart/bar',
                    'echarts/chart/line',
                    'echarts/chart/venn'
                ],
                function (ec) {
                    Optionss.title.text = "数据对比VS数据对比数据对比";
                    Optionss.title.x = 'center';
                    Optionss.title.padding = [1, 0, 10, 0];
                    Optionss.title.borderColor = '#996699';
                    Optionss.series[0].data = [
                        {value: 100, name: '访问'},
                        {value: 50, name: '查询'},
                        {value: 20, name: '公共'}
                    ];
                    var myCharts = ec.init(document.getElementById('itemdevCharts'));
                    myCharts.setOption(Optionss);
                           var myChartss = ec.init(document.getElementById('itemdevChartss'));
                           myChartss.setOption(Optionss);
                           var myChartsss = ec.init(document.getElementById('itemdevChartsss'));
                           myChartsss.setOption(Optionss);
                           var myChartssss = ec.init(document.getElementById('itemdevChartssss'));
                           myChartssss.setOption(Optionss);

                }
        );
    }