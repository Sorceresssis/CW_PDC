<template>
    <div class="layout">
        <Header></Header>
        <div class="main-content">
            <Admin class="content-wrap"></Admin>
        </div>
        <Footer></Footer>
    </div>
</template>

<script setup lang='ts'>
import { shallowReactive } from 'vue'
import Header from './views/Header.vue'
import Footer from './views/Footer.vue'
import Admin from './views/Admin.vue';


const userMenu = shallowReactive<any[]>([
    { id: 1, title: '统计分析', path: '/admin' },
    { id: 2, title: '管理图书', path: '/admin/manageBooks' },
])

</script>
<style scoped>
.layout {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    overflow: hidden;
}

.main-content {
    flex: 1;
    padding: 20px 0;
    overflow: hidden;
    background-color: #f6f6f8;
}

.content-wrap {
    display: flex;
    overflow: hidden;
    height: 100%;
    justify-content: center;
    align-items: top;
    padding: 0 10%;
}
</style>