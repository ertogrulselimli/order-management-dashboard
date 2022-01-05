import store from '@/store'

function checkPermission(el, binding) {
  const { value } = binding
  const roles = store.getters && store.getters.menus
  const isSuperAdmin=store.state.user.isSuperAdmin;
  if (value) {
    let hasPermission=false;
     if(isSuperAdmin){
       hasPermission=true;
     }else{
       hasPermission = roles.some(role => {
         return role==value;
       });
     }
     if (!hasPermission) {
      el.parentNode && el.parentNode.removeChild(el)
     }
  } /*else {
      //throw new Error(`need roles! Like v-permission="'admin'"`)
      //if no value specified means it has access  so dont remove
  }*/
}

export default {
  inserted(el, binding) {
    checkPermission(el, binding)
  },
  update(el, binding) {
    checkPermission(el, binding)
  }
}
