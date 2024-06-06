<template>
    <div>
        <div v-for="ctw in ctws"
             class="team">
            <div style="display: flex;align-items: baseline;">
                <h3>{{ ctw.team?.name }}</h3>
                <div v-if="ctw.team?.captain.uid == user.uid">（您是队长）</div>
            </div>
            <div>
                队员：<span v-for="mem in ctw.team?.member">{{ mem.nickname }}</span>
            </div>
            <div class="flex-autoWidth--row"
                 style="justify-content: space-between;">参加的比赛：{{ ctw.contest?.name }} <button
                        @click="router.push(`/contestDetail?id=${ctw.contest?.id}`)">去比赛页</button> </div>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { ref, onMounted } from 'vue'
import hRequest from '../../utils/HRequest'
import { useRouter } from 'vue-router';
import { useUserStore } from '../../store/userStore';

const router = useRouter()
const user = useUserStore()
const ctws = ref<ContestTeamWork[]>([])


onMounted(async () => {
    ctws.value = (await hRequest.get('/user/UserAllTeamsServlet')).data;
})
</script>

<style scoped>
.team {
    border: 1px solid #ccc;
    padding: 10px;
    margin: 10px;
}
</style>