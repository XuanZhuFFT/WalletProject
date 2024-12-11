<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref,onMounted,watch } from 'vue'

const bankAccount = ref([])
const rechargeForm = ref({
  selectedAccount: '',
  amount: 0
});
const maxAmount = ref(0);

//声明一个异步函数
import { bankAccountListService,addBankAccountService,deleteBankAccountService,rechargeBankAccountService } from '@/API/wallet';
const bankAccountList = async()=>{
    let result = await bankAccountListService();
    bankAccount.value = result.data;
}
onMounted(() => {
    bankAccountList();
    });

 //控制添加分类弹窗
const dialogVisible_add = ref(false)
const dialogVisible_recharge = ref(false);

//添加分类数据模型
const bankAccountModel = ref({
    bankAccount: '',
})
//添加分类表单校验
const rules = {
    bankAccount: [
        { required: true, message: '请输入银行卡号', trigger: 'blur' },
    ],
}
//充值表单规则
const rechargeRules = {
  selectedAccount: [
    { required: true, message: '请选择一个账户', trigger: 'blur' }
  ],
  amount: [
    { required: true, message: '请输入充值金额', trigger: 'blur' },
    { validator: (rule, value, callback) => {
        if (value <= 0) {
          callback(new Error('充值金额必须大于 0'));
        } else if (value > maxAmount.value) {
          callback(new Error('充值金额不能超过账户余额'));
        } else {
          callback();
        }
      }, trigger: 'blur' }
  ]
};

// 监听所选账户的变化，动态更新充值金额的最大值
watch(() => rechargeForm.value.selectedAccount, (newAccount) => {
  const selected = bankAccount.value.find(account => account.account === newAccount);
  maxAmount.value = selected ? selected.balance : 0;
});

// 处理充值操作
const handleRecharge = async () => {
  // 调用充值接口
  const selectedAccount = rechargeForm.value.selectedAccount;
  const amount = rechargeForm.value.amount;

  let params = {
    account:selectedAccount,
    amount:amount
  }
  
  let result = await rechargeBankAccountService(params);
  if (result.code === 1) {
    ElMessage.success('充值成功');
    bankAccountList(); // 刷新银行账户列表
    dialogVisible_recharge.value = false; // 关闭弹窗
  } else {
    ElMessage.error('充值失败');
  }
};

//调用接口，提交表单
import { ElMessage } from 'element-plus';
const addBankAccount = async()=>{
    //调用接口
    let result = await addBankAccountService(bankAccountModel.value);
    ElMessage.success(result.msg?result.msg:'添加成功')

    //调用获取邮箱信息函数刷新页面
    bankAccountList();
    dialogVisible_add.value = false;
}

//删除银行账户
import { ElMessageBox } from 'element-plus';
const deleteBankAccount = (row)=>{
    //确认框
    ElMessageBox.confirm(
    '你确定要删除该账户吗？',
    '警告',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
    )
    .then(async() => {
        //点击确认后调用接口删除
        let result = await deleteBankAccountService({ bankAccount: row.account })
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
      //调用获取邮箱信息函数刷新页面
      bankAccountList();
      dialogVisible_add.value = false;
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消操作',
      })
    })
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>银行账户</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible_add = true">新增账户</el-button>
                    <el-button type="success" @click="dialogVisible_recharge = true">账户充值</el-button>
                </div>
            </div>
        </template>
        <el-table :data="bankAccount" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="账号" prop="account"></el-table-column>
            <el-table-column label="余额" prop="balance"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteBankAccount(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible_add" title="新增银行账户" width="30%">
            <el-form :model="bankAccountModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="银行卡号" prop="bankAccount">
                    <el-input v-model="bankAccountModel.bankAccount" minlength="1" maxlength="30"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addBankAccount"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 账户充值弹窗 -->
        <el-dialog v-model="dialogVisible_recharge" title="账户充值" width="30%">
            <el-form :model="rechargeForm" :rules="rechargeRules" label-width="100px" style="padding-right: 30px">
                <!-- 选择银行账户 -->
                <el-form-item label="选择账户" prop="selectedAccount">
                    <el-select v-model="rechargeForm.selectedAccount" placeholder="选择一个账户">
                        <el-option
                            v-for="account in bankAccount"
                            :key="account.account"
                            :label="account.account"
                            :value="account.account">
                        </el-option>
                    </el-select>
                </el-form-item>

                <!-- 充值金额 -->
                <el-form-item label="充值金额" prop="amount">
                    <el-input 
                    v-model="rechargeForm.amount" 
                    type="number" 
                    min="0" :max="maxAmount" 
                    placeholder="请输入充值金额"
                    @input="onAmountInput">
                    </el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible_recharge = false">取消</el-button>
                    <el-button type="primary" @click="handleRecharge">确认充值</el-button>
                </span>
            </template>
        </el-dialog>


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
}
</style>