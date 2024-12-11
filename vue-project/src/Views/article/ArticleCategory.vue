<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref,onMounted } from 'vue'

const paymentAccount = ref('')
const receivingAccount = ref('')

//用户搜索时选中的发布状态
const state=ref('')

const finishedRecord = ref([])
//声明一个异步函数
import { finishedRecordListService } from '@/API/transaction';
const finishedRecordList = async()=>{
    let params = {
        paymentAccount:paymentAccount.value ? paymentAccount.value:null,
        receivingAccount:receivingAccount.value ? receivingAccount.value:null,
        state:state.value ? state.value:null
    }
    let result = await finishedRecordListService(params);
    finishedRecord.value = result.data;
}
onMounted(() => {
    finishedRecordList();
    }); 

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>交易记录</span>
                <!-- <div class="extra">
                    <el-button type="primary">发起收款请求</el-button>
                </div> -->
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form class="search-form" inline>
            <el-form-item label="交易类型：">
                <el-select placeholder="请选择" clearable v-model="state" style="width: 100px;" popper-append-to-body>
                    <el-option label="付款" value="付款"></el-option>
                    <el-option label="收款" value="收款"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="付款账户：" style="flex: 1;">
                <el-input v-model="paymentAccount" placeholder="请输入付款账户" style="width: 200px;"></el-input>
            </el-form-item>

            <el-form-item label="收款账户：" style="flex: 1;">
                <el-input v-model="receivingAccount" placeholder="请输入收款账户" style="width: 200px;"></el-input>
            </el-form-item>


            <el-form-item style="margin-left: auto;">
                <el-button type="primary" @click="finishedRecordList">搜索</el-button>
                <el-button @click="state='';paymentAccount='';receivingAccount=''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="finishedRecord" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="付款账户" width="400" prop="sendUsername"></el-table-column>
            <el-table-column label="收款账户" prop="receiveUsername"></el-table-column>
            <el-table-column label="完成时间" prop="finishTime"> </el-table-column>
            <el-table-column label="交易金额" prop="amount"></el-table-column>
            <el-table-column label="备注" prop="memo"></el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .search-form {
        display: flex;
        align-items: center;
        margin-bottom: 20px;
        flex-wrap: wrap; // 使表单项换行
    }

    .el-form-item {
        margin-right: 20px;
    }
}
</style>