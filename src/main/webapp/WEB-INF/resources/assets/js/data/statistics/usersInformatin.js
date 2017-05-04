/**
 * Created by pikicast on 17/4/28.
 */


$(document).ready(function () {
    drawLineCharts();
    drawPieCharts();
});
//绘制图表
function drawLineCharts() {
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('lineCharts'));
    // myChart.showLoading({
    //     text : 'loading',
    //     effect : 'whirling',
    //     textStyle : {
    //         fontSize : 20
    //     }
    // });
    var option = {
        title : {
            text: 'Registered Users',
            x:'center',
            y:'top'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            orient : 'vertical',
            x : '100',
            data:['All Registered Users','Users Registered By SNS']
        },
        toolbox: {
            show : true,
            title : {
                text: 'Daily Register Status',
                x:'center'
            },
            feature : {
                mark : {show: true,
                    title : {
                    mark : 'Auxiliary line',
                    markUndo : 'Delete Last Auxiliary line',
                    markClear : 'Clear Auxiliary line'
                }},
                dataZoom : {
                    show : true,
                    title : {
                        dataZoom : 'Zoom In',
                        dataZoomReset : 'Restore Last Zoom In'
                    }
                },

                // magicType: {
                //     show : true,
                //     type: [
                //         'line',
                //         'bar',
                //         'stack',
                //         'tiled',
                //         'force',
                //         'chord',
                //         'pie',
                //         'funnel'],
                //     title : {
                //         line : '折线图切换',
                //         bar : '柱形图切换',
                //         stack : '堆积',
                //         tiled : '平铺',
                //         force: '力导向布局图切换',
                //         chord: '和弦图切换',
                //         pie: '饼图切换',
                //         funnel: '漏斗图切换'
                //     },
                //     option: {
                //         // line: {...},
                //         // bar: {...},
                //         // stack: {...},
                //         // tiled: {...},
                //         // force: {...},
                //         // chord: {...},
                //         // pie: {...},
                //         // funnel: {...}
                //     }
                // },
                restore : {
                    show : true,
                    title : 'Restore'
                },
                dataView : {
                    show : true,
                    title : 'Data View',
                    readOnly: false,
                    lang: ['dateview', 'close', 'refresh']
                },
                saveAsImage : {
                    show : true,
                    title : 'Save As Image',
                    type : 'png',
                    lang : ['Click To Save']
                }
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                axisTick : {    // 轴标记
                    show:true
                },
                axisLabel : {
                    show:true,
                    interval: 'auto',    // {number}
                    rotate: 45,
                    margin: 8,
                    textStyle: {
                        //color: 'blue',
                        //fontFamily: 'sans-serif',
                        fontSize: 12,
                        fontStyle: 'italic',
                        //fontWeight: 'bold'
                    }
                },
                // data : ['mon','tus','wen','thu','fri','sat','sun'],
                data : (function (){
                    var now = new Date();
                    var res = [];
                    var len = 30;
                    while (len--) {
                        res.unshift(now.toLocaleDateString());
                        now = new Date(now - 3600000*24);
                    }
                    return res;
                })()
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    show:true,
                    interval: 'auto',    // {number}
                    // rotate: -45,
                    // margin: 18,
                    formatter: '{value}',    // Template formatter!
                    textStyle: {
                        //color: '#1e90ff',
                        //fontFamily: 'verdana',
                        fontSize: 10,
                        fontStyle: 'normal',
                        fontWeight: 'bold'
                    }
                },
            }
        ],
        series : [
            {
                name:'All Registered Users',
                type:'line',
                smooth:true,
                // stack: '总量',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            },
            {
                name:'Users Registered By SNS',
                type:'line',
                smooth:true,
                // stack: '总量',
                data:[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
            }
        ]
    };
    myChart.setOption(option);

    //数据请求 得到结果 回调图表
    // setTimeout(function () {
    //     myChart.hideLoading();
    //     myChart.setOption(option);
    // },2000);
    blockObj($('#lineCharts'));
    var param = {
        q: encodeURI('stype=date')
    }
    $.ajax({
        method: "GET",
        url: "/v1.2/users/statics",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param,
        success: function (data, status, jqXHR) {
            var jsonString = "";
            for(var i = 0,len = data.userSatisticsResult.length; i < len;i++) {
                if(i<len-1)
                {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'",';

                }else {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'"';
                }
            };
            var ageRangeNums = JSON.parse(
                '{'+jsonString+'}'
            );
            for(var i = 0,len = option.series[0].data.length; i < len;i++) {
                {
                    option.series[0].data[i] = !!ageRangeNums[i+1]?ageRangeNums[i+1]:0;
                }
            }
            // for (var index in option.series[0].data)
            // {
            //     option.series[0].data[index] = !!ageRangeNums[index+1]?ageRangeNums[index+1]:0;
            // }
            myChart.setOption(option);
            $('#lineCharts').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            $('#lineCharts').unblock();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
    var param1 = {
        q: encodeURI('stype=date,isRegBySns=true')
    }
    $.ajax({
        method: "GET",
        url: "/v1.2/users/statics",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param1,
        success: function (data, status, jqXHR) {
            var jsonString = "";
            for(var i = 0,len = data.userSatisticsResult.length; i < len;i++) {
                if(i<len-1)
                {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'",';

                }else {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'"';
                }
            };
            var ageRangeNums = JSON.parse(
                '{'+jsonString+'}'
            );
            for(var i = 0,len = option.series[1].data.length; i < len;i++) {
                {
                    option.series[1].data[i] = !!ageRangeNums[i+1]?ageRangeNums[i+1]:0;
                }
            }
            // for (var index in option.series[0].data)
            // {
            //     option.series[0].data[index] = !!ageRangeNums[index+1]?ageRangeNums[index+1]:0;
            // }
            myChart.setOption(option);
            $('#lineCharts').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            $('#lineCharts').unblock();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
}

function drawPieCharts(){
    // 基于准备好的dom，初始化echarts图表
    var myChart1 = echarts.init(document.getElementById('pieCharts1'));
    var myChart2 = echarts.init(document.getElementById('pieCharts2'));
    // myChart1.showLoading({
    //     text : 'loading',
    //     effect : 'whirling',
    //     textStyle : {
    //         fontSize : 20
    //     }
    // });
    //
    // myChart2.showLoading({
    //     text : 'loading',
    //     effect : 'whirling',
    //     textStyle : {
    //         fontSize : 20
    //     }
    // });

    var option1 = {
        title : {
            text: 'Gender Range of Registered Users',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['male','female','unknow']
        },
        toolbox: {
            show : true,
            feature : {
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    title:{'pie':'pie','funnel':'funnel'},
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {
                    show : true,
                    title : 'Restore'
                },
                dataView : {
                    show : true,
                    title : 'Data View',
                    readOnly: false,
                    lang: ['dateview', 'close', 'refresh']
                },
                saveAsImage : {
                    show : true,
                    title : 'Save As Image',
                    type : 'png',
                    lang : ['Click To Save']
                }
            }
        },
        calculable : true,
        series : [
            {
                name:'Gender',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'male'},
                    {value:310, name:'female'},
                    {value:234, name:'unknow'}
                ]
            }
        ]
    };
    var option2 = {
        title : {
            text: 'Age Range of Registered Users',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : 'left',
            data:['unknow','<10','10s','20s','30s','40s','50s','60s','>60']
        },
        toolbox: {
            show : true,
            feature : {
                magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    title:{'pie':'pie','funnel':'funnel'},
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore : {
                    show : true,
                    title : 'Restore'
                },
                dataView : {
                    show : true,
                    title : 'Data View',
                    readOnly: false,
                    lang: ['dateview', 'close', 'refresh']
                },
                saveAsImage : {
                    show : true,
                    title : 'Save As Image',
                    type : 'png',
                    lang : ['Click To Save']
                }
            }
        },
        calculable : true,
        series : [
            {
                name:'Gender',
                type:'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:0, name:'unknow'},
                    {value:0, name:'<10'},
                    {value:0, name:'10s'},
                    {value:0, name:'20s'},
                    {value:0, name:'30s'},
                    {value:0, name:'40s'},
                    {value:0, name:'50s'},
                    {value:0, name:'60s'},
                    {value:0, name:'>60'}
                ]
            }
        ]
    };

    blockObj($('#pieCharts1'));
    var param1 = {
        q: encodeURI('stype=gender')
    }
    $.ajax({
        method: "GET",
        url: "/v1.2/users/statics",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param1,
        success: function (data, status, jqXHR) {
            var jsonString = "";
            for(var i = 0,len = data.userSatisticsResult.length; i < len;i++) {
                if(i<len-1)
                {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'",'

                }else {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'"'
                }
            }
            var ageRangeNums = JSON.parse(
                '{'+jsonString+'}'
            )
            option1.series[0].data[0].value = !!ageRangeNums.M?ageRangeNums.M:0;
            option1.series[0].data[1].value = !!ageRangeNums.F?ageRangeNums.F:0;
            option1.series[0].data[2].value = !!ageRangeNums.N?ageRangeNums.N:0;

            myChart1.setOption(option1);
            $('#pieCharts1').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            $('#pieCharts1').unblock();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
    blockObj($('#pieCharts2'));
    var param2 = {
        q: encodeURI('stype=age')
    }
    $.ajax({
        method: "GET",
        url: "/v1.2/users/statics",
        headers: {
            "Accept-Language": $.cookie("umsLanguage"),
            "X-ALLETS-LANG": $.cookie("umsLanguage").substring(0, 2),
            "X-ALLETS-COUNTRY": $.cookie("umsLanguage").substring(3)
        },
        data: param2,
        success: function (data, status, jqXHR) {
            var jsonString = "";
            for(var i = 0,len = data.userSatisticsResult.length; i < len;i++) {
                if(i<len-1)
                {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'",'

                }else {
                    jsonString = jsonString +'"'+data.userSatisticsResult[i].type+'":"'+(data.userSatisticsResult[i].result)+'"'
                }
             }
            var ageRangeNums = JSON.parse(
                '{'+jsonString+'}'
            )
            option2.series[0].data[0].value = !!ageRangeNums.s?ageRangeNums.s:0;
            option2.series[0].data[1].value = !!ageRangeNums['10s']?ageRangeNums['10s']:0;
            option2.series[0].data[2].value = !!ageRangeNums['20s']?ageRangeNums['20s']:0;
            option2.series[0].data[3].value = !!ageRangeNums['30s']?ageRangeNums['30s']:0;
            option2.series[0].data[4].value = !!ageRangeNums['40s']?ageRangeNums['40s']:0;
            option2.series[0].data[5].value = !!ageRangeNums['50s']?ageRangeNums['50s']:0;
            option2.series[0].data[6].value = !!ageRangeNums['60s']?ageRangeNums['60s']:0;
            option2.series[0].data[7].value = !!ageRangeNums['>60']?ageRangeNums['>60']:0;

            myChart2.setOption(option2);
            $('#pieCharts2').unblock();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            //TODO: handle other status
            $('#pieCharts2').unblock();
            if (XMLHttpRequest.status == "401") {
                window.location = "/login";
            }
        }
    });
    // setTimeout(function () {
    //     myChart1.hideLoading();
    //     myChart1.setOption(option1);
    // },2000)
    // setTimeout(function () {
    //     myChart2.hideLoading();
    //     myChart2.setOption(option2);
    // },3000)

}
