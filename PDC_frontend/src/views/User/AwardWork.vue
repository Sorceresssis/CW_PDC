<template>
    <div>
        <div v-for="ctw in ctws"
             :key="ctw.work?.id"
             class="work">
            <!-- work -->
            <h2>{{ ctw.contest?.name }}</h2>
            <div>
                <div class="work__title">
                    <h3>{{ ctw.work?.title }}</h3>
                    <h3>{{ ctw.work?.award == 1 ? '一等奖' : ctw.work?.award == 2 ? '二等奖' : '三等奖' }}</h3>
                </div>
                <span class="label">作品介绍：</span>{{ ctw.work?.intro }}<br>
                下载证书：<a :href="serverBaseUrl + `/DownloadAwardServlet?workId=${ctw.work?.id}`">下载</a>
                <div class="workImgs">
                    <div v-for="img in ctw.work?.imgs"
                         class="workImg-wrap">
                        <img class="img--contain"
                             :src="imgBaseUrl + img"
                             alt="图片">
                    </div>
                </div>
            </div>
            <!-- team -->
            <div>
                <div>
                    <span class="label">队伍名：</span> {{ ctw.team?.name }}
                    <span class="label">队长：</span>{{ ctw.team?.captain.nickname }}
                </div>
                <span class="label">队员：</span><span v-for="m in ctw.team?.member">{{ m.nickname }}</span>
            </div>
            <button class="button"
                    @click="router.push(`/contestDetail?id=${ctw.contest?.id}`)">比赛详情页面</button>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { ref, onMounted, inject } from 'vue'
import { useRouter } from 'vue-router'
import hRequest from '../../utils/HRequest'

const router = useRouter()

const ctws = ref<ContestTeamWork[]>([]);
const serverBaseUrl = inject<string>('serverBaseUrl') as string
const imgBaseUrl = serverBaseUrl + '/workImg/'


onMounted(async () => {
    ctws.value = (await hRequest.get('user/UserAwardWorksServlet')).data
})
</script>

<style scoped>
.work {
    margin: 20px;
    padding: 20px;
    border: 1px solid #ccc;
}

.work__title {
    display: flex;
}

.work__title>h3 {
    margin-right: 20px;
}

.workImgs {
    display: flex;
    flex-wrap: wrap;
}

.workImg-wrap {
    width: 300px;
    margin: 10px;
}
</style>