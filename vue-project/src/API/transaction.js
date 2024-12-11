import request from '@/Utils/request.js'
import { useTokenStore } from '@/stores/token'

//交易记录查询
export const finishedRecordListService = (params)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据均不需要.value，可以直接使用
    //return request.get('/user/email',{headers:{'Authorization':tokenStore.token}})
    return request.get('/transaction/finished',{params:params})
}

//未完成交易查询
export const unfinishedRecordListService = (params)=>{
    //const tokenStore = useTokenStore();
    //在pinia中定义的响应式数据均不需要.value，可以直接使用
    //return request.get('/user/email',{headers:{'Authorization':tokenStore.token}})
    return request.get('/transaction/unfinished',{params:params})
}

//取消未完成交易
export const cancelBillService = (billData)=>{
    return request.delete('/transaction/cancel', {
        data:billData, // 直接将 emailData 作为 JSON 数据体发送
        headers: {
            'Content-Type': 'application/json', // 确保 Content-Type 为 JSON
        },
    });
}

//完成交易
export const finishBillService = (billData)=>{
    return request.delete('/transaction/finish', {
        data:billData, // 直接将 emailData 作为 JSON 数据体发送
        headers: {
            'Content-Type': 'application/json', // 确保 Content-Type 为 JSON
        },
    });
}