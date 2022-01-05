
// this is for init Socket and bind this to global variable that every Vue instance can refer to this as this.$stompClient

import * as SockJS from 'sockjs-client'
import * as Stomp from 'webstomp-client'
import {getToken} from '@/utils/auth';
import Vue from 'vue';
let   reConnStopInterval=null;

function  errorCallback() {
        reConnStopInterval = setInterval(() => {
             connectToSocket();
             clearInterval(reConnStopInterval);
        }, 10000);
}

export function  connectToSocket(){
        let socketUrl = '//' + process.env.VUE_APP_WEBSOCKET_URL + '/admin/websocket';
        socketUrl += '?wsauth_token=' + getToken();
        const socket = new SockJS(socketUrl);
        const  stompClient = Stomp.over(socket, {protocols: ['v12.stomp']});
        const headers = {};
        stompClient.connect(headers, () => {
            reConnStopInterval && clearInterval(reConnStopInterval);
            Vue.prototype.$stompClient=stompClient;
        },()=>{
            reConnStopInterval && clearInterval(reConnStopInterval);
            errorCallback();
        });



}
