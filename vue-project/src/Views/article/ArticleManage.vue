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

const unfinishedRecord = ref([])

//声明一个异步函数
import { unfinishedRecordListService,cancelBillService,finishBillService } from '@/API/transaction';
const unfinishedRecordList = async()=>{
    let params = {
        paymentAccount:paymentAccount.value ? paymentAccount.value:null,
        receivingAccount:receivingAccount.value ? receivingAccount.value:null,
        state:state.value ? state.value:null
    }
    let result = await unfinishedRecordListService(params);
    unfinishedRecord.value = result.data;
}
onMounted(() => {
    unfinishedRecordList();
    });

//完成交易
import { ElMessageBox,ElMessage } from 'element-plus';
const finishBill = (row)=>{
    //确认框
    ElMessageBox.confirm(
    '确认是否支付',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
    )
    .then(async() => {
        //点击确认后调用接口删除
        let result = await finishBillService({ 
            ID: row.id,
            sendUsername: row.sendUsername,
            receiveUsername: row.receiveUsername,
            amount: row.amount,
            memo: row.memo
         })

         if(result.code === 0){
            ElMessage({
                type: 'info',
                message: result.msg,})
         }
         if(result.code === 1){
            ElMessage({
                type: 'success',
                message:'支付成功'})
         }
      
      //调用获取邮箱信息函数刷新页面
        unfinishedRecordList();
        dialogVisible.value = false;
    })
    .catch(() => {
      /* ElMessage({
        type: 'info',
        message: '取消操作',
      }) */
    })
}

//取消交易
const cancelBill = (row)=>{
    //确认框
    ElMessageBox.confirm(
    '你确定要取消该交易吗？',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
    )
    .then(async() => {
        //点击确认后调用接口删除
        let result = await cancelBillService({ ID: row.id })
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //调用获取邮箱信息函数刷新页面
        unfinishedRecordList();
        dialogVisible.value = false;
    })
    .catch(() => {
      /* ElMessage({
        type: 'info',
        message: '取消操作',
      }) */
    })
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>未完成交易</span>
                <div class="extra">
                    <el-button type="primary">发起收款请求</el-button>
                </div>
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
                <el-button type="primary" @click="unfinishedRecordList">搜索</el-button>
                <el-button @click="state='';paymentAccount='';receivingAccount=''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- List -->
        <el-table :data="unfinishedRecord" style="width: 100%">
            <el-table-column label="序号" prop="id"> </el-table-column>
            <el-table-column label="付款账户" width="400" prop="sendUsername"></el-table-column>
            <el-table-column label="收款账户" prop="receiveUsername"></el-table-column>
            <el-table-column label="发起时间" prop="createTime"> </el-table-column>
            <el-table-column label="金额" prop="amount"></el-table-column>
            <el-table-column label="备注" prop="memo"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="finishBill(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="cancelBill(row)"></el-button>
                </template>
            </el-table-column>
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
        margin-bottom: 20px; // 根据需要调整间距
    }
}
</style>