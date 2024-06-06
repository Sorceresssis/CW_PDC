<template>
    <!-- Banner -->
    <!-- Note: The "styleN" class below should match that of the header element. -->
    <section id="banner"
             class="style2">
        <div class="inner">
            <span class="image">
                <img src="../assets/images/pic07.jpg"
                     alt="" />
            </span>
            <header class="major">
                <h1>{{ handName }}</h1>
            </header>
            <div class="content">
                <p></p>
            </div>
        </div>
    </section>

    <!-- Main -->
    <div id="main">
        <section id="two"
                 class="spotlights">
            <section v-for="contest in contests"
                     :key="contest.id">
                <a class="image">
                    <img src="../assets/images/pic08.jpg"
                         alt=""
                         data-position="center center" />
                </a>
                <div class="content">
                    <div class="inner">
                        <header class="major">
                            <h3>{{ contest.name }}</h3>
                        </header>
                        <p> 年度：{{ contest.year.substring(0, 4) }} <br>
                            主题：{{ contest.topic }}
                        </p>
                        <p>报名开始：{{ contest.starting_time }}<br>
                            报名截至：{{ contest.registration_deadline }}<br>
                            结果公布：{{ contest.result_announcement_time }}</p>
                        <ul class="actions">
                            <li>
                                <a @click.prevent="router.push({
                                    path: '/contestDetail',
                                    query: {
                                        id: contest.id
                                    }
                                })"
                                   class="button">查看详情</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </section>
        </section>
        <div style="text-align: center; padding: 50px; background-color: #242943;">
            <button class="button"
                    @click.prevent="loadMoreContests()">加载更多</button>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import hRequest from '../utils/HRequest'
import { ElMessage } from 'element-plus';

const route = useRoute()
const router = useRouter()

const isSearch = ref<boolean>(false)
const handName = ref('')

const contests = ref<ContestProfile[]>([])
const pageSize = 5
// 闭包，保存页数，每次加载加一
let loadMoreContests = function () {
    let pageNo = 0
    return async (newpage?: number) => {
        if (newpage !== undefined) pageNo = newpage
        let params = new URLSearchParams()
        isSearch.value ? params.append('keyword', route.query.search as string) :
            params.append('category_id', route.query.category_id as string)
        params.append('page_no', `${++pageNo}`)
        params.append('page_size', `${pageSize}`)
        const newLoad: ContestProfile[] = (await hRequest.get((isSearch.value ? '/SearchContestServlet' : '/ContestServlet'), { params: params })).data
        if (newLoad.length === 0) {
            ElMessage.error('没有更多了')
            return
        }
        if (newpage !== undefined) contests.value = newLoad
        else contests.value.push(...newLoad)
    }
}()

watch(() => route.query.search, (newValue) => {
    handName.value = newValue as string
    loadMoreContests(0)
})

onMounted(async () => {
    // 判断是是分类查询还是搜索
    isSearch.value = route.query.search !== undefined
    handName.value = isSearch.value ? route.query.search :
        (await hRequest.get('/GetCategoryNameServlet', {
            params: { category_id: route.query.category_id }
        })).data.name

    loadMoreContests(0)
})
</script>

<style scoped></style>