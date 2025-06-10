<template>
    <div class="mt-2 mx-5">
    <h2>
       <strong> Cài đặt tài khoản</strong>
    </h2>
    <h6 class="mb-5">WEAR WHAT MAKES YOU FEEL CONFIDENT</h6>
    </div>
    
    <div>
        

        <form action="" >
            <div class="card" style="width: 240px; height: 320px; margin: auto;">
                <img :src="user.Avarta"  alt="" id="avarta" class="card-img" width="100%" height="100%">
            </div>
            <h4 ><strong>Thông tin khách hàng</strong></h4>
            <label for="" class="form-label">Họ và tên</label>
            <input type="text" class="form-control" v-model="user.Name">
            <div class="row">
                <div class="col-sm-6">
                    <label for="" class="form-label">Địa chỉ Email</label>
                    <input type="Email" class="form-control" v-model="user.Email">
                </div>
                <div class="col-sm-6">
                    <label for="" class="form-label">số điện thoại</label>
                    <input type="text" class="form-control" v-model="user.Phone">
                </div>
            </div>
            <h4 class="mt-4"><Strong class="">Đổi mật khẩu</Strong></h4>
            <div class="row">
                <div class="col-sm-6">
                    <label for="" class="form-label">Mật khẩu cũ</label>
                    <input type="password" class="form-control" v-model="user.Password">
                </div> 
                <div class="col-sm-6">
                    
                </div> 
                <div class="col-sm-6 mt-3">
                    <label for="" class="form-label">Mật khẩu mới</label>
                    <input type="password" class="form-control">
                </div>
                <div class="col-sm-6 mt-3">  
                    <label for="" class="form-label">Xác nhận mật khẩu</label>
                    <input type="password" class="form-control">
                </div>
            </div>
            <h4 class="mt-4"><Strong class="" >Địa chỉ</Strong></h4>
            <label for="" class="form-label">Địa chỉ nhận hàng</label>
            <textarea class="form-control input-address"v-model="user.Address"></textarea>
            <div class="div-input">
                <button class="btn my-4">Cập Nhật</button>
            </div>
        </form>
    </div>

</template>
<script setup>
import {reactive} from 'vue';
import avarta from '../../assets/imgs/avarta_Kha.png';
    const user= reactive({
        Name:'Nguyễn Nhật Khả',
        Phone:'0494953535',
        Avarta: avarta,
        Email:'KhaDepTraiNhatVuTru@gmail.com',
        Password:'123123',
        Address:'Cà Mau'
    })
</script>
<style scoped>
    form{
        height: auto;
        width: 50%;
        margin: auto;
        padding: 10px;
        border: 1px solid black;
        border-radius: 15px;
        margin-bottom: 50px;
    }

    .btn{
        background-color: burlywood;
        right: 45%;
        position: absolute;
        font-size: larger;
        height: 60px;
        width: 180px;
        border-radius: 25px;
    }
    .btn:hover{
        background-color: rgb(255, 168, 53);
    }
    .div-input{
        position: relative;
        min-height: 90px;
    }
    
</style>