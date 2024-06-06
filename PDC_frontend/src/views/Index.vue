<template>
    <!-- Banner -->
    <section id="banner"
             class="major">
        <div class="inner">
            <header class="major">
                <h1>Hi, 欢迎参加我们的海报设计竞赛！</h1>
            </header>
            <div class="content">
                <p>展现你的创意与才华，参与我们的海报设计竞赛，赢取丰厚奖品并将你的作品展示给全世界！</p>
                <ul class="actions">
                    <li><a href="#one"
                           class="button next scrolly">开始</a></li>
                </ul>
            </div>
        </div>
    </section>
    <!-- Main -->
    <div id="main">
        <!-- One -->
        <section id="one"
                 class="tiles">
            <article v-for="c in categorys"
                     :key="c.id">
                <span class="image">
                    <img src="../assets/images/pic04.jpg"
                         alt="" />
                </span>
                <header class="major">
                    <h3><a @click.prevent="router.push(`./contests?category_id=${c.id}`)"
                           class="link">
                            {{ c.name }}
                        </a></h3>
                    <p style="min-width: 200px;"></p>
                </header>
            </article>
        </section>
    </div>
</template>

<script setup lang='ts'>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router';
import hRequest from '../utils/HRequest';

const router = useRouter()
const categorys = ref<Category[]>([])

onMounted(async () => {
    categorys.value = (await hRequest.get('/CategoryServlet')).data;
})

</script>

<style scoped></style>