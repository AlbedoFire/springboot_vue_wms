<template>
    <el-menu
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b"
            style="height: 100%;"
            default-active="/Home"
            :collapse="isCollapse"
            :collapse-transition="false"
            router
    >
       <el-menu-item index="/Home">
           <i class="el-icon-s-home"></i>
           <span slot="title">首页</span>
       </el-menu-item>

        <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
            <i :class="item.menuicon"></i>
            <span slot="title">{{item.menuname}}</span>
        </el-menu-item>


    </el-menu>
</template>

<script>

import { addNewRoute } from '../store';

    export default {
        name: "Aside",
        data(){
            return {
                //isCollapse:false

            //    menu:[
            //         {
            //             menuClick:'Admin',
            //             menuName:'管路员管理',
            //             menuIcon:'el-icon-s-custom'
            //         },{
            //             menuClick:'User',
            //             menuName:'用户管理',
            //             menuIcon:'el-icon-user-solid'
            //         },
            //         {
            //             menuClick:'Role',
            //             menuName:'角色管理',
            //             menuIcon:'el-icon-s-custom'
            //         }
            //     ]
            }
        },
        computed:{
            "menu":{
                get(){
                    return this.$store.state.menu
                }
            }
        },
        props:{
            isCollapse:Boolean
        },
        created(){
            // console.log(this.$store.state.menu)
            // this.menu=this.$store.state.menu
            addNewRoute(this.$store.state.menu)
        },
    }
</script>

<style scoped>

</style>