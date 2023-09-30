import axios, { AxiosHeaders, AxiosRequestHeaders } from 'axios';

// Next we make an 'instance' of it
const axiosConf = axios.create({
// .. where we make our configurations
    baseURL: 'http://localhost:8080'
});

const axiosAuth = axios.create({
    // .. where we make our configurations
        baseURL: 'http://localhost:8080'
    });

axiosConf.defaults.headers.common = {
    ...axiosConf.defaults.headers.common,
    "Access-Control-Allow-Origin" : "*", 
    'Access-Control-Allow-Methods': 'GET, POST, OPTIONS, PATCH', 
    "Access-Control-Expose-Headers":"*"
}

const changableActionsList:string[] = ['post', 'patch', 'delete']

// axiosConf.interceptors.response.use(
//     function (response) {
//         return response;
//   }, 
//     function (error) {
//         // console.log(error.config)
//         const originalRequest = error.config
//         // if ((error.response.status == 403 || error.response.status == 401)  ){ // && originalRequest.method == 'get'
//         //     // localStorage.setItem('tokenRefreshed', 'false');
//         //     originalRequest._retry = false;
//         //     console.log("AAAAAAAAAAAAAAAAAAAA")
//         //     // вызываем refresh token api
//         //     window.location.href = 'https://www.google.ru/';
            
//         // }

//         return Promise.reject(error);
// });

// axiosConf.interceptors.response.use(
//     function (response) {
//         return response;
//   }, 
//     function (error) {
//         const originalRequest = error.config
//         // console.log(originalRequest)
//         if ((error.response.status == 403 || error.response.status == 401)  ){ // && originalRequest.method == 'get'
//             // localStorage.setItem('tokenRefreshed', 'false');
//             originalRequest._retry = true;
//             // вызываем refresh token api
//             let token = localStorage.getItem('token');
//             if (token!=null){
//                 axiosAuth.post("/api/auth/refresh", {'token':token})
//                     .then((response) => {
//                         const newToken:string = response.data.token
//                         localStorage.setItem('token', newToken);
//                         localStorage.setItem('tokenRefreshed', 'true');

//                         // window.location.reload();
//                         return axiosConf.request(originalRequest)
//                     })
//                     // вот если не авторизован, типо мы добавим логику просрочки токена силовй для старых кандидатов, вот тогда вернет 500 и tokenRefreshed = false
//                     .catch(()=>{
//                         localStorage.setItem('tokenRefreshed', 'false');
//                         return axiosConf(originalRequest)
//                     })

//             }
//         }

//         return Promise.reject(error);
// });

// axiosConf.interceptors.request.use( config => {
//     let token = localStorage.getItem('token');

//     // console.log(config.url)

//     // // check, if token is closed to beeing expired
//     // axiosConf.get("/api/auth/token-claims/" + token)
//     //     .then((response) => {
//     //         const tokenClaims:JwtTokenClaimsInterface = response.data

//     //         const expirationAt:number = tokenClaims.exp
//     //         const now:EpochTimeStamp = new Date().getTime();
            
//     //         // если меньше 10 сек осталось до завершения токена, сгенери новый
//     //         if ((expirationAt - now) < 10000 ){
//     //             axiosConf.post("/api/auth/refresh", {'token':token})
//     //                 .then((response) => {
//     //                     token = response.data.token
//     //                     if (token != null){
//     //                         localStorage.setItem('token', token);
//     //                     }
//     //                 })
//     //         }

//     //     })

//     // set token everywhere
//     config.headers = { ...config.headers } as Partial<AxiosRequestHeaders>;
//     if (token!=null){
//         config.headers['Authorization'] = `Bearer ${token}`
//     }

//     return config
// });

// axiosConf.interceptors.request.use( config => {
//     axiosConf.get("/api/tasks")
//         .then(()=>{
//             return config
//         })
//         .catch((error) => {
//             console.log(error)
//         })

//     return config
// });

export default axiosConf;