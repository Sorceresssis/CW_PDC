<template>
    <div>
        <div class="list thumbnail">
            <!-- 分类统计 -->
            <div ref="CategoryProportion"
                 class="chart"></div>
            <!-- 分类比例 -->
            <div ref="categoryCount"
                 class="chart"></div>
        </div>
    </div>
</template>
<script setup lang="ts">
import * as echarts from 'echarts';
import { onMounted, ref } from 'vue';
import hRequest from '../../utils/HRequest';

const CategoryProportion = ref<HTMLElement>();
let CategoryProportionOption = {
    title: {
        text: '分类比例',
        x: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{b}:{c}({d}%)'
    },
    legend: {
        type: 'scroll',
        orient: 'vertical',
        left: 0,
        top: 30,
        height: 150,
    },
    color: ['#61adf2', '#56dae8', '#efa49e', '#7ea1ed', '#5ae05a', '#f2d2a2', '#5881e8', '#63d6c0', '#edc29f', '#5b97d3', '#3eceb3', '#6a96ed', '#426ed1', '#65d18b'],
    series: [
        {
            name: '状态统计',
            type: 'pie',
            radius: '40%',
            center: ['50%', '50%'],
            data: null,
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0,0,0,0.5)'
                }
            },
            label: {
                show: true,
                position: 'outside',
                formatter: '{b}:{c}({d}%)'
            }
        }
    ]
}
const categoryCount = ref<HTMLElement>();
let categoryCountOption = {
    title: {
        text: '分类统计',
        x: 'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        data: ['比赛量',],
        orient: 'vertical',
        right: 60,
        top: 20
    },
    xAxis: {
        data: null
    },
    yAxis: {},
    color: ['#c38bef'],
    series: [
        {
            name: '比赛量',
            type: 'bar',
            data: null,
            barWidth: '20',
            label: {
                show: true,
                position: 'top'
            }
        }
    ]
}

type NameValue = {
    name: string,
    value: any
}

onMounted(async () => {
    const data = (await hRequest.get('/admin/ChartsServlet')).data
    // 图书状态统计
    CategoryProportionOption.series[0].data = data[0]

    // 图书分类统计
    categoryCountOption.xAxis.data = data[0].map((item: NameValue) => item.name)
    categoryCountOption.series[0].data = data[0].map((item: NameValue) => item.value)

    // 初始化全部图表
    echarts.init(CategoryProportion.value!).setOption(CategoryProportionOption)
    echarts.init(categoryCount.value!).setOption(categoryCountOption)
});
</script>

<style scoped>
.list {
    flex: 1;
}

.thumbnail {
    padding: 0 20px;
    grid-template-columns: repeat(auto-fill, 450px);
}

.chart {
    width: 450px;
    height: 400px;
}
</style>