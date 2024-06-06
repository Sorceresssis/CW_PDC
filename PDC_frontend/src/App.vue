<template>
	<div>
		<div id="wrapper">
			<Header></Header>
			<router-view></router-view>
			<Footer></Footer>
		</div>
	</div>
</template>

<script setup lang='ts'>
import { ref, provide } from 'vue'
import Header from './views/Header.vue'
import Footer from './views/Footer.vue'

const serverBaseUrl = 'http://localhost:8080/PDC'
provide('serverBaseUrl', serverBaseUrl)
const isLogin = ref<Boolean>(false)
provide('isLogin', isLogin)
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