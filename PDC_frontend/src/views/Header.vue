<template>
    <!-- Header -->
    <header id="header"
            style="z-index: 1000 !important;">
        <a href="/PDC/#/"
           class="logo flex-row"><strong>Poster Design</strong><span>Contest</span></a>
        <nav>
            <el-autocomplete style="width: 300px; margin-right: 30px;"
                             v-model="keyWord"
                             @keyup.enter="router.push({
                                 path: '/contests',
                                 query: {
                                     search: keyWord
                                 }
                             })"
                             :fetch-suggestions="querySearch"
                             placeholder="搜索"
                             clearable
                             value-key="name"
                             :trigger-on-focus="false"
                             onfocus="this.select()">
                <template #suffix>
                    <i class="fa fa-search"></i>
                </template>
            </el-autocomplete>
            <a v-if="!isLogin"
               @click="router.push('/auth')">登录</a>
            <a v-else
               @click="router.push('/user')">{{ user.nickname }}</a>
        </nav>
    </header>
</template>

<script setup lang='ts'>
import { inject, Ref, ref, onMounted } from 'vue'
import { useUserStore } from '../store/userStore'
import hRequest from '../utils/HRequest';
import { useRouter } from 'vue-router';

const router = useRouter()
const user = useUserStore()
const isLogin = inject<Ref<Boolean>>('isLogin') as Ref<Boolean>

const keyWord = ref<string>('')
const querySearch = async (queryString: string, cb: any) => {
    const params = new URLSearchParams()
    params.append('keyword', queryString)
    params.append('page_no', '1')
    params.append('page_size', '10')
    cb((await hRequest.get('/SearchContestServlet', { params: params })).data)
}

onMounted(async () => {
    try {
        const resp: Result = (await hRequest.post("user/GetUserDetailServlet"))
        if (resp.code == 1) {
            isLogin.value = true
            const { nickname, uid, username } = resp.data as UserDetail
            user.setUser(nickname, uid, username)
        }
    } catch (e) {
    }
})
</script>

<style scoped>
.flex-row {
    display: flex !important;
    align-items: center;
}
</style>