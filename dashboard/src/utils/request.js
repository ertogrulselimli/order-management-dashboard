import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'


// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
   withCredentials: true, // send cookies when cross-domain requests
   timeout: 5000 // request timeout
});



// request interceptor
service.interceptors.request.use(
  config => {
      //Log Request
      //console.log(config);
    // do something before request is sent
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['Authorization'] = 'Bearer '+ getToken()
    }
    return config
  },
  error => {
    // do something with request error
   // console.log(error); // for debug
    return Promise.reject(error);
  }
);

// response interceptor
service.interceptors.response.use(
    response => {
           //console.log(response);
           return response;
      },
    error => {
       //The only way to diagnose network and server unreachable error is making string comparisons
        if(error.message=='Network Error'){
            Message({
                message:'Check your network connectivity or contact service support',
                type:'error',
                duration:5000
            });
            return Promise.reject(error);
        }
        //this is framework based when status not 200 this will end up here  we will check our custom error code to decide whether here or in specific component to handle error
           const errorResp=error.response;
         if(errorResp.status==403){
               // to re-login
               MessageBox.confirm('Your session has expired , you can cancel to stay on this page, or log in again', 'Confirm logout', {
                   confirmButtonText: 'Re-Login',
                   cancelButtonText: 'Cancel',
                   type: 'warning'
               }).then(() => {
                   store.dispatch('user/resetToken').then(() => {
                       location.reload()
                   })
               })

       }
      return Promise.reject(error)
  }
);

export default service
