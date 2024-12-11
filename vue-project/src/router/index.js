import {createRouter,createWebHistory} from 'vue-router'

//导入组件
import LoginVue from '@/Views/Login.vue'
import Layout from '@/Views/Layout.vue'

import ArticleCategoryVue from '@/Views/article/ArticleCategory.vue'
import ArticleManageVue from '@/Views/article/ArticleManage.vue'
import UserAvatarVue from '@/Views/user/UserAvatar.vue'
import UserInfoVue from '@/Views/user/UserInfo.vue'
import UserEmailVue from '@/Views/user/Email.vue'
import UserBankAccountVue from '@/Views/user/BankAccount.vue'
import UserResetPasswordVue from '@/Views/user/UserResetPassword.vue'

//定义路由关系
const routes = [
    {path:'/login',component:()=>import('@/Views/Login.vue')},
    {
        path:'/',
        component:Layout,
        redirect: '/article/manage',
        //子路由
        children:[
            {path:'/account/record',component:ArticleCategoryVue},
            {path:'/article/manage',component:ArticleManageVue},
            {path:'/user/info',component:UserInfoVue},
            {path:'/user/email',component:UserEmailVue},
            {path:'/user/bankAccount',component:UserBankAccountVue},
            {path:'/article/avatar',component:UserAvatarVue},
            {path:'/article/resetpassword',component:UserResetPasswordVue}
        ]
    }
]

//创建路由器
const router = createRouter({
    history:createWebHistory(),
    routes:routes
})

//导出路由
export default router