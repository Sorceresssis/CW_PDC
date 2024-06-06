<template>
    <!-- Main -->
    <div id="main"
         class="alt">
        <!-- One -->
        <section id="one">
            <div class="inner">
                <header class="major">
                    <h1 class="over-ellopsis">个人中心</h1>
                </header>
                <p>
                    <button class="button"
                            @click="logout">退出登陆</button>
                </p>
            </div>
        </section>
        <section id="two">
            <div class="inner">
                <header class="menu flex-autoWidth--row">
                    <div class="menu-item"
                         :class="{ active: route.path == '/user' }"
                         @click="router.push('/user')">参加的比赛</div>
                    <div class="menu-item"
                         :class="{ active: route.path == '/user/awards' }"
                         @click="router.push('/user/awards')">获奖的比赛</div>
                </header>
                <router-view></router-view>
            </div>
        </section>
    </div>
</template>

<script setup lang='ts'>
import { inject, Ref } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import hRequest from '../../utils/HRequest'

const router = useRouter()
const route = useRoute()
const isLogin = inject<Ref<boolean>>('isLogin') as Ref<boolean>

const logout = () => {
    // 移除token
    localStorage.removeItem('jwtToken')
    // 移除服务器session
    hRequest.post('auth/LogoutServlet')
    isLogin.value = false
    // 跳转到登陆页面
    router.push('/auth')
}
</script>

<style scoped>
.menu {
    margin-bottom: 20px;
    font-size: 20px;
    font-weight: bold;
}

.menu-item {
    cursor: pointer;
    margin-right: 20px;
}

.active {
    color: #9bf1ff;
}
</style>