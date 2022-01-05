<template>
    <el-dialog :visible.sync="show"
               @close="onClose">
        <el-tree
                :data="menus"
                show-checkbox
                checked-strictly="true"
                node-key="id"
                default-expand-all
                :expand-on-click-node="false"
                :default-checked-keys="selectedMenus"
                @check="onNodeCheck"
        >
                 <span class="custom-tree-node" slot-scope="{ node, data }">
                 <span>{{ node.label }}</span>
              </span>
        </el-tree>
        <span slot="footer" class="dialog-footer">
            <el-button @click="closeDialog">Cancel</el-button>
            <el-button type="primary"  @click="handleApplyMenus">Add</el-button>
         </span>
    </el-dialog>
</template>

<script>
    import {asyncRoutes1} from "@/router";

    function convertRoutesToTree(routes) {
        const res = [];
        routes.forEach(z => {
            let t = {...z};
            let treeNode = {};
            if(!t.name)return;
            if (t.children && t.children.length > 1) {
                treeNode['label'] = t.meta.title;
                treeNode['id'] = t.name;
                treeNode['children'] = convertRoutesToTree(t.children);
            } else if(!t.children){
                treeNode['label'] = t.meta.title;
                treeNode['id'] = t.meta.menu;
                treeNode['concrete']=true;
            }else{//only one child no need for tree
                treeNode['label'] = t.children[0].meta.title;
                treeNode['id'] = t.children[0].meta.name;
                treeNode['concrete']=true;
            }
            res.push(treeNode);
        })
        return res;
    }

    export default {
        name: "calculate-menus",
        props:{
             accessedMenus:{
                 type:Array,
                 required:false,
                 default:[]
             },
            showDialog:{
                 type:Boolean,
                 required:true,
                 default:false
            }
        },
        data(){
             //calculate menus based on  async routes
            const  menuTree= convertRoutesToTree(asyncRoutes1);
            const selectedMenuKeys=  this.accessedMenus.map(c=>c.name);
            return {
                show:this.showDialog,
                menus:menuTree,
                selectedMenus:selectedMenuKeys,
                halfCheckedMenus:[],
            }
        },

        created(){

        },
        mounted(){
        },

        methods:{

            onClose(){
                this.$emit('update:showDialog',false);
            },

            closeDialog(){
                this.show=false;
                this.$emit('update:showDialog',false)
            },

            handleApplyMenus(){
                  const menus= this.selectedMenus.map(t=>({name:t}));
                  this.$emit('on-apply-menus',menus);
                  this.show=false;
                  this.$emit('update:showDialog',false)
            },

            onNodeCheck(node,tree){
                console.log(tree);
                this.selectedMenus=tree.checkedNodes.filter(t=>t.concrete).map(t=>t.id);
                console.log(this.selectedMenus);
            },
        },
        watch: {
            showDialog: function(newVal, oldVal) {
                this.show=newVal;
            }
        }
    }
</script>

<style scoped>

</style>
