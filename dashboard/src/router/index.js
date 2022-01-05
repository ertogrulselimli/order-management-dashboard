import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
/* Layout */
import Layout from '@/layout'

/* Router Modules */
/*import componentsRouter from './modules/components'
import chartsRouter from './modules/charts'
import tableRouter from './modules/table'
import nestedRouter from './modules/nested'*/
/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    menu:'',//will be the same as name of route and must be the same as component name we got
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
/*
For CRUD operations
Important note here all menu names and router names must follow below conventions
Create{entityName} List{entityName} View{entityName} Edit{entityName}

 */
export const constantRoutes = [
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [
            {
                path: 'dashboard',
                component: () => import('@/views/dashboard/index'),
                name: 'Dashboard',
                meta: {title: 'Dashboard', icon: 'dashboard', menu: 'Dashboard',affix:true}
            }
        ]
    },
    {
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [
            {
                path: '/redirect/:path(.*)',
                component: () => import('@/views/redirect/index')
            }
        ]
    },
    {
        path: '/login',
        component: () => import('@/views/login/index'),
        hidden: true
    },
    {
        path:'/change-password',
        component: ()=>import('@/views/login/change-password'),
        hidden:true
    },
    {
        path: '/404',
        component: () => import('@/views/error-page/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () => import('@/views/error-page/401'),
        hidden: true
    },
    {
        path: '/profile',
        component: Layout,
        redirect: '/profile/index',
        hidden: true,
        children: [
            {
                path: 'index',
                component: () => import('@/views/profile/index'),
                name: 'Profile',
                meta: {title: 'Profile', icon: 'user', noCache: true}
            }
        ]
    }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
/*  */

export const asyncRoutes1 = [
   /* {
        path: '/icon',
        component: Layout,
        name:'Icons',
        meta: {
            menu: 'Icons',
            title:'Icon'
        },
        children: [
            {
                path: 'index',
                component: () => import('@/views/icons/index'),
                name: 'Icon',
                meta: {title: 'Icons', icon: 'icon', noCache: true, menu: 'Icon'}
            }
        ]
    },*/
    {
        path: '/managers',
        component: Layout,
        redirect: '/managers/list',
        name: 'Managers',
        meta: {title: 'İstifadəçilər',icon:'peoples'},
        children: [
            {
                path: 'list',
                component: () => import('@/views/system/managers/list-manager.vue'),
                name: 'ListManager',
                meta: {menu: 'ListManager', title: 'İstifadəçi listi'}
            },
            {
                path: 'edit/:id(\\d+)',
                component: () => import('@/views/system/managers/edit-manager.vue'),
                name: 'EditManager',
                hidden: true,
                meta: {menu: 'EditManager', title: 'Edit Manager'}
            },
            {
                path: 'new',
                component: () => import('@/views/system/managers/new-manager.vue'),
                name: 'NewManager',
                meta: {menu: 'NewManager', title: 'Istifadəçi əlavə et'}
            },
            {
                path: 'view/:id(\\d+)',
                component: () => import('@/views/system/managers/view-manager.vue'),
                hidden:true,
                name: 'ViewManager',
                meta: {menu: 'ViewManager', title: 'View Manager'}
            },
            {
                name: 'DeleteManager',
                notRoute: true,
                meta: {menu:'DeleteManager',title:'İstifadəçi silmək'}
            }
        ]
    },
    {
        path: '/products',
        component: Layout,
        redirect: '/products/list',
        name: 'Products',
        meta: {title: 'Məhsullar',icon:'people'},
        children: [
            {
                path: 'list',
                component: () => import('@/views/products/list-product'),
                name: 'ListProduct',
                meta: {menu: 'ListProduct', title: 'Məhsullar',noCache:true}
            },
            {
                path: 'edit/:id(\\d+)',
                component: () => import('@/views/products/edit-product.vue'),
                name: 'EditProduct',
                hidden: true,
                meta: {menu: 'EditProduct', title: 'Məhsulu Dəyiş'}
            },
            {
                path: 'new',
                component: () => import('@/views/products/new-product.vue'),
                name: 'NewProduct',
                meta: {menu: 'NewProduct', title: 'Yeni Məhsul'}
            },
            {
                path: 'view/:id(\\d+)',
                component: () => import('@/views/products/view-product.vue'),
                hidden:true,
                name: 'ViewProduct',
                meta: {menu: 'ViewProduct', title: 'Məhsula Bax'}
            }
        ]
    },
    {
        path: '/distributors',
        component: Layout,
        redirect: '/distributors/list',
        name: 'Distributors',
        meta: {title: 'Distributorlar', menu: 'Distributors',icon:'peoples'},
        children: [
            {
                path: 'list',
                component: () => import('@/views/distributors/list-distributor'),
                name: 'ListDistributor',
                meta: {menu: 'ListDistributor', title: 'Distributorlar',noCache:true}
            },
            {
                path: 'edit/:id(\\d+)',
                component: () => import('@/views/distributors/edit-distributor.vue'),
                name: 'EditDistributor',
                hidden: true,
                meta: {menu: 'EditDistributor', title: 'Edit Distributor'}
            },
            {
                path: 'new',
                component: () => import('@/views/distributors/new-distributor.vue'),
                name: 'NewDistributor',
                meta: {menu: 'NewDistributor', title: 'Distributor əlavə et'}
            },
            {
                path: 'view/:id(\\d+)',
                component: () => import('@/views/distributors/view-distributor.vue'),
                hidden:true,
                name: 'ViewDistributor',
                meta: {menu: 'ViewDistributor', title: 'View Distributor'}
            }
        ]
    },
    {
        path: '/mnf-points',
        component: Layout,
        redirect: '/mnf-points/list',
        name: 'ManufacturePoints',
        meta: {title: 'Istehsal Nöqtələri',icon:'peoples'},
        children: [
            {
                path: 'list',
                component: () => import('@/views/manufacture/points/list-point'),
                name: 'ListManufacturePoint',
                meta: {menu: 'ListManufacturePoint', title: 'Istehsal Nöqtələri',noCache:true}
            },
            {
                path: 'edit/:id(\\d+)',
                component: () => import('@/views/manufacture/points/edit-point.vue'),
                name: 'EditManufacturePoint',
                hidden: true,
                meta: {menu: 'EditManufacturePoint', title: 'Edit Point'}
            },
            {
                path: 'new',
                component: () => import('@/views/manufacture/points/new-point.vue'),
                name: 'NewManufacturePoint',
                meta: {menu: 'NewManufacturePoint', title: 'Yenisini əlavə et'}
            },
            {
                path: 'view/:id(\\d+)',
                component: () => import('@/views/manufacture/points/view-point.vue'),
                hidden:true,
                name: 'ViewManufacturePoint',
                meta: {menu: 'ViewManufacturePoint', title: 'View Point'}
            },
            {
                name: 'DeleteManufacturePoint',
                notRoute: true,
                meta: {menu:'DeleteManufacturePoint',title:'İstehsal nöqtəsini sil'}
            },
        ]
    },
    {
        path:'/parameters',
        component: Layout,
        meta: {title: 'Parameterlər',icon:'el-icon-s-management'},
        name: 'Parameters',
        redirect: '/parameters/units',
        children: [
            {
                path: 'units',
                name: 'ListMeasurementUnit',
                component:()=>import('@/views/parameters/units/list-unit.vue'),
                meta: {menu: 'ListMeasurementUnit',title: 'Ölçü tanımları',noCache:true},
            },
            {
                name:'EditMeasurementUnit',
                notRoute: true,
                meta: {menu:'EditMeasurementUnit',title:'Ölçü dəyiş'}
            },
            {
                name: 'DeleteMeasurementUnit',
                notRoute: true,
                meta: {menu:'DeleteMeasurementUnit',title:'Ölçü sil'}
            },
            {
                name:'NewMeasurementUnit',
                notRoute:true,
                meta: {menu:'NewMeasurementUnit',title:'Yeni Ölçü'}
            },
            {
                path: 'materials',
                name: 'ListRawMaterial',
                component: ()=>import('@/views/parameters/materials/list-material.vue'),
                meta: {menu: 'ListRawMaterial',title: 'Xammallar'}
            },
            {
                name:'EditRawMaterial',
                notRoute: true,
                meta: {menu:'EditRawMaterial',title:'Xammalı dəyiş'}
            },
            {
                name: 'DeleteRawMaterial',
                notRoute: true,
                meta: {menu:'DeleteRawMaterial',title:'Xammalı sil'}
            },
            {
                name:'NewRawMaterial',
                notRoute:true,
                meta: {menu:'NewRawMaterial',title:'Xammal əlavə et'}
            },
        ]
    },
    {
        path: '/roles',
        component: Layout,
        meta: {title: 'Roles', menu: 'Roles',icon:'el-icon-s-management'},
        name: 'Roles',
        redirect: '/roles/list',
        children: [
            {
                path: 'list',
                name: 'ListRole',
                component: () => import('@/views/system/roles/list-role.vue'),
                meta: {menu: 'ListRole', title: 'Role list'}
            },
            {
                path: 'create',
                name: 'NewRole',
                component: () => import('@/views/system/roles/new-role.vue'),
                meta: {menu: 'NewRole', title: 'New Role'}
            },
            {
                path: 'edit/:id(\\d+)',
                name: 'EditRole',
                hidden: true,
                component: () => import('@/views/system/roles/edit-role.vue'),
                meta: {menu: 'EditRole', title: 'Edit Roles'}
            },
            {
                path:'view/:id(\\d+)',
                name:'ViewRole',
                hidden:true,
                component:()=>import('@/views/system/roles/view-role'),
                meta:{menu:'ViewRole',title:'View Role'}
            }
        ]
    },
   /* {
        path: '/example',
        component: Layout,
        redirect: '/example/list',
        name: 'Example',
        meta: {
            title: 'Example',
            icon: 'el-icon-s-help',
            menu: 'Example'
        },
        children: [
            {
                path: 'create',
                component: () => import('@/views/example/create'),
                name: 'CreateArticle',
                meta: {title: 'Create Article', icon: 'edit', menu: 'CreateArticle'}
            },
            {
                path: 'edit/:id(\\d+)',
                component: () => import('@/views/example/edit'),
                name: 'EditArticle',
                meta: {title: 'Edit Article', noCache: true, activeMenu: '/example/list', menu: 'EditArticle'},
                hidden: true
            },
            {
                path: 'list',
                component: () => import('@/views/example/list'),
                name: 'ArticleList',
                meta: {title: 'Article List', icon: 'list', menu: 'ArticleList'}
            }
        ]
    },*/
    {path: '*', redirect: '/404', hidden: true}
]



function filterRoutes(asyncRoutesl){
    const res = [];
    asyncRoutesl.forEach(z => {
        let t = {...z};
        let treeNode = {};
        if (t.children) {
            treeNode=t;
            treeNode['children'] = filterRoutes(t.children);
            res.push(treeNode)
        }else{
            if(!t.notRoute){
                treeNode=t;
                res.push(treeNode)
            }
        }
    })
    return res;
}


export  const asyncRoutes=filterRoutes(asyncRoutes1);

const createRouter = () => new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router
