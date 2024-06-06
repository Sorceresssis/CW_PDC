<template>
    <div id="main"
         class="alt">
        <!-- One -->
        <section id="one">
            <div class="inner">
                <div v-if="isLogin"
                     class="form-wrap">
                    <header class="major">
                        <h1>Sign in</h1>
                    </header>
                    <el-form ref="loginFormRef"
                             :model="loginForm"
                             :rules="loginFormRules"
                             label-width="120px"
                             label-position="top"
                             status-icon>
                        <el-form-item label="用户名或号码"
                                      prop="username"
                                      required>
                            <el-input v-model="loginForm.username"
                                      clearable
                                      placeholder="用户名/号码" />
                        </el-form-item>
                        <el-form-item label="密码"
                                      prop="pwd"
                                      required>
                            <el-input v-model="loginForm.pwd"
                                      type="password"
                                      clearable
                                      show-password
                                      placeholder="输入密码" />
                        </el-form-item>
                        <el-form-item label="验证码"
                                      prop="captcha"
                                      required>
                            <el-col :span="6">
                                <img ref="captchaRef"
                                     :src="captchaUrl"
                                     alt="验证码"
                                     @click="refreshCaptcha($event)"
                                     style="width: 100px ; height: 36px;">
                            </el-col>
                            <el-col :span="18">
                                <el-input v-model="loginForm.captcha"
                                          type="text"
                                          clearable
                                          placeholder="验证码" />
                            </el-col>
                        </el-form-item>
                        <el-form-item class="space-between">
                            <el-col :span="6">
                                <button class="button"
                                        @click.prevent="submitLoginForm(loginFormRef)">登录</button>
                            </el-col>
                            <el-col :span="18"
                                    style="text-align:right;">
                                <a class="link"
                                   @click.prevent="isLogin = false">没有账号？去注册</a>
                            </el-col>
                        </el-form-item>
                    </el-form>
                </div>
                <div v-else
                     class="form-wrap ">
                    <header class="major">
                        <h1>Sign up</h1>
                    </header>
                    <el-form ref="regFormRef"
                             :model="regForm"
                             :rules="regFormRules"
                             label-width="120px"
                             label-position="top"
                             class="demo-ruleForm"
                             status-icon>
                        <el-form-item label="用户名"
                                      prop="username"
                                      required>
                            <el-input v-model="regForm.username"
                                      clearable
                                      placeholder="输入用户名" />
                        </el-form-item>
                        <el-form-item label="密码"
                                      prop="pwd"
                                      required>
                            <el-input v-model="regForm.pwd"
                                      type="password"
                                      clearable
                                      show-password
                                      placeholder="输入密码" />
                        </el-form-item>
                        <el-form-item label="确认密码"
                                      prop="pwdConfirm"
                                      required>
                            <el-input v-model="regForm.pwdConfirm"
                                      type="password"
                                      clearable
                                      show-password
                                      placeholder="确认密码" />
                        </el-form-item>
                        <el-form-item label="号码"
                                      prop="phone">
                            <el-input v-model="regForm.phone"
                                      clearable
                                      placeholder="号码"
                                      type="number" />
                        </el-form-item>
                        <el-form-item label="验证码"
                                      prop="captcha"
                                      required>
                            <el-col :span="6">
                                <img ref="captchaRef"
                                     :src="captchaUrl"
                                     alt="验证码"
                                     @click="refreshCaptcha($event)"
                                     style="width: 100px ; height: 36px;">
                            </el-col>
                            <el-col :span="18">
                                <el-input v-model="regForm.captcha"
                                          type="text"
                                          clearable
                                          placeholder="验证码" />
                            </el-col>
                        </el-form-item>
                        <el-form-item class="space-between">
                            <el-col :span="6">
                                <button class="button"
                                        @click.prevent="submitForm(regFormRef)">注册</button>
                            </el-col>
                            <el-col :span="18"
                                    style="text-align:right;">
                                <a class="link"
                                   @click.prevent="isLogin = true">已经有账号？去登陆</a>
                            </el-col>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
        </section>
    </div>
</template>

<script setup lang='ts'>
import { ref, reactive, inject } from 'vue'
import { throttle } from '../utils/throttle'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import hRequest from '../utils/HRequest'

const serverBaseUrl = inject<string>('serverBaseUrl') as string

const captchaUrl = serverBaseUrl + '/auth/CaptchaServlet'
// 获得验证码元素实例
const captchaRef = ref<HTMLImageElement>()
// 点击刷新验证码
const refreshCaptcha = (e: MouseEvent) => {
    (e.currentTarget as HTMLImageElement).src = captchaUrl
}

// 登录和注册切换
const isLogin = ref(true)

// 登录表单
const loginFormRef = ref<FormInstance>()
const loginForm = reactive({
    username: '',
    pwd: '',
    captcha: ''
})
// 检查登录表单
const loginFormRules = reactive<FormRules>({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    pwd: [
        { required: true, message: '请输入密码', trigger: 'blur' }
    ],
    captcha: [
        { required: true, message: '请输入验证码', trigger: 'blur' }
    ]
})
const submitLoginForm = throttle(async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate(async (valid, fields) => {
        if (valid) {
            // 用户名和密码，放入参数中 
            try {
                let params = new FormData()
                params.append('username', loginForm.username)
                params.append('pwd', loginForm.pwd)
                params.append('captcha', loginForm.captcha)
                const loginRes = (await hRequest.post('auth/LoginServlet', params, {
                })) as Result
                if (loginRes.code == 1) {
                    ElMessage.success('登录成功！ 正在跳转到主页')
                    // 将 token 存入 localStorage
                    localStorage.setItem('jwtToken', loginRes.data)
                    // 跳转到主页
                    setTimeout(() => {
                        window.location.href = './'
                    }, 500)
                } else {
                    // 登陆错误刷新验证码
                    captchaRef.value?.click()
                    ElMessage.error(loginRes.msg)
                }
            } catch (e) {
                ElMessage.error('服务器错误，请稍后再试！')
            }
        }
    })
}, 500)


// 注册表单DOM实例
const regFormRef = ref<FormInstance>()
// 注册表单数据
const regForm = reactive({
    username: '',
    pwd: '',
    pwdConfirm: '',
    phone: '',
    captcha: '',
})
// 注册表单规则
const regFormRules = reactive<FormRules>({
    username: [
        { required: true, message: '用户名不能为空', trigger: 'blur' },
        // 只能是A~Z a~z 0~9 _ -
        { pattern: /^[A-Za-z0-9_-]+$/, message: '用户名只能是A~Z a~z 0~9 _ -', trigger: 'blur' },
        { max: 20, message: '用户名长度不能超过20', trigger: 'blur' },
    ],
    pwd: [
        { required: true, message: '密码不能为空', trigger: 'blur', },
        // 只能是A~Z a~z 0~9 .
        { pattern: /^[A-Za-z0-9.]+$/, message: '密码只能是A~Z a~z 0~9 .', trigger: 'blur' },
        { min: 6, max: 20, message: '密码长度6~20', trigger: 'blur' },
    ],
    pwdConfirm: [
        { required: true, message: '请确认密码', trigger: 'blur', },
        {
            validator: (rule, value, callback) => {
                value !== regForm.pwd ? callback('两次输入密码不一致') : callback()
            },
            trigger: 'blur'
        },
    ],
    phone: [
        { pattern: /^[0-9]+$/, message: '请输入正确的号码', trigger: 'blur' },
        { min: 11, max: 11, message: '手机号长度11', trigger: 'blur' },
    ],
    captcha: [
        { required: true, message: '请输入验证码', trigger: 'blur', },
    ],
})
// 注册表单提交
const submitForm = throttle(async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate(async (valid, fields) => {
        if (valid) {
            // 用户名和密码，放入参数中 
            let data = new FormData()
            data.append('username', regForm.username)
            data.append('pwd', regForm.pwd)
            data.append('phone', regForm.phone)
            data.append('captcha', regForm.captcha)
            const regRes = (await hRequest.post('auth/RegServlet', data, {
            })) as Result
            if (regRes.code == 1) {
                ElMessage.success('注册成功！可以去登录了')
                // 把注册成功的用户名和密码放入登录表单
                loginForm.username = regForm.username
                loginForm.pwd = regForm.pwd
                isLogin.value = true
            } else {
                // 登陆错误刷新验证码
                captchaRef.value?.click()
                ElMessage.error(regRes.msg)
            }
        } else {
            ElMessage.error('请正确输入信息')
        }
    })
}, 500)
</script>

<style scoped>
.inner {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.form-wrap {
    width: 500px;
}

h2 {
    margin: 40px 0;
    font-size: 32px;
    text-align: center;
    font-weight: 400;
}

form {
    width: 100%;
}

form>div {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;
}

a {
    line-height: 36px;
}

.button {
    min-width: 200px;
    text-align: center;
}
</style>