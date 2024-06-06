<template>
    <div style="display: flex;flex: 1;">
        <div class="manageHeader">
            <el-select v-model="activeCategory"
                       class="m-2"
                       placeholder="选择分类"
                       size="large">
                <el-option v-for="item in categoryList"
                           :key="item.id"
                           :label="item.name"
                           :value="item.id" />
            </el-select>
            <button class="button"
                    @click="router.push('/edit')"><span>发布比赛</span></button>
        </div>
        <!-- 数据展示的子组件  -->
        <div class="flex-autoWidth--col"
             style="/* min-height的妙用重新计算高度 */min-height: 0;">
            <div v-if="contests.length == 0"
                 class="empty">
                没有找到比赛哦
            </div>
            <div v-else
                 class="list">
                <div v-for="contest in contests"
                     :key="contest.id"
                     class="contest-item">
                    <h2>{{ contest.name }}</h2>
                    <div class="contest-info flex-autoWidth--row">
                        <div>年度：{{ contest.year.substring(0, 4) }}</div>
                        <div>主题：{{ contest.topic }}</div>
                    </div>
                    <div class="flex-autoWidth--row"
                         style="justify-content: space-between;">
                        <div class="contest-info flex-autoWidth--row">
                            <div>开始时间：{{ contest.starting_time }}</div>
                            <div>截至时间：{{ contest.registration_deadline }}</div>
                            <div>结果公布：{{ contest.result_announcement_time }}</div>
                        </div>
                        <div>
                            <el-button type="danger"
                                       @click.prevent="openDeleteContest(contest.id)">删除</el-button>
                            <el-button @click="router.push(`/edit?id=${contest.id}`)">修改比赛</el-button>
                            <el-button @click="router.push(`/setAward?id=${contest.id}`)"
                                       :disabled="nowDate < contest.registration_deadline">颁发奖项</el-button>
                            <el-dialog v-model="isVisibleDeleteContest"
                                       align-center
                                       title="你确定要这样做吗"
                                       width="350px"
                                       class="dialog-confirm">
                                <p>
                                    此操作<span style="font-weight: 700;">无法</span>撤销。这将永久删除数据
                                </p>
                                <p>
                                    请输入<span style="font-weight: 700; user-select: text;">确认删除</span>
                                    进行确认。
                                </p>
                                <el-input v-model="confirmDeleteInput"></el-input>
                                <template #footer>
                                    <span class="dialog-footer">
                                        <el-button type="danger"
                                                   @click="deleteContest"
                                                   :disabled="'确认删除' !== confirmDeleteInput">
                                            我明白后果，确认删除
                                        </el-button>
                                    </span>
                                </template>
                            </el-dialog>
                        </div>
                    </div>
                </div>
                <div class="flex-autoWidth--row"
                     style="justify-content: center;">
                    <button class="button"
                            @click.prevent="loadMoreContests()">加载更多</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { ref, watch, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import hRequest from '../../utils/HRequest'
import { getNowFormatDate } from '../../utils/date'
import { ElMessage } from 'element-plus'

const router = useRouter();
const route = useRoute();

const activeCategory = ref<number>()   // 当前选中的分类
const categoryList = ref<Category[]>([{ id: 0, name: '全部' }]) // 分类信息

const contests = ref<ContestProfile[]>([])
const pageSize = 5
const nowDate = getNowFormatDate()


// 把分类信息写入路由
watch(activeCategory, (newvalue) => {
    if (newvalue === 0) router.push('/manage')
    else router.push({ path: '/manage', query: { category_id: newvalue } })
})
watch(() => [route.query], () => {
    loadMoreContests(0)
})

let loadMoreContests = function () {
    let pageNo = 0
    return async (newpage?: number) => {
        if (newpage !== undefined) pageNo = newpage
        let params = new URLSearchParams()
        params.append('category_id', route.query.category_id as string || '0')
        params.append('page_no', `${++pageNo}`)
        params.append('page_size', `${pageSize}`)
        const newLoad: ContestProfile[] = (await hRequest.get('/ContestServlet', { params: params })).data
        if (newLoad.length == 0) {
            ElMessage.error('没有更多了')
            return
        }
        // 如果新分类，就清空原有数据
        if (newpage !== undefined) contests.value = newLoad
        else contests.value.push(...newLoad)
    }
}()

const isVisibleDeleteContest = ref<boolean>(false)
const targetContestId = ref<number>(0)
const openDeleteContest = (id: number) => {
    isVisibleDeleteContest.value = true
    targetContestId.value = id
}
const confirmDeleteInput = ref<string>('')
const deleteContest = async () => {
    if (confirmDeleteInput.value !== '确认删除') {
        ElMessage.error('输入不正确')
        return
    }
    confirmDeleteInput.value = ''
    if ((await hRequest.post(`admin/RemoveContestServlet?contest_id=${targetContestId.value}`) as Result).code === 1) {
        ElMessage.success('删除成功')
        isVisibleDeleteContest.value = false
        loadMoreContests(0)
    } else {
        ElMessage.error('删除失败')
    }
}

onMounted(async () => {
    // 请求分类数据
    categoryList.value.push(...(await hRequest.get('/CategoryServlet')).data)
    // 读取url中的分类信息
    activeCategory.value = Number(route.query.category_id) || 0
    // 请求书籍数据
    loadMoreContests()
})
</script>

<style scoped>
.manageHeader {
    display: flex;
    height: 36px;
    padding: 0 20px;
    align-items: center;
    margin-bottom: 10px;
}

.manageHeader>* {
    margin: 0 20px;
}

.contest-item {
    display: flex;
    flex-direction: column;
    margin: 20px;
    padding: 10px;
    background-color: #fff;
    box-shadow: 0 0 3px 2px #e8e7ea;
}

.contest-item>h2 {
    margin-bottom: 10px;
    font-size: 20px;
}

.contest-info {
    font-size: 14px;
}

.contest-info>div {
    margin-right: 20px;
}
</style>