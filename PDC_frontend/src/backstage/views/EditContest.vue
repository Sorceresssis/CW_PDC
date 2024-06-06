<template>
    <div>
        <button class="button"
                @click="router.back()"
                style="margin-bottom: 10px; width: 100px;"><span>返回</span></button>
        <div class="list">
            <el-form ref="editFormRef"
                     :model="editForm"
                     :rules="editFormRules"
                     label-width="120px"
                     label-position="right"
                     status-icon>
                <el-form-item label="比赛名称"
                              prop="name"
                              required>
                    <el-input v-model="editForm.name"
                              clearable
                              placeholder="输入比赛名称" />
                </el-form-item>
                <el-form-item label="年度"
                              prop="year"
                              required>
                    <el-input v-model="editForm.year"
                              clearable
                              :formatter="(value: string) =>/*年度4位数字*/ value.replace(/[^\d]/g, '').substring(0, 4)"
                              placeholder="输入年度" />
                </el-form-item>
                <el-form-item label="主题"
                              prop="topic"
                              required>
                    <el-input v-model="editForm.topic"
                              type="text"
                              clearable
                              placeholder="主题" />
                </el-form-item>
                <el-form-item label="介绍"
                              prop="intro"
                              required>
                    <el-input v-model="editForm.intro"
                              type="textarea"
                              :autosize="{ minRows: 4 }"
                              resize="none"
                              clearable
                              placeholder="介绍" />
                </el-form-item>
                <el-form-item label="比赛开始时间"
                              prop="starting_time"
                              required>
                    <el-date-picker v-model="editForm.starting_time"
                                    type="date"
                                    placeholder="开始时间"
                                    format="YYYY-MM-DD"
                                    value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="报名截至时间"
                              prop="registration_deadline"
                              required>
                    <el-date-picker v-model="editForm.registration_deadline"
                                    type="date"
                                    placeholder="截至时间"
                                    format="YYYY-MM-DD"
                                    value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="结果公布时间"
                              prop="result_announcement_time"
                              required>
                    <el-date-picker v-model="editForm.result_announcement_time"
                                    type="date"
                                    placeholder="比赛结束"
                                    format="YYYY-MM-DD"
                                    value-format="YYYY-MM-DD" />
                </el-form-item>
                <el-form-item label="分类"
                              prop="category_id"
                              required>
                    <el-select v-model="editForm.category_id"
                               class="m-2"
                               placeholder="选择分类"
                               size="large">
                        <el-option v-for="item in categoryList"
                                   :key="item.id"
                                   :label="item.name"
                                   :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item class="space-between">
                    <button class="button"
                            @click.prevent="submitForm(editFormRef)">
                        {{ formSubmitButton }}
                    </button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import hRequest from '../../utils/HRequest'
import { throttle } from '../../utils/throttle'
import { useRouter, useRoute } from 'vue-router'

// 路由工具
const router = useRouter()
const route = useRoute()

// 分类信息, 用作表单的select选择器
const categoryList = ref<Category[]>([{ id: 0, name: '选择分类' }])


// 表单提交按钮的文字
const formSubmitButton = ref<string>('')
// 表单实例
const editFormRef = ref<FormInstance>()
// 编辑书籍的表单数据
const editForm = reactive({
    name: '',
    year: '',
    topic: '',
    intro: '',
    starting_time: '',
    registration_deadline: '',
    result_announcement_time: '',
    category_id: 1
})
// 表单验证规则
const editFormRules = reactive<FormRules>({
    name: [
        { required: true, message: '请输入书名', trigger: 'blur' },
        { max: 100, message: '书名不能超过100个字符', trigger: 'blur' }
    ],
    authors: [
        { required: true, message: '请输入作者', trigger: 'blur' },
        { max: 100, message: '作者不能超过100个字符', trigger: 'blur' }
    ],
    year: [
        { required: true, message: '请输入年份', trigger: 'blur' }
    ],
    topic: [{ required: true, message: '请输入主题', trigger: 'blur' }],
    intro: [{ required: true, message: '请输入介绍', trigger: 'blur' }],
    starting_time: [{ required: true, message: '请输入开始时间', trigger: 'blur' }],
    registration_deadline: [
        { required: true, message: '请输入报名截至时间', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                editForm.starting_time < value ? callback() : callback('报名截至时间要大于开始时间')
            },
            trigger: 'blur'
        }
    ],
    result_announcement_time: [
        { required: true, message: '请输入结果公布时间', trigger: 'blur' },
        {
            validator: (rule, value, callback) => {
                editForm.registration_deadline < value ? callback() : callback('结果公布时间要大于报名截至时间')
            },
            trigger: 'blur'
        }
    ],
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
            formData.append('contest_id', route.query.id as string || '0')
            formData.append('name', editForm.name)
            formData.append('year', editForm.year)
            formData.append('topic', editForm.topic)
            formData.append('intro', editForm.intro)
            formData.append('starting_time', editForm.starting_time)
            formData.append('registration_deadline', editForm.registration_deadline)
            formData.append('result_announcement_time', editForm.result_announcement_time)
            formData.append('category_id', editForm.category_id.toString())
            // 提交表单
            const resp = (await hRequest.post('admin/EditContestServlet', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })) as Result
            if (resp.code === 1) {
                // 成功，提示并返回上一页
                ElMessage.success(resp.msg)
                setTimeout(() => router.go(-1), 1000)
            } else {
                ElMessage.error(resp.msg)
            }
        }
        else {
            ElMessage.error('请检查输入')
        }
    })
}, 500)


onMounted(async () => {
    // 如果是修改书籍，获取书籍信息
    if (route.query.id) {
        const data = (await hRequest.get(`/ContestDetailServlet?contest_id=${route.query.id}`)).data
        editForm.name = data.name
        editForm.year = data.year.substr(0, 4)
        editForm.topic = data.topic
        editForm.intro = data.intro
        editForm.starting_time = data.starting_time
        editForm.registration_deadline = data.registration_deadline
        editForm.result_announcement_time = data.result_announcement_time
        editForm.category_id = data.category_id
    }
    formSubmitButton.value = route.query.id ? '修改' : '添加'
    categoryList.value.push(...(await hRequest.get('CategoryServlet')).data)
})
</script>

<style scoped>
.list {
    flex: 1;
    padding: 0 20px;
}

.form-wrap {
    width: 400px;
    margin: 0 auto;
}
</style>