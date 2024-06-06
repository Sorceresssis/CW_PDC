<template>
    <div class="header">
        <div class="header-wrap">
            <nav class="nav">
                <a href="/admin/">
                    <div class="nav-list">
                        <div class="logo-wrap">
                            <img class="img--cover"
                                 src="/logo.png"
                                 alt="">
                        </div>
                        <div class="title">
                            PDC后台管理系统
                        </div>
                    </div>
                </a>
                <div class="nav-list">
                    <el-dropdown>
                        ADMIN
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click=" ">
                                    退出登陆
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
            </nav>
        </div>
    </div>
</template>

<script setup lang='ts'>
</script>

<style scoped>
.header {
    width: 100%;
    height: 64px;
}

.header-wrap {
    width: 100%;
    position: fixed;
    z-index: 1000;
    top: 0;
    background-color: #fff;
    box-shadow: 0 2px 4px #00000014;
}

.nav {
    height: 64px;
    padding: 0 5%;
    display: flex;
    justify-content: space-between;
}

.nav-list {
    display: flex;
    align-items: center;
}

.nav-list>div {
    margin: 0 10px;
}

.logo-wrap {
    width: 64px;
    height: 64px;
}

.title {
    font-size: 26px;
    font-weight: 200;
}

.search {
    width: 400px;
    margin-right: 40px !important;
}
</style>