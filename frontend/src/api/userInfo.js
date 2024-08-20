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

export function updateMyInfoService(sysuser){
    const data = sysuser; 
    return new Promise((resolve,reject)=>{
        Request({
            url: "/users/updateUser",
            method: "PUT",
            data,
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        });
    });
}

export function updateMyAvatarService(id,url){
    return new Promise((resolve,reject)=>{
        Request({
            url: `/users/updateAvatar?id=${id}&url=${url}`,
            method: "PUT"
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        });
    });
}