<template>
    <base-form :form="form"
               :on-submit="handleSubmit"
               :is-view="isView"
               :is-edit="isEdit"
               :rules="formRules"
               :loader-func="loaderFunc"
    >
        <template v-slot:form-items="{form}">
            <el-input type="hidden" v-if="isEdit" v-model="form.id"/>
            <el-row :gutter="100">
                <el-col :span="12">
                    <el-form-item label="Ad" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Açıqlama">
                        <el-input v-model="form.description" type="textarea"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="100">
               <el-col :span="6">
                   <el-form-item label="Qiymət" prop="price">
                       <el-input v-model="form.price" type="number"></el-input>
                   </el-form-item>
               </el-col>
                <el-col :span="6">
                            <el-form-item prop="recomRetailPrice">
                                 <template v-slot:label>
                                    <el-tooltip content="Məsləhət görülən pərakəndə satış qiyməti">  <label >M.G.P.Q</label></el-tooltip>
                                 </template>
                                <el-input v-model="form.recomRetailPrice" type="number"></el-input>
                            </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="Çəkisi" prop="weight">
                        <el-input v-model="form.weight" type="number"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="Çəki vahidi" prop="weightUnit">
                        <el-select  v-model="form.weightUnit" placeholder="Ölçü tipini seçin" clearable value-key="id" :key="'weightUnit'">
                            <el-option v-for="(item) in measurementUnits" :key="'weightUnit'+item.id" :label="item.description" :value="item" />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="100">
                <el-col :span="6">
                    <el-form-item label="Etibarlılıq müddəti" prop="warrantyPeriod">
                        <el-input type="number" v-model="form.warrantyPeriod"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="Müddət tipi">
                        <el-select  v-model="form.warrantyPeriodTerm" placeholder="Müddət tipini seçin" clearable>
                            <el-option v-for="(item,i) in warrantyPeriodTerms" :key="i" :label="item.label" :value="item.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="Bar kod" prop="barCode">
                        <el-input v-model="form.barCode"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-input type="hidden" v-if="isEdit" v-model="form.id"/>
            <div class="tab-container">
                <el-tabs v-model="activeTab" style="margin-top:15px;" type="border-card">
                    <el-tab-pane  label="Xammallar" name="materials">
                        <el-table  :data="form.rawMaterials" border fit highlight-current-row style="width: 100%;position: relative">
                            <el-table-column align="center" label="Xammal" >
                                <template slot-scope="{row}">
                                    <span>{{ row.materialName }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column  label="Miqdar">
                                <template slot-scope="{row,$index}">
                                    <template v-if="row.edit">
                                        <el-form-item :prop="'rawMaterials['+$index+'].materialQuantity'" :rules="[{required:true,message:'xammalın miqdarı girilməlidir',trigger:'blur'}]">
                                          <el-input v-model="row.materialQuantity" type="number" class="edit-input" size="small" />
                                        </el-form-item>
                                    </template>
                                    <span v-else>{{ row.materialQuantity }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column  label="Ölçü vahidi">
                                <template slot-scope="{row,$index}">
                                    <template v-if="row.edit">
                                        <el-form-item :prop="'rawMaterials['+$index+'].measurementUnit'" :rules="[{required:true,message:'Ölçü tipi girilməlidir',trigger:'blur'}]">
                                         <el-select  v-model="row.measurementUnit" placeholder="Ölçü tipini seçin" clearable value-key="id" :key="'rawMaterialsUnit'+$index">
                                            <el-option v-for="(item,i) in measurementUnits" :key="'rawMaterialsUnit'+$index+item.id" :label="item.description" :value="item" />
                                         </el-select>
                                        </el-form-item>
                                    </template>
                                    <span v-else>{{ row.measurementUnit.description }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column align="right" label="Actions">
                                <template slot-scope="{row}">
                                    <el-button
                                            v-if="row.edit"
                                            type="success"
                                            size="small"
                                            icon="el-icon-circle-check-outline"
                                            @click="toggleRowEdit(row)"
                                            :key="'save'+row.materialId"
                                    >
                                        Saxla
                                    </el-button>
                                    <el-button
                                            v-else
                                            type="primary"
                                            size="small"
                                            icon="el-icon-edit"
                                            @click="toggleRowEdit(row)"
                                            :key="'edit'+row.materialId"
                                    >
                                        Dəyiş
                                    </el-button>
                                    <el-button
                                            type="danger"
                                            size="small"
                                            icon="el-icon-delete"
                                            @click="removeRawMaterial(row)"
                                            :key="'delete'+row.materialId"
                                    >
                                        Sil
                                    </el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <el-row :gutter="100">
                            <el-col :span="24" align="center" style="margin-top: 30px">
                                Xammali əlavə edin
                                <el-button align="center" @click="openRawMaterialDialog"  circle size="large"><i class="el-icon-plus" ></i></el-button>
                            </el-col>
                        </el-row>
                    </el-tab-pane>
                </el-tabs>
            </div>
            <dialog-data-table
                    :show-dialog.sync="showDialog"
                    :columns="rawMaterialCols"
                    :search-url="searchUrl"
                    @on-add-click="onMaterialsAdd"
            >
            </dialog-data-table>
        </template>
    </base-form>

</template>

<script>
    import BaseForm from '@/components/BaseForm'
    import httpClient from '@/utils/request';
    import DialogDataTable from "@/components/DialogDataTable";
    import {MessageBox, Message} from 'element-ui'

    export default {
        name: "product-form",
        components: {DialogDataTable, BaseForm,Message,MessageBox},
        props: {
            isEdit: {
                type: Boolean,
                default: false,
                required: false
            },
            isView: {
                type: Boolean,
                default: false,
                required: false
            }
        },
        data() {
            return {
                activeTab:'materials',
                measurementUnits:[],
                showDialog:false,
                rawMaterialCols: [
                    {
                        label: "id",
                        field: "id",
                        sortable: true
                    },
                    {
                        label: "Ad",
                        field: "name",
                        sortable: false
                    },
                    {
                        label: "Açıqlama",
                        field: "description",
                        sortable: false
                    }
                ],
                searchUrl:'/material-list',
                warrantyPeriodTerms:[{label:'Ay',value:'MONTH'},{label:'İl',value: "YEAR"},{label:"Gün",value:"DAY"}],
                loading: false,
                formRules:{
                    name:[
                        {required:true,message:'məcburi sahədir',trigger:'blur'},
                        {min:3,message:'Length must be greater than 3',trigger:'blur'},
                    ],
                    description:[{required:true,message:'məcburi sahə',trigger:'blur'},],
                },
                form: {
                    id:0,
                    name:'',
                    description:'',
                    warrantyPeriod:0,
                    warrantyPeriodTerm:'',
                    price:0,
                    recomRetailPrice:0,
                    weight:0,
                    weightUnit:null,
                    barCode:'',
                    rawMaterials:[]
                }
            }
        },

       async mounted() {
          const response=await  httpClient.get('/unit/all');
          this.measurementUnits=response.data;
        },
        methods: {


            removeRawMaterial(row){
                this.form.rawMaterials=this.form.rawMaterials.filter(val=>row.materialId!=val.materialId);
            },

            openRawMaterialDialog(){
                this.showDialog=true;
            },

            toggleRowEdit(row) {
                if(row.edit === undefined) row.edit=false;
                if(row.edit===true && (row.materialQuantity== undefined || row.materialQuantity<=0 || row.materialQuantity==null)){
                    Message({
                        message: 'Miqdar düzgün girilməyib',
                        type: 'error',
                        duration: 2 * 1000
                    });
                    return;
                }else if(row.edit==true && (row.measurementUnit== undefined || row.measurementUnit ==null)){
                    Message({
                        message: 'Ölçü tipi düzgün girilməyib',
                        type: 'error',
                        duration: 2 * 1000
                    });
                    return;
                }
                row.edit = !row.edit;

            },

            onMaterialsAdd(materials) {
                const mappedMaterials=materials.map(t=>({materialId:t.id,materialName:t.name,edit:true}));
                const combinedArray = [...this.form.rawMaterials, ...mappedMaterials];
                this.form.rawMaterials = Array.from(new Set(combinedArray.map(a => a.materialId)))
                    .map(id => {
                        return combinedArray.find(a => a.materialId === id)
                    })
            },

            async loaderFunc(){
               const response =  await httpClient.get(`/product/${this.$route.params.id}`);
               this.form=Object.assign(this.form,response.data);
                this.form.rawMaterials = this.form.rawMaterials.map(v => {
                    this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
                    return v
                })
            },

            async handleSubmit() {
                const url = this.isEdit ? '/product/edit' : '/product';
                await httpClient.post(url, this.form);
            }
        }
    }
</script>

<style lang="scss" scoped>
</style>
