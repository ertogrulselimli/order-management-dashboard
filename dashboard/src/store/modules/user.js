import  userApi from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter,constantRoutes, asyncRoutes} from '@/router'



function parseJwt (token) {
  var base64Url = token.split('.')[1];
  var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
}

const state = {
  token: getToken(),
  name: null,
  avatar: '',
  routes:[],
  isSuperAdmin:false
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_MENUS: (state, menus) => {
    state.menus = menus;
  },
  SET_ROUTES: (state, routes) => {
    state.routes = routes
  },
  SET_SUPERADMIN:(state,val)=>{
    state.isSuperAdmin=val;
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      userApi.login({ username: username.trim(), password: password }).then(response => {
        console.log(response);
        const { data } = response
        commit('SET_TOKEN', data.jwttoken)
        setToken(data.jwttoken);
        resolve(response)
      }).catch(error => {
        reject(error)
      })
    })
  },



  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
        const data=parseJwt(state.token);
        if (!data) {
          reject('Verification failed, please Login again.')
        }
        const {firstname,lastname, avatar ,managerSuperAdmin} = data;
        const menus=data.menus.split(',')
        let accessedRoutes=managerSuperAdmin?asyncRoutes:filterAsyncRoutes(asyncRoutes,menus);
        commit('SET_ROUTES', constantRoutes.concat(accessedRoutes))
        commit('SET_MENUS',menus);
        //there should be some actions that needs permission but is not even menu or route --in order to handle this we will specify it in router as calculated_permissions
        commit('SET_NAME', firstname+' '+lastname)
        commit('SET_AVATAR', avatar);
        commit('SET_SUPERADMIN',managerSuperAdmin);
        resolve({routes:accessedRoutes}) //we dont resolve constant routes as it is added on application startup
    })
  },

  // user logout
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      userApi.logout().then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROUTES',[]);
        commit('SET_NAME', null);
        removeToken()
        resetRouter()
        dispatch('tagsView/delAllViews', null, { root: true })
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },




  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_MENUS', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token'
    commit('SET_TOKEN', token)
    setToken(token)
    const { roles } = await dispatch('getInfo')
    resetRouter()
    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, { root: true })
  }
}

//We match route.meta.menu with user menus
function hasPermission(menus, route) {
  if (route.meta && route.meta.menu) {
    return menus.some(menu => route.meta.menu==menu)
  } else {
    return true;//if router has not meta menu it means it has  no guard for specific user
  }
}


export function filterAsyncRoutes(routes, menus) {
  const res = []
  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(menus, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, menus)
      }
      res.push(tmp)
    }
  })
  return res
}


export default {
  namespaced: true,
  state,
  mutations,
  actions
}
