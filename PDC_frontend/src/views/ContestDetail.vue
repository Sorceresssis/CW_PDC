<template>
    <!-- Main -->
    <div id="main"
         class="alt">

        <!-- One -->
        <section id="one">
            <div class="inner">
                <header class="major">
                    <h1 class="over-ellopsis"
                        style="max-width: 1200px;">{{ contestDetails?.name }}</h1>
                </header>
                <p> {{ contestDetails?.name }} <br>
                    年度：{{ contestDetails?.year.substring(0, 4) }} <br>
                    主题：{{ contestDetails?.topic }}
                </p>
                <p>{{ contestDetails?.intro }}</p>
                <p>
                    报名开始：{{ contestDetails?.starting_time }}<br>
                    报名截至：{{ contestDetails?.registration_deadline }}<br>
                    结果公布：{{ contestDetails?.result_announcement_time }}
                </p>
            </div>
        </section>
        <section id="two">
            <div v-if="contestDetails?.status == ContestStatus.PENDING"
                 class="inner">
                <header class="major">
                    <h2>比赛还未开始</h2>
                </header>
            </div>
            <div v-if="contestDetails?.status == ContestStatus.SIGNING"
                 class="inner">
                <header class="major">
                    <h2>报名中</h2>
                </header>
                <button v-if="createdTeam === undefined && joinedTeam.length === 0"
                        class="button"
                        @click="openInputTeamName">创建队伍</button>
                <el-dialog v-model="isVisibleInputTeamName"
                           align-center
                           title="输入队名"
                           width="400px"
                           class="dialog">
                    <div>
                        <div class="dialogCol">
                            <div class="col-title">输入队名</div>
                            <el-input v-model="teamName"></el-input>
                        </div>
                    </div>
                    <template #footer>
                        <span class="dialog-footer">
                            <el-button type="primary"
                                       @click="createTeam">
                                确定
                            </el-button>
                        </span>
                    </template>
                </el-dialog>
                <div v-if="createdTeam != undefined">
                    <h3>创建的队伍</h3>
                    <div>
                        <p style="display: flex;justify-content: space-between;">
                        <div>
                            <span class="label2">队伍名称：</span>
                            <el-input v-if="isRename"
                                      style="width: 300px;"
                                      v-model="createdTeam.team!.name"
                                      @keyup.enter="($event.target as HTMLInputElement)?.blur()"
                                      @blur="rename"></el-input>
                            <span v-else>{{ createdTeam.team?.name }}</span>
                            <el-button @click="isRename = true">重命名</el-button>
                            <span class="label2">成员：</span>
                            <span v-for="mem in createdTeam.team?.member"
                                  :key="mem.uid">{{ mem.nickname }} <span class="fa fa-close"
                                      @click="removeMember(mem.uid)"></span> </span>
                        </div>
                        <div>
                            <span class="label2">添加队员：</span>
                            <el-autocomplete v-model="memeUid"
                                             :fetch-suggestions="suggestionsOfUid"
                                             clearable
                                             :trigger-on-focus="false"
                                             class="inline-input w-50"
                                             placeholder="输入用户的uid"
                                             @select="handleSelect">
                                <template #default="{ item }">
                                    <div>
                                        <span class="link">{{ item.nickname }}</span>
                                        <div class="value">{{ item.uid }}</div>
                                    </div>
                                </template>
                            </el-autocomplete>
                            <el-button class="label2"
                                       @click="sendInvite(createdTeam.team?.id as number)">添加</el-button>
                        </div>
                        </p>
                        <h3>提交作品</h3>
                        <p>
                            <el-form ref="workFormRef"
                                     :model="workForm"
                                     :rules="workFormRules"
                                     label-width="120px"
                                     label-position="right"
                                     status-icon>
                                <el-form-item label="作品标题"
                                              prop="title"
                                              required>
                                    <el-input v-model="workForm.title"
                                              clearable
                                              placeholder="输入作品的标题" />
                                </el-form-item>
                                <el-form-item label="作品介绍"
                                              prop="intro"
                                              required>
                                    <el-input v-model="workForm.intro"
                                              type="textarea"
                                              :autosize="{ minRows: 4 }"
                                              resize="none"
                                              clearable
                                              placeholder="介绍" />
                                </el-form-item>
                                <el-form-item label="海报图片"
                                              prop="imgs">
                                    <el-upload v-model:file-list="imgList"
                                               :action="serverBaseUrl + '/UploadServlet'"
                                               list-type="picture-card"
                                               :on-success="handleUploadSuccess"
                                               :on-preview="handlePictureCardPreview"
                                               :on-remove="handleRemove">
                                        <el-icon>
                                            <Plus />
                                        </el-icon>
                                    </el-upload>
                                    <el-dialog v-model="dialogVisible">
                                        <img w-full
                                             :src="dialogImageUrl"
                                             alt="Preview Image" />
                                    </el-dialog>
                                </el-form-item>
                                <el-form-item class="space-between">
                                    <button class="button"
                                            @click.prevent="submitForm(workFormRef)">
                                        {{ workSubmitButton }}
                                    </button>
                                </el-form-item>
                            </el-form>
                        </p>
                    </div>
                </div>
                <div v-if="joinedTeam.length > 0">
                    <h3>我加入的队伍</h3>
                    <div v-for="team in joinedTeam"
                         :key="team.team?.id">
                        <p style="display: flex;justify-content: space-between;">
                        <div>
                            <span>{{ team.team?.name }}</span>
                            队长：<span>{{ team.team?.captain.nickname }}</span>
                            <span class="label2">成员：</span> <span v-for="mem in team.team?.member"
                                  :key="mem.uid">{{ mem.nickname }}</span>
                        </div>
                        </p>
                    </div>
                </div>
            </div>
            <div v-if="contestDetails?.status == ContestStatus.SIGNED"
                 class="inner">
                <header class="major">
                    <h2>评选中，请耐心等待</h2>
                </header>
            </div>
            <div v-if="contestDetails?.status == ContestStatus.FINISHED"
                 class="inner">
                <header class="major">
                    <h2>比赛结果</h2>
                </header>
                <div v-for="ctw in awardctws"
                     class="work"
                     :key="ctw.work?.id">
                    <h3>{{ ctw.work?.award == 1 ? '一等奖' : ctw.work?.award == 2 ? '二等奖' : '三等奖' }}:{{ ctw.work?.title }}</h3>
                    颁奖日期 {{ ctw.work?.awardTime }} <span>{{ ctw.work?.likes }}</span>
                    <div class="workImgs">
                        <div v-for="url in ctw.work?.imgs"
                             class="workImg-wrap">
                            <img class="img--contain"
                                 :src="imgBaseUrl + url"
                                 alt="图片">
                        </div>
                    </div>
                    <p>{{ ctw.work?.intro }} <br>
                        <span class="label">团队：</span> <span class="label">{{ ctw.team?.name }}</span><span
                              class="label">队长：</span>{{
                                  ctw.team?.captain.nickname }}
                        <br>
                        <span class="label">成员：</span> <span v-for="mem in ctw.team?.member"
                              :key="mem.uid">{{ mem.nickname }}</span>
                    </p>
                </div>
            </div>
        </section>
    </div>
</template>

<script setup lang='ts'>
import { ref, inject, onMounted, Ref, reactive } from 'vue'
import hRequest from '../utils/HRequest'
import { useRoute } from 'vue-router'
import router from '../router'
import { throttle } from '../utils/throttle'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules, UploadProps, UploadUserFile, UploadFile, UploadFiles, UploadRawFile } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'

const route = useRoute();
const serverBaseUrl = inject<string>('serverBaseUrl') as string
const imgBaseUrl = serverBaseUrl + '/workImg/'

const isLogin = inject<Ref<Boolean>>('isLogin') as Ref<Boolean>

const enum ContestStatus {
    PENDING = 1,
    SIGNING = 2,
    SIGNED = 3,
    FINISHED = 4,
}
// 比赛详情
const contestDetails = ref<ContestDetails>()


/* * * * *  比赛报名 * * * * */
const isVisibleInputTeamName = ref<boolean>(false)
const teamName = ref<string>('')
const openInputTeamName = () => {
    // 如果没有登陆跳转到登陆页
    if (!isLogin.value) {
        router.push('/auth')
        return
    }
    // 输入队伍名字。
    isVisibleInputTeamName.value = true
}
const createTeam = async () => {
    // 检查数据合法性
    if (teamName.value.trim() == '') {
        ElMessage.error('队伍名字不能为空')
        return
    }
    // 发送请求，创建队伍
    const params = new URLSearchParams()
    params.append('contest_id', route.query.id as string)
    params.append('team_name', teamName.value)
    const resp = await hRequest.post('/user/CreateTeamServlet', params) as Result
    if (resp.code == 1) {
        // 结果成功，刷新页面，加载队伍，隐藏输入框
        isVisibleInputTeamName.value = false
        location.reload()
    }
    else {
        // 失败，提示失败
        ElMessage.error(resp.msg)
    }
}
const isRename = ref<boolean>(false)
const rename = async () => {
    isRename.value = false
    let params = new URLSearchParams()
    params.append('team_id', createdTeam.value?.team?.id.toString() as string)
    params.append('team_name', createdTeam.value?.team?.name as string)
    if ((await hRequest.post('/user/RenameOfTeamServlet', params) as Result).code == 1) {
        ElMessage.success('修改成功')
    }
}

const removeMember = async (uid: number) => {
    if ((await hRequest.post('/user/RemoveMemberServlet',
        new URLSearchParams({ team_id: createdTeam.value?.team?.id.toString() as string, uid: uid.toString() })) as Result).code == 1) {
        createdTeam.value?.team?.member.splice(createdTeam.value?.team?.member.findIndex((mem) => mem.uid == uid), 1)
        ElMessage.success('移除成功')
    }
}
// 报名情况
const createdTeam = ref<ContestTeamWork | undefined>()
const joinedTeam = ref<ContestTeamWork[]>([])

// 邀请队友
const memeUid = ref<number>()
const suggestionsOfUid = async (queryString: string, cb: any) => {
    const params = new URLSearchParams()
    params.append('uid', queryString)
    params.append('page_no', '1')
    params.append('page_size', '10')
    cb((await hRequest.get('/AutoCompleteUserByUidServlet', { params: params })).data)
}
const handleSelect = (item: UserProfile) => {
    memeUid.value = item.uid
}
// 发送邀请
const sendInvite = async (teamId: number) => {
    if (memeUid.value == undefined) {
        ElMessage.error('请选择要邀请的队友')
        return
    }
    const params = new URLSearchParams()
    params.append('team_id', teamId.toString())
    params.append('uid', memeUid.value.toString())
    const resp = await hRequest.post('user/TeamInvitationServlet', params) as Result
    if (resp.code == 1) {
        ElMessage.success('邀请成功')
        location.reload()
    }
    else {
        ElMessage.error(resp.msg)
    }
}
// 表单提交按钮的文字
const workSubmitButton = ref<string>('')
// 表单实例
const workFormRef = ref<FormInstance>()
// 编辑书籍的表单数据
const workForm = reactive({
    work_id: 0,
    title: '',
    intro: '',
    imgs: [] as string[],
    delImg: [] as number[],
})
// 表单验证规则
const workFormRules = reactive<FormRules>({
    title: [
        { required: true, message: '请输入书名', trigger: 'blur' },
        { max: 100, message: '书名不能超过100个字符', trigger: 'blur' }
    ],
    intro: [{ required: true, message: '请输入介绍', trigger: 'blur' }],
    imgs: [{ required: true, message: '请上传图片', trigger: 'blur' }]
})
// 提交表单
const submitForm = throttle(async (formEl: FormInstance | undefined) => {
    // 如果没有传入表单实例，直接返回
    if (!formEl) return
    // 验证表单
    await formEl.validate(async (valid, fields) => {
        if (valid) {
            // 准备数据
            const formData = new FormData()
            formData.append('team_id', createdTeam.value?.team?.id.toString() as string)
            formData.append('work_id', workForm.work_id.toString())
            formData.append('title', workForm.title)
            formData.append('intro', workForm.intro)
            workForm.imgs.forEach(img => {
                formData.append('imgs', img)
            })
            workForm.delImg.forEach(del => {
                formData.append('del_imgs', del.toString())
            })
            // 提交表单
            const resp = (await hRequest.post('/user/EditWorkServlet', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })) as Result
            if (resp.code === 1) {
                ElMessage.success('提交成功')
                // 刷新页面
                location.reload()
            } else {
                ElMessage.error(resp.msg)
            }
        }
        else {
            ElMessage.error('请检查输入')
        }
    })
}, 500)

// 上传海报的相关数据
const imgList = ref<UploadUserFile[]>([
])
// 服务器响应后
const handleUploadSuccess = (response: any, uploadFile: UploadFile, uploadFiles: UploadFiles) => {
    if (response.code == 1) {
        ElMessage.success('上传成功')
        workForm.imgs.push(response.data)
        workForm.delImg.push(1)
    }
    else {
        ElMessage.error(response.msg)
        uploadFiles.pop()
    }
}
// 移除图片
const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
    workForm.imgs.push(uploadFile.url!.substring(uploadFile.url!.lastIndexOf('/')))
    workForm.delImg.push(0)
}
// 放大预览图片
const dialogImageUrl = ref('')
const dialogVisible = ref(false)
const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
    dialogVisible.value = true
}

/* * * * * 比赛结束 * * * * */
const awardctws = ref<ContestTeamWork[]>([])

onMounted(async () => {
    contestDetails.value = (await hRequest.get('/ContestDetailServlet',
        { params: { contest_id: route.query.id } })
    ).data
    // 根据比赛状态，请求不同的数据
    switch (Number.parseInt(contestDetails.value!.status as any)) {
        case ContestStatus.SIGNING:
            // 获取报名情况
            const data = (await hRequest.get('/user/UserTeamsOfContestServlet', { params: { contest_id: route.query.id } })).data as UserTeamOfContest
            if (data.created) {
                // 已经创建队伍
                createdTeam.value = data.created
                if (createdTeam.value.work) {
                    // 已经提交过作品,
                    workSubmitButton.value = '修改作品'
                    // 写入原始数据
                    const w = createdTeam.value.work
                    workForm.work_id = w.id
                    workForm.title = w.title
                    workForm.intro = w.intro
                    workForm.imgs = w.imgs
                    imgList.value = w.imgs.map((item) => {
                        return { name: item, url: imgBaseUrl + item }
                    })
                } else {
                    workSubmitButton.value = '提交作品'
                }
            } else {
                // 没有创建队伍，获取加入的队伍
                joinedTeam.value = data.joined as ContestTeamWork[]
            }
            break
        case ContestStatus.FINISHED:
            // 已经结束，请求获奖作品
            awardctws.value = (await hRequest.get('/ContestAwardWorksServlet', {
                params: { contest_id: route.query.id }
            })).data
            break
    }
})
</script>

<style scoped>
.workImgs {
    display: flex;
    flex-wrap: wrap;
}

.workImg-wrap {
    width: 300px;
    margin: 10px;
}

.label {
    margin-right: 10px;
}

.label2 {
    margin: 0 10px;
}

.my-autocomplete li {
    line-height: normal;
    padding: 7px;
}

.my-autocomplete li .name {
    text-overflow: ellipsis;
    overflow: hidden;
}

.my-autocomplete li .addr {
    font-size: 12px;
    color: #b4b4b4;
}

.my-autocomplete li .highlighted .addr {
    color: #ddd;
}
</style>