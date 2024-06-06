<template>
    <div>
        <button class="button"
                @click="router.back()"
                style="margin-bottom: 10px; width: 100px;"><span>返回</span></button>
        <div style="padding:0 20px;">
            <h2 style="font-size : 20px; ">{{ contestDetail?.name }}</h2>
            <div>
                <span class="label">年度</span>{{ contestDetail?.year.substring(0, 4) }}
                <span class="label">主题</span>{{ contestDetail?.topic }}
            </div>
            <div>
                开始时间：{{ contestDetail?.starting_time }}
                报名截至：{{ contestDetail?.registration_deadline }}
                结果公布：{{ contestDetail?.result_announcement_time }}
            </div>
        </div>
        <div class="list">
            <div v-for="ctw in ctws"
                 :key="ctw.work?.id"
                 class="work">
                <!-- work -->
                <h2>{{ ctw.work?.title }}</h2>
                <div>
                    <span class="label">作品介绍：</span>{{ ctw.work?.intro }}<br>
                    <div class="workImgs">
                        <div v-for="img in ctw.work?.imgs"
                             class="workImg-wrap">
                            <img class="img--contain"
                                 :src="imgBaseUrl + img"
                                 @error="($event.target as HTMLImageElement).src = '../../assets/images/nodata.png'">
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
                <div class="award">
                    <div>
                        <span class="label">获奖情况：</span>
                        <span class="award-item"
                              :class="{ 'award-item--actived': ctw.work!.award === 0 }"
                              @click="setAward(ctw.work!.id, 0)">未获奖</span>
                        <span class="award-item"
                              :class="{ 'award-item--actived': ctw.work!.award === 1 }"
                              @click="setAward(ctw.work!.id, 1)">一等奖</span>
                        <span class="award-item"
                              :class="{ 'award-item--actived': ctw.work!.award === 2 }"
                              @click="setAward(ctw.work!.id, 2)">二等奖</span>
                        <span class="award-item"
                              :class="{ 'award-item--actived': ctw.work!.award === 3 }"
                              @click="setAward(ctw.work!.id, 3)">三等奖</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import hRequest from '../../utils/HRequest';
import { ElMessage } from 'element-plus';
import { throttle } from '../../utils/throttle';

const router = useRouter();
const route = useRoute();
const imgBaseUrl = 'http://localhost:8080/PDC/workImg/'
const contestDetail = ref<ContestDetails>();
const ctws = ref<ContestTeamWork[]>([]);

const setAward = throttle(async (workId: number, award: number) => {
    const params = new URLSearchParams()
    params.append('work_id', workId.toString())
    params.append('award', award.toString())
    const res = await hRequest.post('admin/SetAwardServlet', params) as Result
    if (res.code === 1) {
        ElMessage.success('设置成功')
        for (let i: number = 0; i < ctws.value.length; i++) {
            if (ctws.value[i].work?.id === workId) {
                ctws.value[i].work!.award = award
                break
            }
        }
    }
    else {
        ElMessage.error(res.msg)
    }
}, 300)

onMounted(async () => {
    contestDetail.value = (await hRequest.get('/ContestDetailServlet', { params: { contest_id: route.query.id } })).data
    ctws.value = (await hRequest.get('admin/TeamWorksOfContestServlet', { params: { contest_id: route.query.id } })).data
}) 
</script>

<style scoped>
.label {
    margin-right: 0 10px;
}

.work {
    display: flex;
    flex-direction: column;
    margin: 20px;
    padding: 10px;
    background-color: #fff;
    box-shadow: 0 0 3px 2px #e8e7ea;
}

.work>h2 {
    margin-bottom: 10px;
    font-weight: 700;
    font-size: 20px;
}

.workImgs {
    display: flex;
    flex-wrap: wrap;
}

.workImg-wrap {
    width: 250px;
    margin: 10px;
}

.award {
    line-height: 34px;
    display: flex;
}

.award-item {
    margin: 20px;
}

.award-item--actived {
    background-color: #9e94f7;
}
</style>