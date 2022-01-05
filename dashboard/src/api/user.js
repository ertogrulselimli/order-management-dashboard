import request from '@/utils/request'


export default  {
  login: function(data) {
  return request({
    url: '/authenticate',
    method: 'post',
    data
  })
  },

  getUserInfo: function (token) {

    console.log('In user info parsing jwt')
    console.log(parseJwt(token))
    return request({
      url: '/manager-info',
      method: 'get'
    });
  },

  logout:function () {
    return request({
      url: '/logout',
      method: 'post'
    })
  }

}

