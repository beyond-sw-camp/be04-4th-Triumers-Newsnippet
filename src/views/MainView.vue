<template>

        <Header>
        <RouterLink to="/" active-class="active" replace>메인</RouterLink>
        <RouterLink to="/quiz" active-class="active" replace>퀴즈</RouterLink>
        <RouterLink to="/manage" active-class="active" replace>퀴즈 관리</RouterLink>
        <RouterLink to="/login" active-class="active" replace>로그인</RouterLink>
        <RouterLink to="/signUp" active-class="active" replace>회원가입</RouterLink>
        </Header>
    <div>
        <Main></Main>
    </div>
</template>

<script setup>
    import Main from '@/components/Main.vue'
</script>

<style scoped>

</style>