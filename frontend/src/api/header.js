import Request from '@/request/index.js'

export function getMyInfoService(id) {
    return new Promise((resolve,reject)=>{
        Request({
            url: "/users/getMyInfo?id="+id,
            method: "GET",
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        });
    });
}